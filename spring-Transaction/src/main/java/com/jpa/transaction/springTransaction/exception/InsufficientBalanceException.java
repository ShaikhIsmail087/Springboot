package com.jpa.transaction.springTransaction.exception;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
