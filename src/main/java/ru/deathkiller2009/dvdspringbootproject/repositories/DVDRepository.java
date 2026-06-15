package ru.deathkiller2009.dvdspringbootproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.deathkiller2009.dvdspringbootproject.models.DVDDisk;

import java.util.List;

@Repository
public interface DVDRepository extends JpaRepository<DVDDisk, Integer> {
    List<DVDDisk> findAllByOwnerIsNull();
}
