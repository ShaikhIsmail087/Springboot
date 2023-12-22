package com.jpa.transaction.springTransaction.repository;

import com.jpa.transaction.springTransaction.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo,String> {
}
