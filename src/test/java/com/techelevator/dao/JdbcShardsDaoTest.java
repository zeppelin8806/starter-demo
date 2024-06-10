package com.techelevator.dao;

import com.techelevator.model.Shards;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;

public class JdbcShardsDaoTest extends BaseDaoTests{

    private static final Shards shard1 = new Shards(1, "nameTest1", "typeTest1", 1, null);
    private static final Shards shard2 = new Shards(2, "nameTest2", "typeTest1", 2, null);
    private static final Shards shard3 = new Shards(3, "nameTest3", "typeTest2", 3, null);
    private static final Shards shard4 = new Shards(4, "nameTest4", "typeTest2", 4, null);

    private JdbcShardsDao dao;
    private Shards testShard;

    @Before
    public void setUp() throws Exception{
        this.dao = new JdbcShardsDao(dataSource);
        testShard = new Shards(0, "nameTest99", "typeTest99", 99, null);
    }

    @Test
    public void getShardsById() {
        Shards shard = dao.getShardsById(1);
        assertShardsMatch(shard1, shard);
    }

    @Test
    public void getShards() {
        List<Shards> shards = dao.getShards();
        Assert.assertEquals(4, shards.size());
    }

    @Test
    public void updateShards() {
        Shards shardToUpdate = dao.getShardsById(1);

        shardToUpdate.setShardbladeName("Testing");
        shardToUpdate.setShardbladeType("AnotherTest");
        shardToUpdate.setCharacterId(1);

        dao.updateShards(shardToUpdate);

        Shards retrievedShard = dao.getShardsById(1);
        assertShardsMatch(shardToUpdate, retrievedShard);
    }

    @Test
    public void createShards() {
        Shards shardCreated = dao.createShards(testShard);

        int newId = shardCreated.getCharacterId();
        Assert.assertTrue(newId>0);

        Shards retrievedShard = dao.getShardsById(newId);
        assertShardsMatch(shardCreated, retrievedShard);
    }

    @Test
    public void deleteShards() {
        dao.deleteShards(1);
        Shards retrievedShard = dao.getShardsById(1);
        Assert.assertNull(retrievedShard);
    }
    private void assertShardsMatch(Shards expected, Shards actual){
        Assert.assertEquals(expected.getShardbladeId(), actual.getShardbladeId());
        Assert.assertEquals(expected.getShardbladeName(), actual.getShardbladeName());
        Assert.assertEquals(expected.getShardbladeType(), actual.getShardbladeType());
        Assert.assertEquals(expected.getCharacterId(), actual.getCharacterId());
    }
}