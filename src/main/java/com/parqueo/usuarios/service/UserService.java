package com.parqueo.usuarios.service;

import com.parqueo.usuarios.dto.SesionDto;
import com.parqueo.usuarios.dto.UserDto;
import com.parqueo.usuarios.entity.Admin;
import com.parqueo.usuarios.entity.Customer;
import com.parqueo.usuarios.entity.Employee;
import com.parqueo.usuarios.entity.User;
import com.parqueo.usuarios.repository.AdminRepository;
import com.parqueo.usuarios.repository.CustumerRepository;
import com.parqueo.usuarios.repository.EmployeeRepository;
import com.parqueo.usuarios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustumerRepository custumerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // encontrar por username y password
    public SesionDto findByUsernameAndPassword(String username, String password) {
        SesionDto sesionDto = new SesionDto();
        Optional<Customer> customer = custumerRepository.findByPasswordAndUser(password, username);
        Optional<Admin> admin = adminRepository.findByPasswordAndUser(password, username);
        Optional<Employee> employee = employeeRepository.findByPasswordAndUser(password, username);

        if(customer.isPresent()){
            sesionDto.setTipoUsuario("Customer");
            sesionDto.setPerson(customer.get().getPerson());
        }else if(admin.isPresent()){
            sesionDto.setTipoUsuario("Admin");
            sesionDto.setPerson(admin.get().getPerson());
        }else if(employee.isPresent()){
            sesionDto.setTipoUsuario("Employee");
            sesionDto.setPerson(employee.get().getPerson());
        }
        return sesionDto;
    }

    public void save(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        User user = findById(id);

        if (user != null) {
            user.setDeleted(true);
            userRepository.save(user);
        }
    }
}
