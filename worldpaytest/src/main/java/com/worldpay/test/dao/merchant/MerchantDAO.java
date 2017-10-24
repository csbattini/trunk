package com.worldpay.test.dao.merchant;

import com.worldpay.test.model.Offer;

import java.util.List;

public interface MerchantDAO {
     Offer createOffer(Offer offer) throws MerchantDAOException;
     public List<Offer> getOfferList();
    }
