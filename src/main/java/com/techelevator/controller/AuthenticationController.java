package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.LoginDto;
import com.techelevator.model.LoginResponseDto;
import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;
import com.techelevator.security.jwt.TokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * AuthenticationController is a class used for handling requests to authenticate Users.
 *
 * It depends on an instance of a UserDao for retrieving and storing user data. This is provided
 * through dependency injection.
 */

@RestController
@CrossOrigin
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDao userDao;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public LoginResponseDto login(@Valid @RequestBody LoginDto loginDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, false);

            User user = userDao.getUserByUsername(loginDto.getUsername());
            return new LoginResponseDto(jwt, user);
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO error - " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public User register(@Valid @RequestBody RegisterUserDto newUser) {
        try {
            User user = userDao.createUser(
                    new User(newUser.getUsername(),newUser.getPassword(), newUser.getRole(), newUser.getName(), newUser.getAddress(), newUser.getCity(), newUser.getStateCode(), newUser.getZIP())
            );
            return user;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO error - " + e.getMessage());
        }
    }
/*
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> listUsers(){
        try{
            return userDao.getUsers();
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id){
        try{
            return userDao.getUserById(id);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/users", method = RequestMethod.DELETE)
    public void deleteExistingUser(@PathVariable int id){
        try{
            int userDeleted = userDao.deleteUserById(id);
            if(userDeleted == 0){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DAO Error - " + e.getMessage());
        }
    }*/
}
