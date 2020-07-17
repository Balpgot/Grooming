package com.tsarzverey.crud.controllers;

import com.tsarzverey.crud.entities.ClientDAO;
import com.tsarzverey.crud.entities.PetDAO;
import com.tsarzverey.crud.repositories.IClientRepository;
import com.tsarzverey.crud.repositories.INOrderRepository;
import com.tsarzverey.crud.repositories.IPetRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QueryController {
    INOrderRepository orderRepo;
    IClientRepository clientRepo;
    IPetRepository petRepo;

    public QueryController(INOrderRepository orderRepo,
                           IClientRepository clientRepo,
                           IPetRepository petRepo) {
        this.orderRepo = orderRepo;
        this.clientRepo = clientRepo;
        this.petRepo = petRepo;
    }

    @GetMapping(value = "/query")
    public String loadPage(Model model) {
        model.addAttribute("pet", new PetDAO());
        return "queryPage";
    }

    @GetMapping(value = "/query/{poroda}")
    public String loadPage(@PathVariable String poroda, Model model) {
        if(!poroda.isBlank()){
            switch (poroda){
                case "1": poroda="Шпиц"; break;
                case "2": poroda="Бишон"; break;
                case "3": poroda="Йорк"; break;
                case "4": poroda="Дворняга"; break;
                case "5": poroda="Кошка"; break;
            }
            List<PetDAO> pets = petRepo.findAllByPoroda(poroda);
            List<ClientDAO> owners = new ArrayList<>();
            for (PetDAO pet:pets) {
                owners.add(pet.getClient());
            }
            model.addAttribute("clientList", owners);
        }
        else{
            model.addAttribute("clientList", new ArrayList<ClientDAO>());
        }
        return "queryResult :: clientRequestResult";
    }

    /*@PostMapping(value = "/query/{poroda}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String getResult(@PathVariable String poroda, PetDAO pet){
        System.out.println(pet);
        return "redirect:/query/"+pet.getPoroda();
    }

    @PostMapping(value = "/query", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String getResult(PetDAO pet){
        System.out.println(pet);
        return "redirect:/query/"+pet.getPoroda();
    }*/
}
