package com.msh.mrfix.controllers;

import com.msh.mrfix.helpers.OrderCreationHelper;
import com.msh.mrfix.models.Order;
import com.msh.mrfix.repositories.Conections;
import com.msh.mrfix.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Order> list(@PathVariable String id){
        return orderR.findAllFromUser(Long.parseLong(id));
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
