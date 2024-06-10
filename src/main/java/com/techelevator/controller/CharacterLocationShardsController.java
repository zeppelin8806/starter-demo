package com.techelevator.controller;

import com.techelevator.dao.CharactersDao;
import com.techelevator.dao.LocationDao;
import com.techelevator.dao.ShardsDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Characters;
import com.techelevator.model.Location;
import com.techelevator.model.Shards;
import com.techelevator.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class CharacterLocationShardsController {

    private CharactersDao charactersDao;
    private LocationDao locationDao;
    private ShardsDao shardsDao;


    public CharacterLocationShardsController(CharactersDao charactersDao, LocationDao locationDao, ShardsDao shardsDao){
        this.charactersDao = charactersDao;
        this.locationDao = locationDao;
        this.shardsDao = shardsDao;
    }

    /*
    ------------
    START OF CHARACTER API
    ------------
    */

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/characters", method = RequestMethod.GET)
    public List<Characters> listCharacters(){
        try{
            return charactersDao.getCharacters();
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }

    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/characters/{id}", method = RequestMethod.GET)
    public Characters characterById(@PathVariable int id){
        try{
            return charactersDao.getCharactersById(id);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }

    }


    /*
    * Will require authentication
    */

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/characters/{id}", method = RequestMethod.PUT)
    public Characters updateCharacterById(@Valid @RequestBody Characters changedCharacter, @PathVariable int id, Principal principal){
        try{
            User user = new User();
            user.setUsername(principal.getName());
            changedCharacter.setCharacterId(id);
            changedCharacter.setUser(user);
            return charactersDao.updateCharacters(changedCharacter);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }

    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/characters", method = RequestMethod.POST)
    public Characters addCharacter(@Valid @RequestBody Characters character, Principal principal){
        try{
            User user = new User();
            user.setUsername(principal.getName());
            character.setUser(user);
            return charactersDao.createCharacters(character);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/characters/{id}", method = RequestMethod.DELETE)
    public void deleteExistingCharacter(@PathVariable int id){
        try{
            int characterDeleted = charactersDao.deleteCharactersById(id);
            if(characterDeleted == 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }
    /*
    End of required authentication
    */


     /*
    ------------
    END OF CHARACTER API
    ------------
    */
     /*
    ------------
    START OF LOCATION API
    ------------
    */

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<Location> listLocations(){
        try{
            return locationDao.getLocations();
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }

    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/location/{id}", method = RequestMethod.GET)
    public Location locationbyid(@PathVariable int id){
        try{
            return locationDao.getLocationById(id);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }
     /*
    ------------
    END OF LOCATION API
    ------------
    */

     /*
    ------------
    START OF SHARDS API
    ------------
    */

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/shards", method = RequestMethod.GET)
    public List<Shards> listShards(){
        return shardsDao.getShards();
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/shards/{id}", method = RequestMethod.GET)
    public Shards shardById(@PathVariable int id){
        return shardsDao.getShardsById(id);
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/shards/search", method = RequestMethod.GET)
    public List<Shards> shardByType(@RequestParam String type){
        return shardsDao.getShardsByType(type);
    }

    /*
     * Will require authentication
     */

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/shards/{id}", method = RequestMethod.PUT)
    public Shards updateShardById(@Valid @RequestBody Shards changedShard, @PathVariable int id, Principal principal){
        try{
            User user = new User();
            user.setUsername(principal.getName());
            changedShard.setCharacterId(id);
            changedShard.setUser(user);

            changedShard.setShardbladeId(id);
            return shardsDao.updateShards(changedShard);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }

    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/shards", method = RequestMethod.POST)
    public Shards addShards(@Valid @RequestBody Shards shard, Principal principal){
        try{
            User user = new User();
            user.setUsername(principal.getName());
            shard.setUser(user);

            return shardsDao.createShards(shard);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }

    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/shards/{id}", method = RequestMethod.DELETE)
    public void deleteShard(@PathVariable int id){
        try{
            int shardDeleted = shardsDao.deleteShards(id);
            if(shardDeleted == 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }
    /*
     * End of required authentication
     */

     /*
    ------------
    END OF SHARDS API
    ------------
    */

}
