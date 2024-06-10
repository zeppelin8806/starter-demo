package com.techelevator.dao;

import com.techelevator.model.Characters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcCharactersDaoTest extends BaseDaoTests{
    private static final Characters char1 = new Characters(1,"nameTest1", "genderTest1", "nationTest1",1, null);
    private static final Characters char2 = new Characters(2,"nameTest2", "genderTest2", "nationTest2",2, null);
    private static final Characters char3 = new Characters(3,"nameTest3", "genderTest3", "nationTest3",3, null);
    private Characters testCharacter;
    private JdbcCharactersDao dao;

    @Before
    public void setUp() throws Exception{
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcCharactersDao(jdbcTemplate);
        testCharacter = new Characters(0, "nameTest99", "genderTest99", "nationTest99", 99, null);
    }

    @Test
    public void getCharactersById() {
        Characters characters = dao.getCharactersById(1);
        assertCharactersMatch(char1, characters);

        Characters characters2 = dao.getCharactersById(2);
        assertCharactersMatch(char2, characters2);
    }

    @Test
    public void getCharacters() {
        List<Characters> charactersList = dao.getCharacters();
        Assert.assertEquals(4, charactersList.size());
    }

    @Test
    public void getCharactersByLocationId() {
        List<Characters> characters1 = dao.getCharactersByLocationId(1);
        Assert.assertEquals(1, characters1.size());
    }

    @Test
    public void deleteCharactersById() {
        dao.deleteCharactersById(1);
        Characters retrievedCharacter = dao.getCharactersById(1);
        Assert.assertNull(retrievedCharacter);
    }

    @Test
    public void updateCharacters() {
        Characters charToUpdate = dao.getCharactersById(1);

        charToUpdate.setName("Testing");
        charToUpdate.setGender("Testing1");
        charToUpdate.setNationality("TestingUSA");
        charToUpdate.setLocationId(2);

        dao.updateCharacters(charToUpdate);

        Characters retrievedChar = dao.getCharactersById(1);
        assertCharactersMatch(charToUpdate, retrievedChar);
    }

    @Test
    public void createCharacters() {
        Characters characters = dao.createCharacters(testCharacter);

        int newId = characters.getCharacterId();
        Assert.assertTrue(newId>0);

        Characters retrievedChar = dao.getCharactersById(newId);
        assertCharactersMatch(characters, retrievedChar);
    }

    private void assertCharactersMatch(Characters expected, Characters actual){
        Assert.assertEquals(expected.getCharacterId(), actual.getCharacterId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getGender(), actual.getGender());
        Assert.assertEquals(expected.getNationality(), actual.getNationality());
        Assert.assertEquals(expected.getLocationId(), actual.getLocationId());
        Assert.assertEquals(expected.getUser(), actual.getUser());
    }
}