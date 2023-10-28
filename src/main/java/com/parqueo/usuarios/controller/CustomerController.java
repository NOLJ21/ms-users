package com.parqueo.usuarios.controller;

import com.parqueo.usuarios.dto.CustomerDto;
import com.parqueo.usuarios.dto.CustomerDtoSpecial;
import com.parqueo.usuarios.entity.Customer;
import com.parqueo.usuarios.service.CustomerService;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            return ResponseEntity.ok(customerService.findAll());
        } catch (Exception e) {
            log.error("Error al obtener la lista de clientes", e);
            return ResponseEntity.badRequest().build();
        }
    }
    // -t rsa github.com

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(customerService.findById(id));
        } catch (Exception e) {
            log.error("Error al obtener el cliente", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody CustomerDtoSpecial customerDto) {
        try {
           Customer customer = customerService.save(customerDto);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            log.error("Error al guardar el cliente", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteById(id);
            return ResponseEntity.ok("Cliente eliminado correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar el cliente", e);
            return ResponseEntity.badRequest().body("Error al eliminar el cliente");
        }
    }

}
