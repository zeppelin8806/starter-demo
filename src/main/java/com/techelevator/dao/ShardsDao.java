package com.techelevator.dao;

import com.techelevator.model.Shards;

import java.util.List;

public interface ShardsDao {

    Shards getShardsById(int shardbladeId);

    List<Shards> getShards();

    List<Shards> getShardsByType(String shardbladeType);
    Shards updateShards(Shards shards);
    Shards createShards(Shards shards);
    int deleteShards(int shardId);
    
}
