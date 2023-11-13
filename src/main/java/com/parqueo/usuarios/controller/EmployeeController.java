package com.parqueo.usuarios.controller;

import com.parqueo.usuarios.dto.EmployeeDtoSpecial;
import com.parqueo.usuarios.entity.Employee;
import com.parqueo.usuarios.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            return ResponseEntity.ok(employeeService.findAll());
        } catch (Exception e) {
            log.error("Error al obtener la lista de empleados", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(employeeService.findById(id));
        } catch (Exception e) {
            log.error("Error al obtener el empleado", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody EmployeeDtoSpecial employeeDto) {
        try {
           Employee employee = employeeService.save(employeeDto);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            log.error("Error al guardar el empleado", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteById(id);
            return ResponseEntity.ok("Empleado eliminado correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar el empleado", e);
            return ResponseEntity.badRequest().body("Error al eliminar el empleado");
        }
    }
}
