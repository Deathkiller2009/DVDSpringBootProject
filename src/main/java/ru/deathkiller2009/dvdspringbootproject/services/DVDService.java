package ru.deathkiller2009.dvdspringbootproject.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deathkiller2009.dvdspringbootproject.models.DVDDisk;
import ru.deathkiller2009.dvdspringbootproject.models.Person;
import ru.deathkiller2009.dvdspringbootproject.repositories.DVDRepository;
import ru.deathkiller2009.dvdspringbootproject.repositories.PeopleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DVDService {
    private final DVDRepository dvdRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public DVDService(DVDRepository dvdRepository, PeopleRepository peopleRepository) {
        this.dvdRepository = dvdRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<DVDDisk> getAllDVD() {
        return dvdRepository.findAllByOwnerIsNull();
    }

    public List<DVDDisk> getAllDisksForAdmin() {
        return dvdRepository.findAll();
    }

    @Transactional
    public void addDisk(DVDDisk dvdDisk) {
        dvdRepository.save(dvdDisk);
    }

    public DVDDisk getDiskById(int id) {
        return dvdRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Диск с id=" + id + " не найден"));
    }

    @Transactional
    public void updateDisk(int id, DVDDisk updatedDisk) {
        DVDDisk existingDisk = dvdRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Диск с id=" + id + " не найден"));

        existingDisk.setTitle(updatedDisk.getTitle());
        existingDisk.setAuthor(updatedDisk.getAuthor());
        existingDisk.setReleaseDate(updatedDisk.getReleaseDate());
        existingDisk.setAnnotation(updatedDisk.getAnnotation());

        dvdRepository.save(existingDisk);
    }

    @Transactional
    public void deleteDisk(int id) {
        DVDDisk disk = dvdRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Диск с id=" + id + " не найден"));

        dvdRepository.delete(disk);
    }

    @Transactional
    public void takeDiskByCurrentUser(int diskId, int currentUserId) {
        DVDDisk dvdDisk = dvdRepository.findById(diskId)
                .orElseThrow(() -> new EntityNotFoundException("Диск с id=" + diskId + " не найден"));

        if (dvdDisk.getOwner() != null) {
            throw new IllegalStateException("Диск уже занят другим пользователем");
        }

        Person currentUser = peopleRepository.findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + currentUserId + " не найден"));

        dvdDisk.setOwner(currentUser);

        dvdRepository.save(dvdDisk);
    }

    @Transactional
    public void returnDiskByCurrentUser(int diskId, int currentUserId) {
        DVDDisk dvdDisk = dvdRepository.findById(diskId)
                .orElseThrow(() -> new EntityNotFoundException("Диск с id=" + diskId + " не найден"));

        if (dvdDisk.getOwner() == null) {
            throw new IllegalStateException("Диск уже свободен");
        }

        if (dvdDisk.getOwner().getId() != currentUserId) {
            throw new AccessDeniedException("Нельзя вернуть диск, который принадлежит другому пользователю");
        }

        dvdDisk.setOwner(null);

        dvdRepository.save(dvdDisk);
    }

    public boolean isDiskOwner(int diskId, int userId) {
        DVDDisk dvdDisk = dvdRepository.findById(diskId)
                .orElseThrow(() -> new EntityNotFoundException("Диск с id=" + diskId + " не найден"));

        return dvdDisk.getOwner() != null && dvdDisk.getOwner().getId() == userId;
    }



}
