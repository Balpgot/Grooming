package com.tsarzverey.crud.controllers;

import com.tsarzverey.crud.entities.ClientDAO;
import com.tsarzverey.crud.repositories.IClientRepository;
import com.tsarzverey.crud.repositories.IPetRepository;
import com.tsarzverey.crud.entities.PetDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.ZoneId;

@Controller
@Slf4j
public class ClientController {

    IClientRepository clientRepo;
    IPetRepository petRepo;

    @Autowired
    public ClientController(IClientRepository clientRepo, IPetRepository petRepo) {
        this.clientRepo = clientRepo;
        this.petRepo = petRepo;
        this.clientRepo.saveAndFlush(
                new ClientDAO(
                        "Иван",
                        "+79995359742",
                        true,
                        LocalDate.of(2020,6, 14)
                )
        );
        this.petRepo.saveAndFlush(
                new PetDAO(
                        "Рекс",
                        clientRepo.findFirstByMobilePhone("+79995359742").get(),
                        "Ласковый",
                        "Собака",
                        "Шпиц"
                )
        );
    }

    @GetMapping(value = "/")
    public String nav(){
        return "nav";
    }

    @GetMapping(value = "/clients")
    public String all(Model model){
        model.addAttribute("clientList", clientRepo.findAll());
        Pageable page = PageRequest.of(0,10, Sort.by(Sort.Direction.ASC,"id"));
        return "clientList";
    }

    @GetMapping(value = "/clients/{id}")
    public String clientPage(@PathVariable Long id, Model model){
        model.addAttribute("client", clientRepo.findById(id).get());
        model.addAttribute("petList", petRepo.findAllByClient(clientRepo.findById(id).get()));
        model.addAttribute("newPet", new PetDAO());
        return "clientPage";
    }

    @PostMapping(value = "/clients/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addPetToClient(@PathVariable Long id, PetDAO pet){
        pet.setClient(clientRepo.findById(id).get());
        petRepo.saveAndFlush(pet);
        return "redirect:/clients/"+id;
    }

    @GetMapping("/clients/add")
    public String addClientPage(Model model){
        model.addAttribute("newClient", new ClientDAO());
        return "clientAdd";
    }

    @GetMapping("/clients/{id}/edit")
    public String editClientPage(@PathVariable Long id, Model model){
        model.addAttribute("client", clientRepo.findById(id).get());
        return "clientEdit";
    }

    @PostMapping(value = "/clients/{id}/edit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String replaceClient(@PathVariable Long id, ClientDAO client) {
        ClientDAO oldClient = clientRepo.findById(id).get();
        oldClient.setClientName(client.getClientName());
        oldClient.setMobilePhone(client.getMobilePhone());
        oldClient.setLocal(client.isLocal());
        clientRepo.saveAndFlush(oldClient);
        return "redirect:/clients/"+id;
    }

    @PostMapping(value = "/clients/add",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addClient(ClientDAO client){
        client.setRegistration(LocalDate.now(ZoneId.of("Europe/Moscow")));
        clientRepo.save(client);
        clientRepo.flush();
        return "redirect:/clients";
    }
}


