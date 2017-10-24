package com.worldpay.test.service;

import com.worldpay.test.dao.merchant.MerchantDAO;
import com.worldpay.test.dao.merchant.MerchantDAOException;
import com.worldpay.test.dao.merchant.MerchantDAOImpl;
import com.worldpay.test.model.Offer;
import com.worldpay.test.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.OverlappingFileLockException;
import java.util.List;

public class MerchantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantService.class);

    private MerchantDAO merchantDAO;

    public MerchantService() {
        this.merchantDAO = new MerchantDAOImpl(DBUtil.getConnection());
    }

    void setMerchantDAO(MerchantDAO merchantDAO) {
        this.merchantDAO = merchantDAO;
    }

    public Offer create(Offer offer) throws MerchantServiceException {
        try {
            if (offer == null) {
                LOGGER.debug("create - null Offer Given");
                return null;
            }
            Offer createdOffer = merchantDAO.createOffer(offer);
            DBUtil.close(DBUtil.getConnection());
            if (createdOffer == null) {
                  LOGGER.debug("create - Can not write offer into the DB - %s", offer.getName());
            }
            return createdOffer;
        } catch (MerchantDAOException e) {
            throw new MerchantServiceException(e);
        }
    }

    /* As the following method is not part of the requirement,
       I haven't found time to start off this with a TDD test.
       I added the method while refactoring other code.
     */
    public List<Offer> GetOfferList() {
        List<Offer> offerList = merchantDAO.getOfferList();
        if (offerList == null) {
            LOGGER.debug("create - Can not get offer list");
        }
        return offerList;
    }

}
