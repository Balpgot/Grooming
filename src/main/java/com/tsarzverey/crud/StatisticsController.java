package com.tsarzverey.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
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
        Long counter_local = clientRepo.countAllByisLocalIsTrue();
        Long counter_return = clientRepo.findAll().stream().filter(x -> x.getClientOrders().size()>1).count();
        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_MONTH, 1);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, 1);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.add(Calendar.MONTH, 1);
        end.add(Calendar.DAY_OF_MONTH, -1);
        Long counter_month_new = clientRepo.findAllByRegistrationBetween(start, end);
        double local_percent = ((double)counter_local)*100/counter;
        model.addAttribute("counter",counter);
        model.addAttribute("counter_local", counter_local);
        model.addAttribute("counter_tourist", counter - counter_local);
        model.addAttribute("counter_return", counter_return);
        model.addAttribute("counter_new", counter_month_new);
        model.addAttribute("local_percent", local_percent);
        model.addAttribute("tourist_percent", 100d - local_percent);
        return "statsClients";
    }
}
