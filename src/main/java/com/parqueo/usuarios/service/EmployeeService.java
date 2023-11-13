package com.parqueo.usuarios.service;

import com.parqueo.usuarios.dto.EmployeeDtoSpecial;
import com.parqueo.usuarios.entity.Employee;
import com.parqueo.usuarios.entity.Person;
import com.parqueo.usuarios.entity.User;
import com.parqueo.usuarios.repository.EmployeeRepository;
import com.parqueo.usuarios.repository.PersonRepository;
import com.parqueo.usuarios.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    public Employee save(EmployeeDtoSpecial employeeDto) {
        Employee employee = new Employee();
        Person person = new Person();
        User user = new User();
        Optional<User> userOptional = userRepository.findByUsernameAndPasswordOptional
                (employeeDto.getPerson().getUser().getUsername(), employeeDto.getPerson().getUser().getPassword());


        //verificar si existe el usuario
        if (!userOptional.isPresent()) {
            log.info("No existe el usuario, se creará uno nuevo");
            log.info("Usuario: {}", employeeDto.getPerson().getUser().getUsername());
            log.info("Contraseña: {}", employeeDto.getPerson().getUser().getPassword());
            user.setUsername(employeeDto.getPerson().getUser().getUsername());
            user.setPassword(employeeDto.getPerson().getUser().getPassword());
            user.setDeleted(false);
            userRepository.save(user);
            userOptional = userRepository.findByUsernameAndPasswordOptional
                    (employeeDto.getPerson().getUser().getUsername(), employeeDto.getPerson().getUser().getPassword());
            user = userOptional.get();
        } else {
            user = userOptional.get();
        }
        // con getter y setters, primero person
        person.setName(employeeDto.getPerson().getName());
        person.setSurname(employeeDto.getPerson().getSurname());
        person.setDni(employeeDto.getPerson().getDni());
        person.setEmail(employeeDto.getPerson().getEmail());
        person.setPhone(employeeDto.getPerson().getPhone());
        person.setUser(user);
        person.setDeleted(false);
        personRepository.save(person);

        // luego employee
        employee.setPerson(person);
        employee.setDeleted(false);
        employeeRepository.save(employee);
        Employee employee1 = employeeRepository.findByDni(employeeDto.getPerson().getDni());

        return employee1;
    }

    public List<Employee> findAll() {return employeeRepository.findAllByNotDeleted();}

    public Employee findById(Long id) {
        Employee employee = new Employee();
        try {
            employee = employeeRepository.findByIdAndNotDeleted(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar el empleado");
        }

        return employee;
    }

    public void deleteById(Long id) {
        Employee employee = findById(id);

        employee.setDeleted(true);
        employeeRepository.save(employee);
    }
}
