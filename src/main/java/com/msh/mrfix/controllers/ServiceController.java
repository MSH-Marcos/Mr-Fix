package com.msh.mrfix.controllers;

import com.msh.mrfix.helpers.CityObject;
import com.msh.mrfix.models.Service;
import com.msh.mrfix.repositories.ServiceRepository;
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
public class ServiceController {

    @Autowired
    ServiceRepository serviceR;

    @GetMapping("/services")
    public ResponseEntity<List<Service>> list(@RequestBody CityObject city) {
        try {
            if (city.getCity() == null)
                throw new Exception();

            return new ResponseEntity<List<Service>>(serviceR.findAllFromCity(city.getCity()), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect get service from city petition", ex);
        }
    }

    //***********Admin*****************

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/services/{id}")
    public ResponseEntity<Service> update(@PathVariable String id, @RequestBody Service serviceToUpdate) {
        try {
            serviceR.updateService(Long.parseLong(id), serviceToUpdate.getDescription(), serviceToUpdate.getName(),
                    serviceToUpdate.getPrice(), serviceToUpdate.getAvailable(), serviceToUpdate.getCity());

            return new ResponseEntity<Service>(serviceToUpdate, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect data for update", ex);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/services")
    public ResponseEntity<List<Service>> listAdmin() {
        try {
            return new ResponseEntity<List<Service>>(serviceR.findAll(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "There was an error wih the petition", ex);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/services")
    public ResponseEntity<Service> add(@RequestBody Service serviceToAdd) {
        try {
            return new ResponseEntity<Service>(serviceR.save(serviceToAdd), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Incorrect add service petition", ex);
        }
    }
}