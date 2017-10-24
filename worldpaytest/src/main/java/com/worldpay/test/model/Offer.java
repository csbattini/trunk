package com.worldpay.test.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Offer {

    private UUID id;
    private String name;
    private String description;
    private Boolean isService;
    private List<String> conditions;
    private BigDecimal price;
    private String currency;
    // FIXME - add merchant id
    //    private String merchantId;

    public Offer() {
    }

    public Offer(UUID id, String name, String description, Boolean isService, List<String> conditions, BigDecimal price, String currency) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isService = isService;
        this.conditions = conditions;
        this.price = price;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsService() {
        return isService;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsService(Boolean service) {
        isService = service;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(getName(), offer.getName()) &&
                Objects.equals(getDescription(), offer.getDescription()) &&
                Objects.equals(getIsService(), offer.getIsService()) &&
                Objects.equals(getConditions(), offer.getConditions()) &&
                Objects.equals(getPrice(), offer.getPrice()) &&
                Objects.equals(getCurrency(), offer.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getIsService(), getConditions(), getPrice(), getCurrency());
    }
}
