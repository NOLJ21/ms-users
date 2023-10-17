package com.parqueo.usuarios.repository;

import com.parqueo.usuarios.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    // find all by not deleted
    @Query("SELECT p FROM Person p WHERE p.deleted = false")
    public List<Person> findAllByDeletedFalse();

    @Query("SELECT p FROM Person p WHERE p.id = :id AND p.deleted = false")
    Optional<Person> findByIdAndDeletedFalse(Long id);
}
