package com.techelevator.model;

public class Shards {
    private int shardbladeId;
    private String shardbladeName;
    private String shardbladeType;
    private int characterId;
    private User user;

    public Shards(){

    }

    public Shards(int shardbladeId, String shardbladeName, String shardbladeType, int characterId, User user) {
        this.shardbladeId = shardbladeId;
        this.shardbladeName = shardbladeName;
        this.shardbladeType = shardbladeType;
        this.characterId = characterId;
        this.user = user;
    }

    public int getShardbladeId() {
        return shardbladeId;
    }

    public void setShardbladeId(int shardbladeId) {
        this.shardbladeId = shardbladeId;
    }

    public String getShardbladeName() {
        return shardbladeName;
    }

    public void setShardbladeName(String shardbladeName) {
        this.shardbladeName = shardbladeName;
    }

    public String getShardbladeType() {
        return shardbladeType;
    }

    public void setShardbladeType(String shardbladeType) {
        this.shardbladeType = shardbladeType;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Shards{" +
                "shardbladeId=" + shardbladeId +
                ", shardbladeName='" + shardbladeName + '\'' +
                ", shardbladeType='" + shardbladeType + '\'' +
                ", characterId=" + characterId +
                ", user=" + user +
                '}';
    }
}
