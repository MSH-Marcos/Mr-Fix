package com.msh.mrfix.repositories;

import com.msh.mrfix.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
