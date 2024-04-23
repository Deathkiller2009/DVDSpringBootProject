package ru.deathkiller2009.dvdspringbootproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.deathkiller2009.dvdspringbootproject.services.DVDService;

@Controller
@RequestMapping("/disks")
public class DisksController {
    private final DVDService dvdService;
    @Autowired
    public DisksController(DVDService dvdService) {
        this.dvdService = dvdService;
    }
    @GetMapping
    public String getAllDisks(Model model){
        model.addAttribute("disks", dvdService.getAllDVD());
        return "disks/all";
    }
}
