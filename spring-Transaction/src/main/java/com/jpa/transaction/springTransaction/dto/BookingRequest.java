package com.jpa.transaction.springTransaction.dto;

import com.jpa.transaction.springTransaction.entity.PassengerInfo;
import com.jpa.transaction.springTransaction.entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private PaymentInfo paymentInfo;
    private PassengerInfo passengerInfo;
}
