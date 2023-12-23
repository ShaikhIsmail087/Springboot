package com.jpa.transaction.springTransaction.controller;

import com.jpa.transaction.springTransaction.dto.BookingRequest;
import com.jpa.transaction.springTransaction.dto.BookingResponse;
import com.jpa.transaction.springTransaction.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking")
    public BookingResponse bookTicket(@RequestBody BookingRequest bookingRequest) {
        return bookingService.bookTicket(bookingRequest);
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {
        BookingResponse bookingResponse = bookingService.getBooking(id);

        if (bookingResponse != null) {
            return ResponseEntity.ok(bookingResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data available for the given booking ID: " + id);
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> getAllBookings() {
        List<BookingResponse> bookingResponses = bookingService.getAllBookings();

        if (!bookingResponses.isEmpty()) {
            return ResponseEntity.ok(bookingResponses);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No booking data available");
        }
    }

    @PutMapping("/booking/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody BookingRequest bookingRequest) {
        BookingResponse updatedBooking = bookingService.updateBooking(id, bookingRequest);

        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No data available for the given booking ID: " + id + ". Unable to update.");
        }
    }


    @DeleteMapping("/booking/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        boolean isBookingCancelled = bookingService.cancelBooking(id);

        if (isBookingCancelled) {
            return ResponseEntity.ok("Booking successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data available for the given booking ID");
        }
    }


}
