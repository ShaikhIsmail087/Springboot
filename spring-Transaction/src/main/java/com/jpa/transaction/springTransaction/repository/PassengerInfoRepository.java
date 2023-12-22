package com.jpa.transaction.springTransaction.repository;

import com.jpa.transaction.springTransaction.entity.PassengerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerInfoRepository extends JpaRepository<PassengerInfo, Long> {
}
