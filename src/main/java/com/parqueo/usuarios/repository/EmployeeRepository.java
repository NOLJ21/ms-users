package com.parqueo.usuarios.repository;

import com.parqueo.usuarios.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // find all by not deleted
    @Query("SELECT e FROM Employee e WHERE e.deleted = false")
    public List<Employee> findAllByNotDeleted();

    // find all by not deleted
    @Query("SELECT e FROM Employee e WHERE e.deleted = false AND e.id = :id")
    public Employee findByIdAndNotDeleted(@PathVariable Long id);

    @Query("SELECT e FROM Employee e WHERE e.person.dni = :dni")
    public Employee findByDni(@PathVariable String dni);

    // encontrar con el usuario y contraseña de un customer en la base de datos
    @Query("SELECT e FROM Employee e, Person p, User u WHERE e.person.id = p.id AND p.user.id = u.id AND u.username = :user AND u.password = :password")
    Optional<Employee> findByPasswordAndUser(String password, String user);
}
