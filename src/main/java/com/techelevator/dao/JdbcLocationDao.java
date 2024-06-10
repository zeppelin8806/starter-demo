package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Location;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcLocationDao implements LocationDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcLocationDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Location getLocationById(int locationId) {
        Location location = null;
        String sql = "SELECT * FROM location WHERE location_id =?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, locationId);
            if(results.next()){
                location = mapRowToLocation(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return location;
    }

    @Override
    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM location ORDER BY location_id;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()){
                Location location = mapRowToLocation(results);
                locations.add(location);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return locations;
    }

    @Override
    public Location getLocationByPopulation(int population) {
        Location location = null;
        String sql = "SELECT * FROM location WHERE population = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, population);
            if(results.next()){
                location = mapRowToLocation(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return location;
    }

    private Location mapRowToLocation(SqlRowSet results){
        Location location = new Location();
        location.setLocation_id(results.getInt("location_id"));
        location.setName(results.getString("name"));
        location.setPopulation(results.getInt("population"));
        return location;
    }


}
