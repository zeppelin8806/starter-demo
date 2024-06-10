package com.techelevator.dao;

import com.techelevator.model.Characters;

import java.util.List;

public interface CharactersDao {

    Characters getCharactersById(int characterId);

    List<Characters> getCharacters();

    List<Characters> getCharactersByNationality(String nationality);

    List<Characters> getCharactersByLocationId(int locationId);

    int deleteCharactersById(int characterId);

    /**
     *
     * @param characters
     * @return
     */
    Characters updateCharacters(Characters characters);
    Characters createCharacters(Characters characters);
}
