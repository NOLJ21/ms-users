package com.parqueo.usuarios.repository;

import com.parqueo.usuarios.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CustumerRepository extends JpaRepository<Customer, Long> {

    // find all by not deleted
    @Query("SELECT c FROM Customer c WHERE c.deleted = false")
    public List<Customer> findAllByNotDeleted();

    // find all by not deleted
    @Query("SELECT c FROM Customer c WHERE c.deleted = false AND c.id = :id")
    public Customer findByIdAndNotDeleted(@PathVariable Long id);

    @Query("SELECT c FROM Customer c WHERE c.person.dni = :dni")
    public Customer findByDni(@PathVariable String dni);
}
