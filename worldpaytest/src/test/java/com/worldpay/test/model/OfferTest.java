package com.worldpay.test.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class OfferTest {

    @Test
    public void testCreateOffer_product() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "my product";
        String description = "my product description";
        Boolean isService = false;
        String condition1 = "condition 1";
        String condition2 = "condition 2";
        String condition3 = "condition 3";
        List<String> conditions = Arrays.asList(condition1, condition2, condition3);
        BigDecimal price = new BigDecimal("99.99");
        String currency = "GBP";
        Offer offer = new Offer(id, name, description, isService, conditions, price, currency);

        assertEquals(id, offer.getId());
        assertEquals(name, offer.getName());
        assertEquals(description, offer.getDescription());
        assertEquals(isService, offer.getIsService());
        assertEquals(conditions, offer.getConditions());
        assertEquals(price, offer.getPrice());
        assertEquals(currency, offer.getCurrency());
    }

    @Test
    public void testCreateOffer_service() throws Exception {
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
        Offer offer = new Offer(id, name, description, isService, conditions, price, currency);

        assertEquals(id, offer.getId());
        assertEquals(name, offer.getName());
        assertEquals(description, offer.getDescription());
        assertEquals(isService, offer.getIsService());
        assertEquals(conditions, offer.getConditions());
        assertEquals(price, offer.getPrice());
        assertEquals(currency, offer.getCurrency());
    }

}
