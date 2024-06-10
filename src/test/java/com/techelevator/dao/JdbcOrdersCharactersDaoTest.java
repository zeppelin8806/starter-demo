package com.techelevator.dao;

import com.techelevator.model.OrdersCharacters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcOrdersCharactersDaoTest extends BaseDaoTests{
    private static final OrdersCharacters oc1 = new OrdersCharacters("orderTest1", "characterTest1", 1);
    private static final OrdersCharacters oc2 = new OrdersCharacters("orderTest2", "characterTest2", 2);
    private static final OrdersCharacters oc3 = new OrdersCharacters("orderTest3", "characterTest3", 3);

    private JdbcOrdersCharactersDao dao;

    @Before
    public void setUp() throws Exception{
        this.dao = new JdbcOrdersCharactersDao(dataSource);
    }

    @Test
    public void getOrderById() {
        List<OrdersCharacters> ordersCharacters = dao.getOrderById(1);
        Assert.assertEquals(1, ordersCharacters.size());
    }
}