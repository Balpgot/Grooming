package com.tsarzverey.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class StatisticsController {
    IClientRepository clientRepo;
    IPetRepository petRepo;
    INOrderRepository orderRepo;

    public StatisticsController(IClientRepository clientRepo, IPetRepository petRepo, INOrderRepository orderRepo) {
        this.clientRepo = clientRepo;
        this.petRepo = petRepo;
        this.orderRepo = orderRepo;
    }

    @GetMapping(value = "/statistics")
    public String nav(){
        return "statsNav";
    }

    @GetMapping(value = "/statistics/clients")
    public String clientStats(Model model){
        Long counter = clientRepo.count();
        Long counter_local = clientRepo.countAllByLocalIsTrue();
        model.addAttribute("counter",clientRepo.count());
        model.addAttribute("counter_local", clientRepo.countAllByLocalIsTrue());
        model.addAttribute("counter_tourist", counter - counter_local);
        return "statsClients";
    }
}
