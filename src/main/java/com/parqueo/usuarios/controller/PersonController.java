package com.parqueo.usuarios.controller;

import com.parqueo.usuarios.dto.PersonDto;
import com.parqueo.usuarios.entity.Person;
import com.parqueo.usuarios.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/all")
    public ResponseEntity<List<Person>> getAllPersons() {
        try {
            return ResponseEntity.ok(personService.findAll());
        } catch (Exception e) {
            log.error("Error al obtener la lista de personas", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(personService.findById(id));
        } catch (Exception e) {
            log.error("Error al obtener la persona", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> savePerson(@RequestBody PersonDto personDto) {
        try {
            personService.save(personDto);
            return ResponseEntity.ok("Persona guardada correctamente");
        } catch (Exception e) {
            log.error("Error al guardar la persona", e);
            return ResponseEntity.badRequest().body("Error al guardar la persona");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        try {
            personService.deleteById(id);
            return ResponseEntity.ok("Persona eliminada correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar la persona", e);
            return ResponseEntity.badRequest().body("Error al eliminar la persona");
        }
    }

}
