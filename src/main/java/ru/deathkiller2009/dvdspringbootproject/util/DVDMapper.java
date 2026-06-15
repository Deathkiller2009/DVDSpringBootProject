package ru.deathkiller2009.dvdspringbootproject.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.deathkiller2009.dvdspringbootproject.dto.DVDDiskDTO;
import ru.deathkiller2009.dvdspringbootproject.models.DVDDisk;

@Component
public class DVDMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public DVDMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DVDDiskDTO mapToDVDDiskDto(DVDDisk dvdDisk) {
        return modelMapper.map(dvdDisk, DVDDiskDTO.class);
    }

    public DVDDisk mapToDVDDisk(DVDDiskDTO dvdDiskDTO) {
        return modelMapper.map(dvdDiskDTO, DVDDisk.class);
    }
}
