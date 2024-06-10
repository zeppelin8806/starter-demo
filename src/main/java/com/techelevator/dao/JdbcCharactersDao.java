package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Characters;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCharactersDao implements CharactersDao{

    private JdbcTemplate jdbcTemplate;
    public JdbcCharactersDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
/*

    public JdbcCharactersDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
*/

    @Override
    public Characters getCharactersById(int characterId) {
        Characters character = null;
        String sql = "SELECT * FROM characters WHERE character_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, characterId);
            if(results.next()){
                character = mapRowToCharacters(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return character;
    }

    @Override
    public List<Characters> getCharacters() {
        List<Characters> characters = new ArrayList<>();
        String sql = "SELECT * FROM characters ORDER BY name;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                Characters character = mapRowToCharacters(results);
                characters.add(character);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return characters;
    }

    @Override
    public List<Characters> getCharactersByNationality(String nationality) {
        List<Characters> characters = new ArrayList<>();
        String sql = "SELECT * FROM characters WHERE nationality = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, nationality);
            while(results.next()){
                Characters character = mapRowToCharacters(results);
                characters.add(character);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return characters;
    }

    @Override
    public List<Characters> getCharactersByLocationId(int locationId) {
        List<Characters> characters = new ArrayList<>();
        String sql = "SELECT * FROM characters WHERE location_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, locationId);
            while(results.next()){
                Characters character = mapRowToCharacters(results);
                characters.add(character);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return characters;
    }

    @Override
    public int deleteCharactersById(int characterId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM characters WHERE character_id = ?;";
        String updateShard = "UPDATE shards SET character_id = 0 WHERE character_id = ?;";
        String updateOrdersCharacters = "UPDATE orders_characters SET character_id = 0 WHERE character_id = ?;";

        try{
            jdbcTemplate.update(updateOrdersCharacters, characterId);
            jdbcTemplate.update(updateShard,characterId);
            numberOfRows = jdbcTemplate.update(sql, characterId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    @Override
    public Characters updateCharacters(Characters characters) {
        Characters updatedCharacters = null;
        String sql = "UPDATE characters SET name = ?, gender = ?, nationality = ?, location_id = ?"
                    + " WHERE character_id = ?;";

        try{
            int numberOfRows = jdbcTemplate.update(sql, characters.getName(), characters.getGender(), characters.getNationality(), characters.getLocationId(), characters.getCharacterId());
            if(numberOfRows == 0){
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedCharacters = getCharactersById(characters.getCharacterId());
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return updatedCharacters;
    }

    @Override
    public Characters createCharacters(Characters characters) {
        Characters newCharacters = null;
        String sql = "INSERT INTO characters (name, gender, nationality, location_id) "
                    + "VALUES (?, ?, ?, ?) RETURNING character_id;";
        try{
            int newCharacterId = jdbcTemplate.queryForObject(sql, int.class, characters.getName(), characters.getGender(), characters.getNationality(), characters.getLocationId());
            newCharacters = getCharactersById(newCharacterId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return newCharacters;
    }

    private Characters mapRowToCharacters(SqlRowSet results){
        Characters characters = new Characters();
        characters.setCharacterId(results.getInt("character_id"));
        characters.setName(results.getString("name"));
        characters.setGender(results.getString("gender"));
        characters.setNationality(results.getString("nationality"));
        characters.setLocationId(results.getInt("location_id"));
        characters.setUserById(results.getInt("user_id"));
        return characters;
    }
}
