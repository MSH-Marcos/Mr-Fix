package com.msh.mrfix.controllers;

import com.msh.mrfix.helpers.CityObject;
import com.msh.mrfix.models.Service;
import com.msh.mrfix.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice
@RestController
@RequestMapping("${mrfix.version}")
public class ServiceController {

    @Autowired
    ServiceRepository serviceR;

    @GetMapping("/services")
    public List<Service> list(@RequestBody CityObject city){
        return serviceR.findAllFromCity(city.getCity());
    }

    //***********Admin*****************

    @PutMapping("/admin/services/{id}")
    public ResponseEntity<Service> update(@PathVariable String id, @RequestBody Service serviceToUpdate) {
        int result = serviceR.updateService(Long.parseLong(id), serviceToUpdate.getDescription(), serviceToUpdate.getName(),
                serviceToUpdate.getPrice(), serviceToUpdate.getAvailable(), serviceToUpdate.getCity());

        if (result == 0) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<Service>(serviceToUpdate, HttpStatus.OK);
    }
    @GetMapping("/admin/services")
    public List<Service> listAdmin(){
        return serviceR.findAll();
    }

    @PostMapping("/admin/services")
    public ResponseEntity<Service> add(@RequestBody Service serviceToAdd) {
        try{
            serviceR.save(serviceToAdd);
        }catch (IllegalArgumentException e){
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<Service>(serviceToAdd, HttpStatus.OK);
    }

}
