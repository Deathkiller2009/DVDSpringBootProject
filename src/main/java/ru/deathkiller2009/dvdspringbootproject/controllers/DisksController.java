package ru.deathkiller2009.dvdspringbootproject.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.deathkiller2009.dvdspringbootproject.dto.DVDDiskDTO;
import ru.deathkiller2009.dvdspringbootproject.models.DVDDisk;
import ru.deathkiller2009.dvdspringbootproject.services.DVDService;
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
    public String getAllDisks(Model model){
        model.addAttribute("disks", dvdService.getAllDVD());
        return "disks/all";
    }

    @GetMapping("/{id}")
    public String getDiskPage(@PathVariable("id") int id, Model model){
        model.addAttribute("disk", dvdService.getDiskById(id));
        return "/disks/diskPage";
    }

    @GetMapping("/create")
    public String createDiskForm(@ModelAttribute("disk") DVDDiskDTO dvdDiskDTO){
        return "disks/create";
    }

    @PostMapping("/create")
    public String createDisk(@ModelAttribute("disk") @Valid DVDDiskDTO dvdDiskDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/disks/create";
        }
        DVDDisk disk = dvdMapper.mapToDVDDisk(dvdDiskDTO);
        dvdService.addDisk(disk);
        return "redirect:/disks";
    }
}
