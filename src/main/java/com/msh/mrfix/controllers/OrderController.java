package com.msh.mrfix.controllers;

import com.msh.mrfix.helpers.OrderCreationHelper;
import com.msh.mrfix.models.Order;
import com.msh.mrfix.repositories.Conections;
import com.msh.mrfix.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@PreAuthorize("authenticated")
@RestController
@RequestMapping("${mrfix.version}")
public class OrderController {

    @Autowired
    OrderRepository orderR;

    @Autowired
    Conections conections;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/orders/user/{id}")
    public ResponseEntity<List<Order>> list(@PathVariable String id) {
        try {
            return new ResponseEntity<List<Order>>(orderR.findAllFromUser(Long.parseLong(id)), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect get orders from user petition", ex);
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/orders")
    public ResponseEntity<Order> add(@RequestBody OrderCreationHelper orderToAdd) {
        try {
            return new ResponseEntity<Order>(conections.addOrder(orderToAdd), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect parameter for posting order", ex);
        }
    }

    //****************Admin****************+

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/orders")
    public ResponseEntity<List<Order>> list() {
        try {
            return new ResponseEntity<List<Order>>(orderR.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect get orders petition", ex);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/orders/service/{id}")
    public ResponseEntity<List<Order>> listFormService(@PathVariable String id) {
        try {
            return new ResponseEntity<List<Order>>(orderR.findAllFromService(Long.parseLong(id)), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect get orders from service petition", ex);
        }
    }
}
