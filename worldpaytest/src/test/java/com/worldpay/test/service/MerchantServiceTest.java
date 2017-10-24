package com.worldpay.test.service;

import com.worldpay.test.dao.merchant.MerchantDAO;
import com.worldpay.test.model.Offer;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// MerchantService tests will mock DB(MerchantDAO) layer.
public class MerchantServiceTest {

    private MerchantService merchantService;
    private MerchantDAO mockmerchantDAO;

    @Before
    public void before() {
        merchantService = new MerchantService();
        mockmerchantDAO = mock(MerchantDAO.class);
        merchantService.setMerchantDAO(mockmerchantDAO);
    }

    @Test
    public void testCreateOffer_service() throws Exception {
        Offer offer = getServiceOffer();
        when(mockmerchantDAO.createOffer(offer)).thenReturn(offer);
        Offer createdOffer = merchantService.create(offer);
        assertNotNull(createdOffer.getId());
        assertEquals(offer.getName(), createdOffer.getName());
        assertEquals(offer.getDescription(), createdOffer.getDescription());
        assertEquals(offer.getIsService(), createdOffer.getIsService());
        assertEquals(offer.getConditions(), createdOffer.getConditions());
        assertEquals(offer.getPrice(), createdOffer.getPrice());
        assertEquals(offer.getCurrency(), createdOffer.getCurrency());
    }

    @Test
    public void testCreateOffer_product() throws Exception {

        Offer offer = getProductOffer();
        when(mockmerchantDAO.createOffer(offer)).thenReturn(offer);
        Offer createdOffer = merchantService.create(offer);
        assertNotNull(createdOffer.getId());
        assertEquals(offer.getName(), createdOffer.getName());
        assertEquals(offer.getDescription(), createdOffer.getDescription());
        assertEquals(offer.getIsService(), createdOffer.getIsService());
        assertEquals(offer.getConditions(), createdOffer.getConditions());
        assertEquals(offer.getPrice(), createdOffer.getPrice());
        assertEquals(offer.getCurrency(), createdOffer.getCurrency());
    }

    @Test
    public void testNullOffer() throws Exception{
        Offer createdOffer = merchantService.create(null);
        assertNull(createdOffer);
    }

    private Offer getServiceOffer() {
        UUID id = UUID.randomUUID();
        String name = "my service";
        String description = "my service description";
        Boolean isService = true;
        String condition1 = "condition 1";
        String condition2 = "condition 2";
        String condition3 = "condition 3";
        List<String> conditions = Arrays.asList(condition1, condition2, condition3);
        BigDecimal price = new BigDecimal("99.99");
        String currency = "GBP";
        return new Offer(id, name, description, isService, conditions, price, currency);
    }

    private Offer getProductOffer() {
        UUID id = UUID.randomUUID();
        String name = "my product";
        String description = "my product description";
        Boolean isService = true;
        String condition1 = "condition 1";
        String condition2 = "condition 2";
        String condition3 = "condition 3";
        List<String> conditions = Arrays.asList(condition1, condition2, condition3);
        BigDecimal price = new BigDecimal("99.99");
        String currency = "GBP";
        return new Offer(id, name, description, isService, conditions, price, currency);
    }
}
