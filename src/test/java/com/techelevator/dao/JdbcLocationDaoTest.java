package com.techelevator.dao;

import com.techelevator.model.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcLocationDaoTest extends BaseDaoTests{

    private static final Location loc1 = new Location(1, "Test1", 000001);
    private static final Location loc2 = new Location(2, "Test2", 000002);
    private static final Location loc3 = new Location(3, "Test3", 000003);
    private static final Location loc4 = new Location(4, "Test4", 000004);


    private JdbcLocationDao dao;

    @Before
    public void setUp() throws Exception{
        this.dao = new JdbcLocationDao(dataSource);
    }

    @Test
    public void getLocationById() {
        Location location = dao.getLocationById(1);
        assertLocationMatch(loc1,location);
    }

    @Test
    public void getLocations() {
        List<Location> locations = dao.getLocations();
        Assert.assertEquals(4, locations.size());
    }

    @Test
    public void getLocationByPopulation() {Location location = dao.getLocationByPopulation(000001);
        assertLocationMatch(loc1, location);
    }

    private void assertLocationMatch(Location expected, Location actual){
        Assert.assertEquals(expected.getLocation_id(), actual.getLocation_id());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getPopulation(), actual.getPopulation());
    }
}