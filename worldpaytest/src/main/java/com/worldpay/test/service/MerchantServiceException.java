package com.worldpay.test.service;

public class MerchantServiceException extends Exception {

    public MerchantServiceException(Exception e) {
        super(e);
    }

    public MerchantServiceException(String message, Exception e) {
        super(message, e);
    }

}
