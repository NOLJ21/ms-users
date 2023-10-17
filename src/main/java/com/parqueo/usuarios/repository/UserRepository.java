package com.parqueo.usuarios.repository;

import com.parqueo.usuarios.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    // find by username
    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User findByUsername(String username);

    // find by username and password
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    public User findByUsernameAndPassword(String username, String password);

}
