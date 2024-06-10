package com.techelevator.model;

public class Orders {

    private int orderId;

    private String name;
    public Orders(){

    }

    public Orders(int order_id, String name) {
        this.orderId = order_id;
        this.name = name;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "orders{" +
                "order_id = " + orderId +
                ", order_name = '" + name + '\'' +
                '}';
    }
}
