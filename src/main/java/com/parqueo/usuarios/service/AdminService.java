package com.parqueo.usuarios.service;

import com.parqueo.usuarios.dto.AdminDtoSpecial;
import com.parqueo.usuarios.entity.Admin;
import com.parqueo.usuarios.entity.Person;
import com.parqueo.usuarios.entity.User;
import com.parqueo.usuarios.repository.AdminRepository;
import com.parqueo.usuarios.repository.PersonRepository;
import com.parqueo.usuarios.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    public Admin save(AdminDtoSpecial adminDto) {
        Admin admin = new Admin();
        Person person = new Person();
        User user = new User();
        Optional<User> userOptional = userRepository.findByUsernameAndPasswordOptional
                (adminDto.getPerson().getUser().getUsername(), adminDto.getPerson().getUser().getPassword());

        //verificar si existe el usuario
        if (!userOptional.isPresent()) {
            log.info("No existe el usuario, se creará uno nuevo");
            log.info("Usuario: {}", adminDto.getPerson().getUser().getUsername());
            log.info("Contraseña: {}", adminDto.getPerson().getUser().getPassword());
            user.setUsername(adminDto.getPerson().getUser().getUsername());
            user.setPassword(adminDto.getPerson().getUser().getPassword());
            user.setDeleted(false);
            userRepository.save(user);
            userOptional = userRepository.findByUsernameAndPasswordOptional
                    (adminDto.getPerson().getUser().getUsername(), adminDto.getPerson().getUser().getPassword());
            user = userOptional.get();
        } else {
            user = userOptional.get();
        }
        // con getter y setters, primero person
        person.setName(adminDto.getPerson().getName());
        person.setSurname(adminDto.getPerson().getSurname());
        person.setDni(adminDto.getPerson().getDni());
        person.setEmail(adminDto.getPerson().getEmail());
        person.setPhone(adminDto.getPerson().getPhone());
        person.setUser(user);
        person.setDeleted(false);
        personRepository.save(person);

        // luego admin
        admin.setPerson(person);
        admin.setDeleted(false);
        adminRepository.save(admin);
        Admin admin1 = adminRepository.findByDni(adminDto.getPerson().getDni());

        return admin1;
    }

    public List<Admin> findAll() {return adminRepository.findAllByNotDeleted();}

    public Admin findById(Long id) {
        Admin admin = new Admin();
        try {
            admin = adminRepository.findByIdAndNotDeleted(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar el administrador");
        }

        return admin;
    }
    public void deleteById(Long id) {
        Admin admin = findById(id);

        admin.setDeleted(true);
        adminRepository.save(admin);
    }
}
