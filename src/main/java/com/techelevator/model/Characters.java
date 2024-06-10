package com.techelevator.model;

public class Characters {

    private int characterId;
    private String name;
    private String gender;
    private String nationality;
    private int locationId;
    private User user;

    public Characters(){

    }

    public Characters(int characterId, String name, String gender, String nationality, int locationId, User user) {
        this.characterId = characterId;
        this.name = name;
        this.gender = gender;
        this.nationality = nationality;
        this.locationId = locationId;
        this.user = user;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User setUserById(int id){
        User user = new User();
        user.setId(id);
        return user;
    }

    @Override
    public String toString() {
        return "Characters{" +
                "characterId=" + characterId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                ", locationId=" + locationId +
                ", user=" + user +
                '}';
    }
}
