package com.msh.mrfix.repositories;

import com.msh.mrfix.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.deleted = false ")
    public List<User> findAllNotDeleted();

    @Query("SELECT u FROM User u WHERE u.name = :name and u.password = :password and u.deleted = false ")
    public User login(@Param("name") String name, @Param("password") String password);

    @Query("SELECT u FROM User u WHERE u.name = :name and u.deleted = false ")
    public User findUserByName(@Param("name") String name);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE User u SET u.address = :address, u.name = :name, u.surname = :surname, u.email = :email, u.city = :city WHERE u.id = :userId")
    public int updateUser(@Param("userId") long userId, @Param("address") String address,
                   @Param("name") String name, @Param("surname") String surname,
                   @Param("email") String email, @Param("city") String city);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE User u SET u.deleted = true WHERE u.id = :userId")
    public int deleteUser(@Param("userId") long userId);
}
