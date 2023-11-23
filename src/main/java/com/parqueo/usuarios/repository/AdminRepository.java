package com.parqueo.usuarios.repository;

import com.parqueo.usuarios.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    // find all by not deleted
    @Query("SELECT a FROM Admin a WHERE a.deleted = false")
    public List<Admin> findAllByNotDeleted();

    // find all by not deleted
    @Query("SELECT a FROM Admin a WHERE a.deleted = false AND a.id = :id")
    public Admin findByIdAndNotDeleted(@PathVariable Long id);

    @Query("SELECT a FROM Admin a WHERE a.person.dni = :dni")
    public Admin findByDni(@PathVariable String dni);

    // encontrar con el usuario y contraseña un admin en la base de datos
    @Query("SELECT a FROM Admin a, Person p, User u WHERE a.person.id = p.id AND p.user.id = u.id AND u.username = :user AND u.password = :password")
    Optional<Admin> findByPasswordAndUser(String password, String user);
}
