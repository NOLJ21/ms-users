package com.parqueo.usuarios.repository;

import com.parqueo.usuarios.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface CustumerRepository extends JpaRepository<Customer, Long> {

    // find all by not deleted
    @Query("SELECT c FROM Customer c WHERE c.deleted = false")
    public List<Customer> findAllByNotDeleted();

    // find all by not deleted
    @Query("SELECT c FROM Customer c WHERE c.deleted = false AND c.person.id = :id")
    public Customer findByIdAndNotDeleted(@PathVariable Long id);

    @Query("SELECT c FROM Customer c WHERE c.person.dni = :dni")
    public Customer findByDni(@PathVariable String dni);

    // encontrar con el usuario y contraseña de un customer en la base de datos
    @Query("SELECT c FROM Customer c, Person p, User u " +
            "WHERE c.person.id = p.id AND p.user.id = u.id AND u.username = :user AND u.password = :password")
    Optional<Customer> findByPasswordAndUser(String password, String user);
}
