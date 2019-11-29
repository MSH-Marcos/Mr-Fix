package com.msh.mrfix.repositories;

import com.msh.mrfix.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
