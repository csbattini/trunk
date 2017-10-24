package com.worldpay.test.dao.merchant;

public class MerchantDAOException extends Exception {

    public MerchantDAOException(Exception e) {
        super(e);
    }

    public MerchantDAOException(String message, Exception e) {
        super(message, e);
    }
}
