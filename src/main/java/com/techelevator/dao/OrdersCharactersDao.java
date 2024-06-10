package com.techelevator.dao;
import com.techelevator.model.OrdersCharacters;
import java.util.List;

public interface OrdersCharactersDao {
    List<OrdersCharacters> getOrderById(int orderId);

}
