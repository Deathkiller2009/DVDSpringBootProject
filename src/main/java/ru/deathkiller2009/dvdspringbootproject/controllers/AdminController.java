package ru.deathkiller2009.dvdspringbootproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.deathkiller2009.dvdspringbootproject.models.Person;
import ru.deathkiller2009.dvdspringbootproject.security.PersonDetails;
import ru.deathkiller2009.dvdspringbootproject.services.PeopleService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PeopleService peopleService;

    @Autowired
    public AdminController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String getAdminPage(Model model) {
        model.addAttribute("people", peopleService.findPeopleExceptAdmins());
        return "/admin/adminPage";
    }

    @PostMapping("/new")
    public String makeAdmin(HttpServletRequest request) {
        String admin = request.getParameter("newAdmin");
        int newAdminId = Integer.parseInt(admin);
        peopleService.makeNewAdmin(newAdminId);
        return "redirect:/admin";
    }
}
