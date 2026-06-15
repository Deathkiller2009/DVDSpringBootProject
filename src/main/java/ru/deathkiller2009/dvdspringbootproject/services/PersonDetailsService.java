package ru.deathkiller2009.dvdspringbootproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deathkiller2009.dvdspringbootproject.models.Person;
import ru.deathkiller2009.dvdspringbootproject.repositories.PeopleRepository;
import ru.deathkiller2009.dvdspringbootproject.security.PersonDetails;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = peopleRepository.findPersonByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователь с именем " + username + " не найден"
                ));

        return new PersonDetails(person);
    }
}
