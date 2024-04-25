package ru.deathkiller2009.dvdspringbootproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.deathkiller2009.dvdspringbootproject.models.Person;
import ru.deathkiller2009.dvdspringbootproject.security.PersonDetails;
import ru.deathkiller2009.dvdspringbootproject.services.PeopleService;

@Controller
@RequestMapping("/person")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String getAccountPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails principal = (PersonDetails) authentication.getPrincipal();
        Person person = principal.getPerson();
        model.addAttribute("person", person);
        return "/auth/personalAccount";
    }
}
