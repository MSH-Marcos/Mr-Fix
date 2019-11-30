package com.msh.mrfix.repositories;

import com.msh.mrfix.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :id ")
    public List<Order> findAllFromUser(Long id);

    @Query("SELECT o FROM Order o WHERE o.service.id = :id ")
    public List<Order> findAllFromService(Long id);
}
