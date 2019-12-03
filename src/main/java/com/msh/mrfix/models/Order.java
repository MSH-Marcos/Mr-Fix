package com.msh.mrfix.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.msh.mrfix.helpers.Helper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float price;

    @Column(name = "createdate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date creationDate;

    @Column(name = "estimatedarrive")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date arriveDate;

    @JsonBackReference(value = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonBackReference(value = "service")
    @ManyToOne(fetch = FetchType.LAZY)
    private Service service;

    @PrePersist
    public void prePersist(){
        creationDate = new Date();
        arriveDate = Helper.arriveTime(new Date(), "Adress");
    }

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String toString(){
        return "price: " +price+ " date: " +arriveDate+ " user: " + user;
    }
}
