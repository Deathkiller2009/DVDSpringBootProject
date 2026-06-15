package ru.deathkiller2009.dvdspringbootproject.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.deathkiller2009.dvdspringbootproject.dto.PersonDTO;
import ru.deathkiller2009.dvdspringbootproject.models.Person;

@Component
public class PersonMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public PersonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PersonDTO mapToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    public Person mapToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
