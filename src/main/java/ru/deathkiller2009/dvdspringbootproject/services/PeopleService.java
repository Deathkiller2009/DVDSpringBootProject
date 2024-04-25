package ru.deathkiller2009.dvdspringbootproject.services;

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

    public void addUser(Person person){
        String encode = passwordEncoder.encode(person.getPassword());
        person.setPassword(encode);
        person.setRole(Role.ROLE_USER);
        peopleRepository.save(person);
    }

    public List<Person> findPeopleExceptAdmins(){
        return peopleRepository.findAllByRoleNot(Role.ROLE_ADMIN);
    }

    public void makeNewAdmin(int id){
        Person person = peopleRepository.findById(id).get();
        person.setRole(Role.ROLE_ADMIN);
        peopleRepository.save(person);
    }
}
