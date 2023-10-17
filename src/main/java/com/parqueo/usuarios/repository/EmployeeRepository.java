package com.parqueo.usuarios.repository;

import com.parqueo.usuarios.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // find all by not deleted
    @Query("SELECT e FROM Employee e WHERE e.deleted = false")
    public List<Employee> findAllByNotDeleted();
}
