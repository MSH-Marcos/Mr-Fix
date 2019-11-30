package com.msh.mrfix.repositories;

import com.msh.mrfix.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Long> {

    @Query("SELECT s FROM Service s WHERE s.city = :city and s.available = true")
    public List<Service> findAllFromCity(String city);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Service s SET s.name = :name, s.description = :description, s.price = :price, s.available = :available, s.city = :city WHERE u.id = :serviceId")
    public int updateService(@Param("serviceId") long serviceId, @Param("description") String description,
                          @Param("name") String name, @Param("price") float price,
                          @Param("available") Boolean available, @Param("city") String city);
}
