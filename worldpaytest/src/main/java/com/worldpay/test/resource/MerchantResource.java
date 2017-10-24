package com.worldpay.test.resource;

import com.google.gson.Gson;
import com.worldpay.test.model.Offer;
import com.worldpay.test.service.MerchantService;
import com.worldpay.test.service.MerchantServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("merchant")
public class MerchantResource {

    private MerchantService merchantService;

    public MerchantResource() {
        this.merchantService = new MerchantService();
    }

    MerchantResource(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(Offer offer) {
        try {
            // To Do : check the given offer contents before creating on the DB.
            Offer createdOffer = merchantService.create(offer);
            if (createdOffer == null) {
                return Response
                        .status(403)
                        .entity(null)
                        .build();
            }
            return Response
                    .status(201)
                    .entity(createdOffer)
                    .build();
        } catch (MerchantServiceException e) {
            return Response
                    .status(500)
                    .entity(e.getLocalizedMessage())
                    .build();
        }
    }

    @GET
    @Produces("text/plain")
    public String getOffers() {
        // Return some cliched textual content
        String usage = "Usage : \n" +
                "-------------------------------------------------------\n" +
                 "HTTP Verb : POST \n\n" +
                "Link : http://localhost:8080/merchant \n\n" +
                "Example Payload : \n" +
                "{\n" +
                "  \"name\": \"my product\",\n" +
                "  \"description\": \"my product description\",\n" +
                "  \"isService\": false,\n" +
                "  \"conditions\": [\n" +
                "    \"condition 1\",\n" +
                "    \"condition 2\",\n" +
                "    \"condition 3\"\n" +
                "  ],\n" +
                "  \"price\": 99.99,\n" +
                "  \"currency\": \"GBP\"\n" +
                "}\n" +
                "-------------------------------------------------------\n";

        String offers = "";
        List<Offer> offerList = merchantService.GetOfferList();
        //offerList.forEach(offer -> getOfferToSting(offer));
        for (Offer offer : offerList){
            offers += getOfferToSting(offer);
        }
         return usage + offers;
    }

    private String getOfferToSting(Offer offer){
        Gson gson = new Gson();
        return gson.toJson(offer) + "\n";
    }
}
