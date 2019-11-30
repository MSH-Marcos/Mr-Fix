package com.msh.mrfix.repositories;

import com.msh.mrfix.helpers.Helper;
import com.msh.mrfix.helpers.OrderCreationHelper;
import com.msh.mrfix.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public class Conections {

    @Autowired
    OrderRepository orderR;

    @Autowired
    UserRepository userR;

    @Autowired
    ServiceRepository serviceR;

    public Order addOrder(OrderCreationHelper o){
        Order newOrder = new Order();

        newOrder.setUser(userR.findById(o.getUserId()).orElse(null));
        newOrder.setService(serviceR.findById(o.getServiceId()).orElse(null));
        newOrder.setPrice(Helper.calculatePrice(newOrder.getService().getPrice(), newOrder.getUser().getCity()));

        return orderR.save(newOrder);
    }
}
