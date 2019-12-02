package com.msh.mrfix.controllers;

import com.msh.mrfix.models.User;
import com.msh.mrfix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@ControllerAdvice
@RestController
@RequestMapping("${mrfix.version}")
public class UserController {

    @Autowired
    UserRepository userR;

    @GetMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody User user){
        try {
            User responseUser = userR.login(user.getName(), user.getPassword());
            if (responseUser != null)
                return new ResponseEntity<User>(responseUser, HttpStatus.OK);
            else
                throw new Exception();
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Incorrect nick/password", ex);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> add(@RequestBody User userToAdd) {
        try{
            userR.save(userToAdd);
            return new ResponseEntity<User>(userToAdd, HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Incorrect login", ex);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody User userToUpdate) {
        int result = userR.updateUser(Long.parseLong(id), userToUpdate.getAddress(), userToUpdate.getName(),
                userToUpdate.getSurname(), userToUpdate.getEmail(), userToUpdate.getCity());

        if (result == 0) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<User>(userToUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> delete(@PathVariable String id) {
        int result = userR.deleteUser(Long.parseLong(id));

        if (result != 1) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<User>(getUser(id), HttpStatus.OK);
    }

    //**************Admin********

    @GetMapping("/users")
    public List<User> list(){
        return userR.findAllNotDeleted();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id){
        return userR.findById(Long.parseLong(id)).orElse(null);
    }
}
