package com.jpa.transaction.springTransaction.service;

import com.jpa.transaction.springTransaction.dto.BookingRequest;
import com.jpa.transaction.springTransaction.dto.BookingResponse;
import com.jpa.transaction.springTransaction.exception.InsufficientBalanceException;

import java.util.List;

public interface BookingService {

    public BookingResponse bookTicket(BookingRequest bookingRequest) throws InsufficientBalanceException;

    BookingResponse getBooking(Long id);

    List<BookingResponse> getAllBookings();

    BookingResponse updateBooking(Long id, BookingRequest bookingRequest);

    boolean cancelBooking(Long id);
}
