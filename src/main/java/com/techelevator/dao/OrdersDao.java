package com.techelevator.dao;

import com.techelevator.model.Orders;

import java.util.List;

public interface OrdersDao {

    Orders getOrderById(int orderId);

    List<Orders> getOrders();
}
