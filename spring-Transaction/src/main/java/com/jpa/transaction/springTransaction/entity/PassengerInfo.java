package com.jpa.transaction.springTransaction.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PASSENGER_INFO")
public class PassengerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pId;
    private String name;
    private String email;
    private String source;
    private String destination;
    private String pickupTime;
    private String arrivalTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date travelDate;
    private double fare;
}
