package com.techelevator.model;

public class OrdersCharacters {

    private int orderId;
    private int characterId;
    private int ideal;
    private String orderName;
    private String characterName;

    public OrdersCharacters(){

    }
/*

    public OrdersCharacters(int orderId, int characterId, int ideal) {
        this.orderId = orderId;
        this.characterId = characterId;
        this.ideal = ideal;
    }
*/

    public OrdersCharacters(String orderName, String characterName, int ideal){
        this.orderName = orderName;
        this.characterName = characterName;
        this.ideal = ideal;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public int getIdeal() {
        return ideal;
    }

    public void setIdeal(int ideal) {
        this.ideal = ideal;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
/*

    @Override
    public String toString(){
        return "orders_characters{" +
                "order_id = " + orderId +
                ", characterId = '" + characterId + '\'' +
                ", ideal = '" + ideal + '\'' +
                '}';
    }
*/

    public String toString(){
        return "orders_characters{" +
                "name = " + orderName +
                ", name = '" + characterName + '\'' +
                ", ideal = '" + ideal + '\'' +
                '}';
    }
}