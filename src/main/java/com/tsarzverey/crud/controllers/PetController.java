package com.tsarzverey.crud.controllers;

import com.tsarzverey.crud.repositories.IPetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
public class PetController {

    IPetRepository petRepo;

    @Autowired
    public PetController(IPetRepository petRepo) {
        this.petRepo = petRepo;
    }

    @GetMapping(value = "/pets")
    public String all(Model model){
        model.addAttribute("petList", petRepo.findAll());
        return "petList";
    }

}
