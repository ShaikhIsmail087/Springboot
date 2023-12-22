package com.jpa.transaction.springTransaction.service;

import com.jpa.transaction.springTransaction.dto.BookingRequest;
import com.jpa.transaction.springTransaction.dto.BookingResponse;
import com.jpa.transaction.springTransaction.exception.InsufficientBalanceException;

public interface BookingService {

    public BookingResponse bookTicket(BookingRequest bookingRequest) throws InsufficientBalanceException;
}
