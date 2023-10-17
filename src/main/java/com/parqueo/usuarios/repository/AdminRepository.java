package com.parqueo.usuarios.repository;

import com.parqueo.usuarios.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("SELECT a FROM Admin a WHERE a.deleted = false")
    public List<Admin> findAllByNotDeleted();
}
