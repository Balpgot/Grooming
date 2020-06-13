package com.tsarzverey.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Calendar;

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
        Long counter_month_new = clientRepo.countAllByRegistrationBetween(start, end);
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

    @GetMapping(value = "/statistics/orders")
    public String orderStats(Model model){
        Long counter = orderRepo.count();
        Long counter_local = orderRepo.countAllByClient_isLocalIsTrue();
        LocalDate start = LocalDate.now();
        start = start.withDayOfMonth(1);
        LocalDate end = start.plusMonths(1);
        end = end.minusDays(1);
        Long counter_month = orderRepo.countAllByDateBetween(start, end);
        double local_percent = ((double)counter_local)*100/counter;
        model.addAttribute("counter",counter);
        model.addAttribute("counter_local", counter_local);
        model.addAttribute("counter_tourist", counter - counter_local);
        model.addAttribute("counter_new", counter_month);
        model.addAttribute("local_percent", local_percent);
        model.addAttribute("tourist_percent", 100d - local_percent);
        return "statsOrders";
    }

    @GetMapping(value = "/statistics/finance")
    public String financeStats(Model model){
        int counter = orderRepo.findAll()
                .stream()
                .map(NOrderDAO::getPrice)
                .mapToInt(Integer::valueOf)
                .sum();
        LocalDate start = LocalDate.now();
        start = start.withDayOfMonth(1);
        LocalDate end = start.plusMonths(1);
        end = end.minusDays(1);
        int counter_month = orderRepo.findAllByDateBetween(start, end)
                .stream()
                .map(NOrderDAO::getPrice)
                .mapToInt(Integer::valueOf)
                .sum();
        model.addAttribute("counter",counter);
        model.addAttribute("counter_month", counter_month);
        return "statsFinance";
    }
}
