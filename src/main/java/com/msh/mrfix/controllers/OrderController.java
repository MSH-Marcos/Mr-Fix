package com.msh.mrfix.controllers;

import com.msh.mrfix.helpers.OrderCreationHelper;
import com.msh.mrfix.models.Order;
import com.msh.mrfix.repositories.Conections;
import com.msh.mrfix.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@ControllerAdvice
@RestController
@RequestMapping("${mrfix.version}")
public class OrderController {

    @Autowired
    OrderRepository orderR;

    @Autowired
    Conections conections;

    @GetMapping("/orders/user/{id}")
    public ResponseEntity<List<Order>> list(@PathVariable String id){
        try{
            return new ResponseEntity<List<Order>>(orderR.findAllFromUser(Long.parseLong(id)), HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect get orders from user petition", ex);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> add(@RequestBody OrderCreationHelper orderToAdd) {
        Order newOrder;
        try{
            newOrder = conections.addOrder(orderToAdd);
        }catch (IllegalArgumentException e){
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
    }

    //****************Admin****************+

    @GetMapping("/orders")
    public List<Order> list(){
        return orderR.findAll();
    }

    @GetMapping("/orders/service/{id}")
    public List<Order> listFormService(@PathVariable String id){
        return orderR.findAllFromService(Long.parseLong(id));
    }
}
