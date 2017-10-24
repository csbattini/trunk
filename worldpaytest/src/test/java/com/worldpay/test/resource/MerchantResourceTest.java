package com.worldpay.test.resource;

import com.worldpay.test.model.Offer;
import com.worldpay.test.service.MerchantService;
import com.worldpay.test.service.MerchantServiceException;
import com.worldpay.test.util.DBUtil;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

// These tests Mocks MerchantService layer.
public class MerchantResourceTest extends JerseyTest {

    @Mock
    private MerchantService merchantService;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig().register(new MerchantResource(merchantService));
    }

    @Test
    public void testCreateOffer() throws MerchantServiceException {
        DBUtil.dropTable(DBUtil.getConnection());

        Offer offer = getServiceOffer();
        when(merchantService.create(offer)).thenReturn(offer);
        Response response = target("merchant")
                .request()
                .post(Entity.entity(offer, MediaType.APPLICATION_JSON));

        Offer createdOffer = response.readEntity(Offer.class);
        Assert.assertEquals(201, response.getStatus());
        Assert.assertEquals(offer, createdOffer);
        // Assert.assertNotNull(createdOffer.getId());
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

}