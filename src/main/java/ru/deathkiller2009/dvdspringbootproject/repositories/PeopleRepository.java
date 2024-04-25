package ru.deathkiller2009.dvdspringbootproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.deathkiller2009.dvdspringbootproject.models.Person;
import ru.deathkiller2009.dvdspringbootproject.util.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findPersonByUsername(String username);

    List<Person> findAllByRoleNot(Role role);
}
