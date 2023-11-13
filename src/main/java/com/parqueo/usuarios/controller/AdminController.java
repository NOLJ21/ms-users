package com.parqueo.usuarios.controller;

import com.parqueo.usuarios.dto.AdminDtoSpecial;
import com.parqueo.usuarios.entity.Admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parqueo.usuarios.entity.Admin;
import com.parqueo.usuarios.service.AdminService;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        try {
            return ResponseEntity.ok(adminService.findAll());
        } catch (Exception e) {
            log.error("Error al obtener la lista de administradores", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.findById(id));
        } catch (Exception e) {
            log.error("Error al obtener el administrador", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Admin> saveAdmin(@RequestBody AdminDtoSpecial adminDto) {
        try {
           Admin admin = adminService.save(adminDto);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            log.error("Error al guardar el administrador", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteById(id);
            return ResponseEntity.ok("Administrador eliminado correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar el administrador", e);
            return ResponseEntity.badRequest().body("Error al eliminar el administrador");
        }
    }

}
