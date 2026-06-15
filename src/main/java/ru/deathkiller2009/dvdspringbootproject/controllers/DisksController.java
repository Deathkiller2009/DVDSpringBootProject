package ru.deathkiller2009.dvdspringbootproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.deathkiller2009.dvdspringbootproject.dto.DVDDiskDTO;
import ru.deathkiller2009.dvdspringbootproject.models.DVDDisk;
import ru.deathkiller2009.dvdspringbootproject.models.Person;
import ru.deathkiller2009.dvdspringbootproject.security.PersonDetails;
import ru.deathkiller2009.dvdspringbootproject.services.DVDService;
import ru.deathkiller2009.dvdspringbootproject.services.PeopleService;
import ru.deathkiller2009.dvdspringbootproject.util.DVDMapper;

@Controller
@RequestMapping("/disks")
public class DisksController {
    private final DVDService dvdService;
    private final DVDMapper dvdMapper;

    @Autowired
    public DisksController(DVDService dvdService, DVDMapper dvdMapper) {
        this.dvdService = dvdService;
        this.dvdMapper = dvdMapper;
    }

    @GetMapping
    public String getAllDisks(Model model) {
        model.addAttribute("disks", dvdService.getAllDVD());
        return "disks/all";
    }

    @GetMapping("/{id}")
    public String getDiskPage(@PathVariable("id") int id, Model model, Authentication authentication) {
        DVDDisk disk = dvdService.getDiskById(id);

        model.addAttribute("disk", disk);

        if (authentication != null && authentication.getPrincipal() instanceof PersonDetails personDetails) {
            int currentUserId = personDetails.getPerson().getId();
            boolean currentUserIsOwner = disk.getOwner() != null && disk.getOwner().getId() == currentUserId;
            model.addAttribute("currentUserIsOwner", currentUserIsOwner);
        } else {
            model.addAttribute("currentUserIsOwner", false);
        }

        return "disks/diskPage";
    }

    @GetMapping("/create")
    public String createDiskForm(@ModelAttribute("disk") DVDDiskDTO dvdDiskDTO) {
        return "disks/create";
    }

    @PostMapping("/create")
    public String createDisk(@ModelAttribute("disk") @Valid DVDDiskDTO dvdDiskDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/disks/create";
        }
        DVDDisk disk = dvdMapper.mapToDVDDisk(dvdDiskDTO);
        dvdService.addDisk(disk);
        return "redirect:/disks";
    }

    @GetMapping("/{id}/edit")
    public String editDiskForm(@PathVariable("id") int id, Model model) {
        DVDDisk disk = dvdService.getDiskById(id);
        DVDDiskDTO diskDTO = dvdMapper.mapToDVDDiskDto(disk);

        model.addAttribute("disk", diskDTO);
        model.addAttribute("diskId", id);

        return "disks/edit";
    }

    @PatchMapping("/{id}")
    public String updateDisk(@PathVariable("id") int id, @ModelAttribute("disk") @Valid DVDDiskDTO dvdDiskDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("diskId", id);
            return "disks/edit";
        }

        DVDDisk updatedDisk = dvdMapper.mapToDVDDisk(dvdDiskDTO);
        dvdService.updateDisk(id, updatedDisk);

        return "redirect:/disks/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteDisk(@PathVariable("id") int id) {
        dvdService.deleteDisk(id);
        return "redirect:/disks";
    }

    @PostMapping("/{id}/take")
    public String takeDisk(@PathVariable("id") int id, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int currentUserId = personDetails.getPerson().getId();

        dvdService.takeDiskByCurrentUser(id, currentUserId);

        return "redirect:/person";
    }

    @PostMapping("/{id}/return")
    public String returnDisk(@PathVariable("id") int id, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int currentUserId = personDetails.getPerson().getId();

        dvdService.returnDiskByCurrentUser(id, currentUserId);

        return "redirect:/person";
    }

}
