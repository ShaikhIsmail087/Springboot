package com.jpa.transaction.springTransaction.controller;

import com.jpa.transaction.springTransaction.dto.BookingRequest;
import com.jpa.transaction.springTransaction.dto.BookingResponse;
import com.jpa.transaction.springTransaction.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking")
    public BookingResponse bookTicket(@RequestBody BookingRequest bookingRequest) {
        return bookingService.bookTicket(bookingRequest);
    }
}
