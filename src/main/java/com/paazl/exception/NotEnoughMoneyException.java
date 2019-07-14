package com.paazl.exception;

import java.math.BigInteger;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(BigInteger balance, BigInteger totalPrice) {
        super(String.format("There is not enough money on your balance to make order. " +
                "Balance: %s, total price: %s", balance, totalPrice));
    }
}
