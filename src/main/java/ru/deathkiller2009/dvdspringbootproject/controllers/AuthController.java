package ru.deathkiller2009.dvdspringbootproject.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.deathkiller2009.dvdspringbootproject.dto.PersonDTO;
import ru.deathkiller2009.dvdspringbootproject.models.Person;
import ru.deathkiller2009.dvdspringbootproject.services.PeopleService;
import ru.deathkiller2009.dvdspringbootproject.util.PersonMapper;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonMapper personMapper;
    private final PeopleService peopleService;

    @Autowired
    public AuthController(PersonMapper personMapper, PeopleService peopleService) {
        this.personMapper = personMapper;
        this.peopleService = peopleService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("personDTO") PersonDTO personDTO) {
        return "/auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("personDTO") @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/auth/register";
        }
        Person person = personMapper.mapToPerson(personDTO);
        peopleService.addUser(person);
        return "redirect:/auth/login";
    }
}
