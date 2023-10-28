package com.parqueo.usuarios.service;

import com.parqueo.usuarios.dto.PersonDto;
import com.parqueo.usuarios.dto.UserDto;
import com.parqueo.usuarios.entity.Person;
import com.parqueo.usuarios.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserService userService;

    public void save(PersonDto personDto) {
        Person person = new Person();
        BeanUtils.copyProperties(personDto, person);
        person.setUser(userService.findById(personDto.getUser().getId()));
        personRepository.save(person);
    }

    public List<Person> findAll() {
        return personRepository.findAllByDeletedFalse();
    }

    public Person findById(Long id) {
        Person person = personRepository.findByIdAndDeletedFalse(id).get();
        if (person == null) {
            throw new RuntimeException("No se encontró la persona");
        }

        return person;
    }

    public void deleteById(Long id) {
        Person person = findById(id);

        person.setDeleted(true);
        personRepository.save(person);
    }
}
