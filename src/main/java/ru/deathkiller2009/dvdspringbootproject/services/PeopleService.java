package ru.deathkiller2009.dvdspringbootproject.services;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deathkiller2009.dvdspringbootproject.models.Person;
import ru.deathkiller2009.dvdspringbootproject.repositories.PeopleRepository;
import ru.deathkiller2009.dvdspringbootproject.util.Role;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(Person person) {
        String encode = passwordEncoder.encode(person.getPassword());
        person.setPassword(encode);
        person.setRole(Role.ROLE_USER);
        peopleRepository.save(person);
    }

    public void addAdmin(Person person) {
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        person.setRole(Role.ROLE_ADMIN);
        peopleRepository.save(person);
    }

    @Transactional(readOnly = true)
    public List<Person> findPeopleExceptAdmins() {
        return peopleRepository.findAllByRoleNot(Role.ROLE_ADMIN);
    }

    public void makeNewAdmin(int id) {
        Person person = peopleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));

        person.setRole(Role.ROLE_ADMIN);
        peopleRepository.save(person);
    }

    @Transactional(readOnly = true)
    public List<Person> findAllPeople(){
        return peopleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Person findPersonById(int id){
        return peopleRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return peopleRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public Person findPersonWithDisksById(int id) {
        return peopleRepository.findWithDisksById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));
    }

}
