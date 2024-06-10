package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Shards;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcShardsDao implements ShardsDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcShardsDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Shards getShardsById(int shardbladeId) {
        Shards shards = null;
        String sql = "SELECT * FROM shards WHERE shardblade_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, shardbladeId);
            if(results.next()){
                shards = mapRowToShards(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return shards;
    }

    @Override
    public List<Shards> getShards() {
        List<Shards> shards = new ArrayList<>();
        String sql = "SELECT * FROM shards ORDER BY shardblade_type;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                Shards shard = mapRowToShards(results);
                shards.add(shard);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return shards;
    }

    @Override
    public List<Shards> getShardsByType(String type) {
        List<Shards> shards = new ArrayList<>();
        String sql = "SELECT * FROM shards WHERE shardblade_type ILIKE '%?%';";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, type);
            while(results.next()){
                Shards shard = mapRowToShards(results);
                shards.add(shard);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return shards;
    }

    @Override
    public Shards updateShards(Shards shards) {
        Shards updatedshards = null;
        String sql = "UPDATE shards SET shardblade_name = ?, shardblade_type = ?, character_id = ?"
                + " WHERE shardblade_id = ?;";
        try{
            int numberOfRows = jdbcTemplate.update(sql, shards.getShardbladeName(), shards.getShardbladeType(), shards.getCharacterId(), shards.getShardbladeId());
            if(numberOfRows ==0){
                throw new DaoException("Zero rows affected, expected at least one");
            } else{
                updatedshards = getShardsById(shards.getShardbladeId());
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return updatedshards;
    }

    @Override
    public Shards createShards(Shards shards) {
        Shards newShards = null;
        String sql = "INSERT INTO shards (shardblade_name, shardblade_type, character_id) "
                +"VALUES (?, ?, ?) RETURNING shardblade_id;";
        try{
            int newShardsId = jdbcTemplate.queryForObject(sql, int.class, shards.getShardbladeName(), shards.getShardbladeType(), shards.getCharacterId());

            newShards = getShardsById(newShardsId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return newShards;
    }

    @Override
    public int deleteShards(int shardId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM shards WHERE shardblade_id = ?;";
        try{
            numberOfRows = jdbcTemplate.update(sql, shardId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Shards mapRowToShards(SqlRowSet results){
        Shards shards = new Shards();
        shards.setShardbladeId(results.getInt("shardblade_id"));
        shards.setShardbladeName(results.getString("shardblade_name"));
        shards.setShardbladeType(results.getString("shardblade_type"));
        shards.setCharacterId(results.getInt("character_id"));
        return shards;
    }
}
