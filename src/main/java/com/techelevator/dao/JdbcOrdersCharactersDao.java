package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Orders;
import com.techelevator.model.OrdersCharacters;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcOrdersCharactersDao implements OrdersCharactersDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcOrdersCharactersDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<OrdersCharacters> getOrderById(int orderId) {
        List<OrdersCharacters> ordersCharacters = new ArrayList<>();
        String sql = "SELECT order_name, characters.name, ideal" +
                " FROM orders_characters" +
                " INNER JOIN orders ON orders.order_id = orders_characters.order_id" +
                " INNER JOIN characters ON characters.character_id = orders_characters.character_id" +
                " WHERE orders_characters.order_id = ?" +
                " ORDER BY ideal desc";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, orderId);
            while (results.next()){
                OrdersCharacters ordersCharacter = mapRowToOrdersCharacters(results);
                ordersCharacters.add(ordersCharacter);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return ordersCharacters;
    }
    private OrdersCharacters mapRowToOrdersCharacters(SqlRowSet results){
        OrdersCharacters ordersCharacters = new OrdersCharacters();
        ordersCharacters.setOrderName(results.getString("order_name"));
        ordersCharacters.setCharacterName(results.getString("name"));
        ordersCharacters.setIdeal(results.getInt("ideal"));
        return ordersCharacters;
    }
}
