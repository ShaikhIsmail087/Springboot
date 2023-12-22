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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}
