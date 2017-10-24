package com.worldpay.test.dao.merchant;

import com.worldpay.test.model.Offer;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MerchantDAOImplTest {
    //due to a bug with anyString(), i need to copy the following 2 lines. Ideally, i should get them trough getters
    private static String INSERT_OFFER = "INSERT INTO merchant_offer" + "(id, name, description, is_service, conditions, price, currency) values" + "(?,?,?,?,?,?,?)";
    private static String SELECT_ALL_OFFERS = "SELECT * FROM merchant_offer";
    private static String SELECT_OFFER = "SELECT * FROM merchant_offer where name = ? and is_service = ? and currency = ? and conditions = ?";

    private MerchantDAOImpl merchantDAO = null;
    private Offer offer = null;
    PreparedStatement insertPreparedStatementMock = null;
    PreparedStatement selectPreparedStatementMock = null;
    ResultSet rsmock = null;
    Connection connMock = null;

    @Before
    public void before() throws Exception{
        offer = getProductOffer();
        merchantDAO = new MerchantDAOImpl();
        connMock = Mockito.mock(Connection.class);
        Mockito.doNothing().when(connMock).setAutoCommit(false);
        Mockito.doNothing().when(connMock).commit();
        merchantDAO.setConnection(connMock);
        insertPreparedStatementMock = Mockito.mock(PreparedStatement.class);
        selectPreparedStatementMock = Mockito.mock(PreparedStatement.class);
        rsmock = mock(ResultSet.class);


    }
    private void setInsertPreparedStatementMock() throws Exception    {
        assertNotNull(connMock);
        assertNotNull(merchantDAO);
        assertNotNull(insertPreparedStatementMock);
        when(connMock.prepareStatement(INSERT_OFFER)).thenReturn(insertPreparedStatementMock);
        merchantDAO.setInsertPreparedStatement(insertPreparedStatementMock);
        Mockito.doNothing().when(insertPreparedStatementMock).setString(anyInt(), anyString());
        Mockito.doNothing().when(insertPreparedStatementMock).setObject(anyInt(), anyObject());
        Mockito.doNothing().when(insertPreparedStatementMock).setBoolean(anyInt(), anyBoolean());
        Mockito.doNothing().when(insertPreparedStatementMock).setBigDecimal(anyInt(), any(BigDecimal.class));
        when(insertPreparedStatementMock.executeUpdate()).thenReturn(1);
        Mockito.doNothing().when(insertPreparedStatementMock).close();
    }

    private void setSelectPreparedStatementMock() throws Exception {
        assertNotNull(connMock);
        assertNotNull(merchantDAO);
        assertNotNull(selectPreparedStatementMock);
        merchantDAO.setSelectPreparedStatement(selectPreparedStatementMock);
        when(connMock.prepareStatement(SELECT_OFFER, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)).thenReturn(selectPreparedStatementMock);
        Mockito.doNothing().when(selectPreparedStatementMock).setString(anyInt(), anyString());
        Mockito.doNothing().when(selectPreparedStatementMock).setBoolean(anyInt(), anyBoolean());
        Mockito.doNothing().when(selectPreparedStatementMock).close();

    }

    private void setResultSetMock() throws Exception {
        assertNotNull(connMock);
        assertNotNull(merchantDAO);
        assertNotNull(selectPreparedStatementMock);
        assertNotNull(rsmock);
        when(selectPreparedStatementMock.executeQuery()).thenReturn(rsmock);
        Mockito.when(rsmock.last()).thenReturn(true);
        Mockito.doNothing().when(rsmock).beforeFirst();
    }

    private void setResultSetMockData() throws Exception {
        assertNotNull(connMock);
        assertNotNull(merchantDAO);
        assertNotNull(selectPreparedStatementMock);
        assertNotNull(rsmock);
       // Mockito.when(rsmock.next()).thenReturn(true);
        when(rsmock.getString("name")).thenReturn("my product");
        when(rsmock.getString("description")).thenReturn("my product description");
        when(rsmock.getBoolean("is_service")).thenReturn(true);
        when(rsmock.getString("currency")).thenReturn("GBP");
        when(rsmock.getBigDecimal("price")).thenReturn(new BigDecimal("99.99"));
        when(rsmock.getObject("id")).thenReturn(offer.getId());
    }



    @Test
    public void testCeateOffer() throws Exception {
        setInsertPreparedStatementMock();
        setSelectPreparedStatementMock();
        setResultSetMock();
        // retrun false first time, and true second time
        Mockito.when(rsmock.next()).thenReturn(false, true);
        Mockito.when(rsmock.getRow()).thenReturn(0,1);
        setResultSetMockData();
        Offer expectedOffer = merchantDAO.createOffer(offer);
        assertNotNull(expectedOffer.getId());
        assertEquals(offer.getName(), expectedOffer.getName());
        assertEquals(offer.getDescription(), expectedOffer.getDescription());
        assertEquals(offer.getIsService(), expectedOffer.getIsService());
        assertEquals(offer.getPrice(), expectedOffer.getPrice());
        assertEquals(offer.getCurrency(), expectedOffer.getCurrency());
    }

    @Test
    public void testCreateOfferForNull() throws Exception {
        setInsertPreparedStatementMock();
        setSelectPreparedStatementMock();
        setResultSetMock();

        Offer expectedOffer = merchantDAO.createOffer(null);
        assertNull(expectedOffer);
    }

    @Test
    public void testCreateOfferAlreayInThere() throws Exception {
        setInsertPreparedStatementMock();
        setSelectPreparedStatementMock();
        setResultSetMock();
        // retrun false first time, and true second time
        Mockito.when(rsmock.next()).thenReturn(true);
        Mockito.when(rsmock.getRow()).thenReturn(1);
        setResultSetMockData();
        Offer expectedOffer = merchantDAO.createOffer(offer);
        assertNull(expectedOffer);

    }

    @Test
    public void testGetOffer() throws Exception{
        setSelectPreparedStatementMock();
        setResultSetMock();
        Mockito.when(rsmock.next()).thenReturn(true);
        Mockito.when(rsmock.getRow()).thenReturn(1);
        setResultSetMockData();
        Offer expectedOffer = merchantDAO.getOffer(offer);
        assertNotNull(expectedOffer.getId());
        assertEquals(offer.getName(), expectedOffer.getName());
        assertEquals(offer.getDescription(), expectedOffer.getDescription());
        assertEquals(offer.getIsService(), expectedOffer.getIsService());
        assertEquals(offer.getPrice(), expectedOffer.getPrice());
        assertEquals(offer.getCurrency(), expectedOffer.getCurrency());
    }

    @Test
    public void testGetOfferForNull() throws Exception{
        Offer expectedOffer = merchantDAO.getOffer(null);
        assertNull(expectedOffer);
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
