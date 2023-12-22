package com.jpa.transaction.springTransaction.utility;

import com.jpa.transaction.springTransaction.exception.InsufficientBalanceException;
import java.util.HashMap;
import java.util.Map;

public class PaymentGatewaySimulator {

    private static Map<String, Double> accountBalanceMap = new HashMap<>();

    static{
        accountBalanceMap.put("acnt-1212", 2000.00);
        accountBalanceMap.put("acnt-1313", 4000.00);
        accountBalanceMap.put("acnt-1414", 3000.00);
    }

    public static boolean validateFareBalanceCriteria(String accountNo, Double fare) {

        if (fare > accountBalanceMap.get(accountNo)) {
            throw new InsufficientBalanceException("You do not have sufficient balance in your account");
        }
        return true;
    }
}
