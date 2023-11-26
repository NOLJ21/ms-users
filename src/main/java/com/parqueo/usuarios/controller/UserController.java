package com.parqueo.usuarios.controller;

import com.parqueo.usuarios.dto.SesionDto;
import com.parqueo.usuarios.dto.UserDto;
import com.parqueo.usuarios.entity.User;
import com.parqueo.usuarios.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.findAll());
        } catch (Exception e) {
            log.error("Error al obtener la lista de usuarios", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (Exception e) {
            log.error("Error al obtener el usuario", e);
            return ResponseEntity.badRequest().build();
        }
    }

    // obtener mediante password y usuario
    @PostMapping("/login")
    public ResponseEntity<SesionDto> getUserByUsernameAndPassword(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword()));
        } catch (Exception e) {
            log.error("Error al obtener el usuario", e);
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
        try {
            userService.save(userDto);
            return ResponseEntity.ok("Usuario guardado correctamente");
        } catch (Exception e) {
            log.error("Error al guardar el usuario", e);
            return ResponseEntity.badRequest().body("Error al guardar el usuario");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar el usuario", e);
            return ResponseEntity.badRequest().body("Error al eliminar el usuario");
        }
    }
}
