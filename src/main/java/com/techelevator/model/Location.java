package com.techelevator.model;

public class Location {
    private int locationId;
    private String name;
    private int population;

    public Location(){

    }

    public Location(int locationId, String name, int population) {
        this.locationId = locationId;
        this.name = name;
        this.population = population;
    }

    public int getLocation_id() {
        return locationId;
    }

    public void setLocation_id(int location_id) {
        this.locationId = location_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "location{" +
                "location_id = " + locationId +
                ", name = '" + name + '\'' +
                ", population = '" + population + '\'' +
                '}';
    }
}
