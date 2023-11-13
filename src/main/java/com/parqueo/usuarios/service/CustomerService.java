package com.parqueo.usuarios.service;

import com.parqueo.usuarios.dto.CustomerDtoSpecial;
import com.parqueo.usuarios.entity.Customer;
import com.parqueo.usuarios.entity.Person;
import com.parqueo.usuarios.entity.User;
import com.parqueo.usuarios.repository.CustumerRepository;
import com.parqueo.usuarios.repository.PersonRepository;
import com.parqueo.usuarios.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustumerRepository custumerRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    public Customer save(CustomerDtoSpecial customerDto) {
        Customer customer = new Customer();
        Person person = new Person();
        User user = new User();
        Optional<User> userOptional = userRepository.findByUsernameAndPasswordOptional
                (customerDto.getPerson().getUser().getUsername(), customerDto.getPerson().getUser().getPassword());


        // verificar si existe el usuario
        if(!userOptional.isPresent()) {
            log.info("No existe el usuario, se creará uno nuevo");
            log.info("Usuario: {}", customerDto.getPerson().getUser().getUsername());
            log.info("Contraseña: {}", customerDto.getPerson().getUser().getPassword());
            user.setUsername(customerDto.getPerson().getUser().getUsername());
            user.setPassword(customerDto.getPerson().getUser().getPassword());
            user.setDeleted(false);
            userRepository.save(user);
            userOptional = userRepository.findByUsernameAndPasswordOptional
                    (customerDto.getPerson().getUser().getUsername(), customerDto.getPerson().getUser().getPassword());
            user = userOptional.get();
        } else{
            user = userOptional.get();
        }
        // con getter y setters, primero person
        person.setName(customerDto.getPerson().getName());
        person.setSurname(customerDto.getPerson().getSurname());
        person.setDni(customerDto.getPerson().getDni());
        person.setEmail(customerDto.getPerson().getEmail());
        person.setPhone(customerDto.getPerson().getPhone());
        person.setUser(user);
        person.setDeleted(false);
        personRepository.save(person);

        // luego customer
        customer.setPerson(person);
        customer.setDeleted(false);
        custumerRepository.save(customer);
        Customer customer1 = custumerRepository.findByDni(customerDto.getPerson().getDni());

        return customer1;
    }

    public List<Customer> findAll() {
        return custumerRepository.findAllByNotDeleted();
    }

    public Customer findById(Long id) {
        Customer customer = new Customer();
        try {
            customer = custumerRepository.findByIdAndNotDeleted(id);
        } catch (Exception e) {
            throw new RuntimeException("No se encontró el cliente");
        }

        return customer;
    }

    public void deleteById(Long id) {
        Customer customer = findById(id);

        customer.setDeleted(true);
        custumerRepository.save(customer);
    }
}
