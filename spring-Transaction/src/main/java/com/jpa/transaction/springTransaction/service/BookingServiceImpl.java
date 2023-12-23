package com.jpa.transaction.springTransaction.service;

import com.jpa.transaction.springTransaction.dto.BookingRequest;
import com.jpa.transaction.springTransaction.dto.BookingResponse;
import com.jpa.transaction.springTransaction.entity.PassengerInfo;
import com.jpa.transaction.springTransaction.entity.PaymentInfo;
import com.jpa.transaction.springTransaction.repository.PassengerInfoRepository;
import com.jpa.transaction.springTransaction.repository.PaymentInfoRepository;
import com.jpa.transaction.springTransaction.utility.PaymentGatewaySimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;
    @Autowired
    private PassengerInfoRepository passengerInfoRepository;

    // Transaction only works with public method and unchecked exception
    @Transactional//(rollbackFor = {InsufficientBalanceException.class},readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public BookingResponse bookTicket(BookingRequest bookingRequest) {
        BookingResponse bookingResponse = null;
        PassengerInfo passengerInfo = passengerInfoRepository.save(bookingRequest.getPassengerInfo());

        PaymentInfo paymentInfo = bookingRequest.getPaymentInfo();
        //simulate transaction failure due to insufficient balance in account
        PaymentGatewaySimulator.validateFareBalanceCriteria(paymentInfo.getAccountNo(), passengerInfo.getFare());

        paymentInfo.setPassengerId(passengerInfo.getPId());
        paymentInfo.setTotalFare(passengerInfo.getFare());
        paymentInfoRepository.save(paymentInfo);

        bookingResponse = new BookingResponse();
        bookingResponse.setStatus("SUCCESS");
        bookingResponse.setPassengerInfo(passengerInfo);
        bookingResponse.setPnr(UUID.randomUUID().toString().split("-")[0]);
        bookingResponse.setTotalFare(passengerInfo.getFare());

        return bookingResponse;
    }

    @Transactional(readOnly = true)
    public BookingResponse getBooking(Long id) {
        Optional<PassengerInfo> optionalPassengerInfo = passengerInfoRepository.findById(id);

        if (optionalPassengerInfo.isPresent()) {
            PassengerInfo passengerInfo = optionalPassengerInfo.get();

            BookingResponse bookingResponse = new BookingResponse();
            bookingResponse.setStatus("SUCCESS");
            bookingResponse.setPassengerInfo(passengerInfo);
            bookingResponse.setPnr(UUID.randomUUID().toString().split("-")[0]);
            bookingResponse.setTotalFare(passengerInfo.getFare());

            return bookingResponse;
        } else {
            return null; // Handle the case when the booking with the given ID is not found
        }
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings() {
        List<BookingResponse> bookingResponses = new ArrayList<>();

        // Retrieve all passenger info records
        List<PassengerInfo> passengerInfos = passengerInfoRepository.findAll();

        for (PassengerInfo passengerInfo : passengerInfos) {
            BookingResponse bookingResponse = new BookingResponse();
            bookingResponse.setStatus("SUCCESS");
            bookingResponse.setPassengerInfo(passengerInfo);
            bookingResponse.setPnr(UUID.randomUUID().toString().split("-")[0]);
            bookingResponse.setTotalFare(passengerInfo.getFare());

            bookingResponses.add(bookingResponse);
        }

        return bookingResponses;
    }

    @Transactional
    public BookingResponse updateBooking(Long id, BookingRequest bookingRequest) {
        Optional<PassengerInfo> optionalPassengerInfo = passengerInfoRepository.findById(id);

        if (optionalPassengerInfo.isPresent()) {
            PassengerInfo existingPassengerInfo = optionalPassengerInfo.get();
            PassengerInfo updatedPassengerInfo = bookingRequest.getPassengerInfo();

            // Update relevant fields in existingPassengerInfo with values from updatedPassengerInfo
            existingPassengerInfo.setName(updatedPassengerInfo.getName());
            existingPassengerInfo.setEmail(updatedPassengerInfo.getEmail());
            existingPassengerInfo.setSource(updatedPassengerInfo.getSource());
            existingPassengerInfo.setDestination(updatedPassengerInfo.getDestination());
            existingPassengerInfo.setPickupTime(updatedPassengerInfo.getPickupTime());
            existingPassengerInfo.setArrivalTime(updatedPassengerInfo.getArrivalTime());
            existingPassengerInfo.setFare(updatedPassengerInfo.getFare());
            existingPassengerInfo.setTravelDate(updatedPassengerInfo.getTravelDate());

            passengerInfoRepository.save(existingPassengerInfo);

            BookingResponse bookingResponse = new BookingResponse();
            bookingResponse.setStatus("SUCCESS");
            bookingResponse.setPassengerInfo(existingPassengerInfo);
            bookingResponse.setPnr(UUID.randomUUID().toString().split("-")[0]);
            bookingResponse.setTotalFare(existingPassengerInfo.getFare());

            return bookingResponse;
        } else {
            return null; // Handle the case when the booking with the given ID is not found
        }
    }

    @Transactional
    public boolean cancelBooking(Long bookingId) {
        Optional<PassengerInfo> passengerInfoOptional = passengerInfoRepository.findById(bookingId);

        if (passengerInfoOptional.isPresent()) {
            PassengerInfo passengerInfo = passengerInfoOptional.get();

            // Delete PassengerInfo by Booking ID
            passengerInfoRepository.deleteById(bookingId);

            return true; // Return true when the booking is successfully canceled
        }

        return false; // Return false when the booking ID is not found
    }
}
