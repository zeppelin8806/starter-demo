package com.techelevator.controller;

import com.techelevator.dao.OrdersCharactersDao;
import com.techelevator.dao.OrdersDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Orders;
import com.techelevator.model.OrdersCharacters;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrdersCharacterController {
    private OrdersDao ordersDao;
    private OrdersCharactersDao ordersCharactersDao;

    public OrdersCharacterController(OrdersDao ordersDao, OrdersCharactersDao ordersCharactersDao) {
        this.ordersDao = ordersDao;
        this.ordersCharactersDao = ordersCharactersDao;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/orders", method = RequestMethod.GET)
    public List<Orders> listOrders(){
        try{
            return ordersDao.getOrders();
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/orders/{id}", method = RequestMethod.GET)
    public Orders orderById(@PathVariable int id){
        try{
            return ordersDao.getOrderById(id);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/orders/{id}/characters", method = RequestMethod.GET)
    public List<OrdersCharacters> ordersAndCharacters(@PathVariable int id){
        try{
            return ordersCharactersDao.getOrderById(id);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }
}
