package com.worldpay.test.dao.merchant;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.worldpay.test.model.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MerchantDAOImpl implements MerchantDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantDAOImpl.class);
    private static String INSERT_OFFER = "INSERT INTO merchant_offer" + "(id, name, description, is_service, conditions, price, currency) values" + "(?,?,?,?,?,?,?)";
    private static String SELECT_ALL_OFFERS = "SELECT * FROM merchant_offer";
    private static String SELECT_OFFER = "SELECT * FROM merchant_offer where name = ? and is_service = ? and currency = ? and conditions = ?";

    private Connection connection;
    private PreparedStatement insertPreparedStatement = null;
    private PreparedStatement selectPreparedStatement = null;

    MerchantDAOImpl() {
    }

    public MerchantDAOImpl(Connection connection) {
        this.connection = connection;
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    void setInsertPreparedStatement(PreparedStatement insertPreparedStatement) {
        this.insertPreparedStatement = insertPreparedStatement;
    }

    void setSelectPreparedStatement(PreparedStatement selectPreparedStatement) {
        this.selectPreparedStatement = selectPreparedStatement;
    }

    public List<Offer> getOfferList() {
        List<Offer> offerList = new ArrayList<>();
        try {
            selectPreparedStatement = connection.prepareStatement(SELECT_ALL_OFFERS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPreparedStatement.executeQuery();
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            LOGGER.error("total number of rows found - %d)", rows);
            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Boolean isService = rs.getBoolean("is_service");
                String conditions = rs.getString("conditions");
                BigDecimal price = rs.getBigDecimal("price");
                String currency = rs.getString("currency");
                offerList.add(new Offer(id, name, description, isService, fromJson(conditions), price, currency));
            }
            return offerList;

        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public Offer getOffer(Offer offer) {

        if (offer == null) {
            LOGGER.debug("getOffer - null object given");
            return null;
        }
        try {
            selectPreparedStatement = connection.prepareStatement(SELECT_OFFER, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            selectPreparedStatement.setString(1, offer.getName());
            selectPreparedStatement.setBoolean(2, offer.getIsService());
            selectPreparedStatement.setString(3, offer.getCurrency());
            selectPreparedStatement.setString(4, toJson(offer.getConditions()));
            ResultSet rs = selectPreparedStatement.executeQuery();
            rs.last();
            int rows = rs.getRow();
            if (rows > 1) {
                LOGGER.error("More than one row found in the DB with details %s", offer.getName());
            }
            rs.beforeFirst();
            if(rs.next() == false)
            {
                return null;
            }
            UUID id = (UUID) rs.getObject("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            Boolean isService = rs.getBoolean("is_service");
            String conditions = rs.getString("conditions");
            BigDecimal price = rs.getBigDecimal("price");
            String currency = rs.getString("currency");
            return new Offer(id, name, description, isService, fromJson(conditions), price, currency);

        } catch (SQLException e) {
            LOGGER.error("", e);
        } finally {
            try {
                selectPreparedStatement.close();
            } catch (SQLException e) {
                LOGGER.error("", e);
            }
        }
        return null;
    }

    public Offer createOffer(Offer offer) throws MerchantDAOException {

         if (offer == null) {
            LOGGER.error("createOffer - null object given");
            return null;
        }
        try {
            if (getOffer(offer) != null) {
                LOGGER.debug("createOffer - product information is already available in the DB - %s", offer.getName());
                return null;
            }
            connection.setAutoCommit(false);
            insertPreparedStatement = connection.prepareStatement(INSERT_OFFER);
            insertPreparedStatement.setObject(1, UUID.randomUUID());
            insertPreparedStatement.setString(2, offer.getName());
            insertPreparedStatement.setString(3, offer.getDescription());
            insertPreparedStatement.setBoolean(4, offer.getIsService());
            insertPreparedStatement.setString(5, toJson(offer.getConditions()));
            insertPreparedStatement.setBigDecimal(6, offer.getPrice());
            insertPreparedStatement.setString(7, offer.getCurrency());
            insertPreparedStatement.executeUpdate();
            connection.commit();
            return getOffer(offer);
        } catch (Exception e) {
            LOGGER.error("", e.getMessage());
        } finally {
            try {
                insertPreparedStatement.close();
            } catch (Exception e) {
                LOGGER.error("", e.getMessage());
            }
        }
        return null;
    }

    private String toJson(List<String> conditions) {
        Gson gson = new Gson();
        return gson.toJson(conditions);
    }

    private List<String> fromJson(String conditions) {
        Gson gson = new Gson();
        return gson.fromJson(conditions, new TypeToken<List<String>>() {
        }.getType());
    }
}
