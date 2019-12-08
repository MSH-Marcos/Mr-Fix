package com.msh.mrfix.controllers;

import com.msh.mrfix.models.User;
import com.msh.mrfix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("${mrfix.version}")
public class UserController {

    @Autowired
    UserRepository userR;

    @PostMapping("/register")
    public ResponseEntity<User> add(@RequestBody User userToAdd) {
        try {
            userR.save(userToAdd);
            return new ResponseEntity<User>(userToAdd, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect register", ex);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody User userToUpdate) {
        try {
            userR.updateUser(Long.parseLong(id), userToUpdate.getAddress(), userToUpdate.getName(),
                    userToUpdate.getSurname(), userToUpdate.getEmail(), userToUpdate.getCity());

            return new ResponseEntity<User>(userToUpdate, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect data for update", ex);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> delete(@PathVariable String id) {
        try {
            userR.deleteUser(Long.parseLong(id));
            return getUser(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect delete", ex);
        }
    }

    //**************Admin********

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/users")
    public ResponseEntity<List<User>> list() {
        try {
            return new ResponseEntity<List<User>>(userR.findAllNotDeleted(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect get user list", ex);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        try {
            User response = userR.findById(Long.parseLong(id)).orElse(null);

            if (response == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesnt exist");
            else
                return new ResponseEntity<User>(response, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect get user", ex);
        }
    }
}


/*    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody User user) {
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
    }*/