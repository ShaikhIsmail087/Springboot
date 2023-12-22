package com.jpa.transaction.springTransaction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT_INFO")
public class PaymentInfo {

    @Id
    @GeneratedValue(generator = "UUID2")
    @GenericGenerator(name = "UUID2", strategy = "org.hibernate.id.UUIDGenerator")
    private String paymentId;
    private String accountNo;
    private double totalFare;
    private String cardType;
    private Long passengerId;

}
