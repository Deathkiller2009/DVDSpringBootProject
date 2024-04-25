package ru.deathkiller2009.dvdspringbootproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deathkiller2009.dvdspringbootproject.models.DVDDisk;
import ru.deathkiller2009.dvdspringbootproject.repositories.DVDRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DVDService {
    private final DVDRepository dvdRepository;
    @Autowired
    public DVDService(DVDRepository dvdRepository) {
        this.dvdRepository = dvdRepository;
    }

    public List<DVDDisk> getAllDVD(){
        return dvdRepository.findAll();
    }
    @Transactional
    public void addDisk(DVDDisk dvdDisk){
        dvdRepository.save(dvdDisk);
    }

    public DVDDisk getDiskById(int id){
        return dvdRepository.findById(id).orElse(null);
    }
}
