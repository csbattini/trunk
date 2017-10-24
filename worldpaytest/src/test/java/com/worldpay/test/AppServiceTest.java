package com.worldpay.test;

import com.worldpay.test.model.Offer;
import com.worldpay.test.resource.MerchantResource;
import com.worldpay.test.service.MerchantServiceException;
import com.worldpay.test.util.DBUtil;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertNull;

// These tests will NOT MOCK any objects. It creates temporary schema for testing purposes, runs the tests, and drops the table.
public class AppServiceTest extends JerseyTest {

    @Override
    protected Application configure() {

        return new ResourceConfig(MerchantResource.class);
    }

    @BeforeClass
    public static void setup() {
        DBUtil.dropTable(DBUtil.getConnection());
        DBUtil.createTable(DBUtil.getConnection());
    }

    @AfterClass
    public static void destroy() {
        DBUtil.dropTable(DBUtil.getConnection());
    }

    @Test
    public void testCreateOfferService() throws MerchantServiceException {

        Offer offer = getServiceOffer();
        Response response = target("merchant")
                .request()
                .post(Entity.entity(offer, MediaType.APPLICATION_JSON));

        Offer createdOffer = response.readEntity(Offer.class);
        Assert.assertEquals(201, response.getStatus());
        Assert.assertEquals(offer, createdOffer);
        // Assert.assertNotNull(createdOffer.getId());
    }
    @Test
    public void testCreateOfferServiceRepeat() throws MerchantServiceException {

        Offer offer = getNextServiceOffer();
        Response response = target("merchant")
                .request()
                .post(Entity.entity(offer, MediaType.APPLICATION_JSON));

        Offer createdOffer = response.readEntity(Offer.class);
        Assert.assertEquals(201, response.getStatus());
        Assert.assertEquals(offer, createdOffer);

        response = target("merchant")
                .request()
                .post(Entity.entity(offer, MediaType.APPLICATION_JSON));
        createdOffer = response.readEntity(Offer.class);
        Assert.assertNotEquals(201, response.getStatus());
        assertNull(createdOffer);

        // Assert.assertNotNull(createdOffer.getId());
    }


    @Test
    public void testCreateNullService() throws MerchantServiceException {

        Response response = target("merchant")
                .request()
                .post(Entity.entity(null, MediaType.APPLICATION_JSON));

        Offer createdOffer = response.readEntity(Offer.class);
        Assert.assertNotEquals(201, response.getStatus());
        // Assert.assertNotNull(createdOffer.getId());
        assertNull(createdOffer);
    }

    private Offer getServiceOffer() {
        UUID id = null;
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

    private Offer getNextServiceOffer() {
        UUID id = null;
        String name = "my service1";
        String description = "my service description1";
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
