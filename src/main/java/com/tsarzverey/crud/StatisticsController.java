package com.tsarzverey.crud;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.ZoneId;
import java.util.ArrayList;
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
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1);
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

    @GetMapping(value = "/statistics/clients/plots")
    public String clientPlots(@RequestParam(required = false) LocalDate start,
                              @RequestParam(required = false) LocalDate end,
                              Model model){
        Plot plot_month;
        Plot plot_year;
        Plot plot_period = null;
        plot_month = createPlot(
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1),
                "Клиенты за месяц");
        plot_year = createPlot(
                LocalDate.now().withMonth(1).withDayOfMonth(1),
                LocalDate.now().withMonth(1).withDayOfMonth(1).plusYears(1).minusDays(1),
                "Клиенты за год");
        if(start!=null && end!=null){
            plot_period = createPlot(start,end,"Клиенты за выбранный период");
        }
        model.addAttribute("clients_month", JSON.toJSON(plot_month));
        model.addAttribute("clients_year", JSON.toJSON(plot_year));
        model.addAttribute("clients_period", JSON.toJSON(plot_period));
        return "statsClientPlots";
    }

    private Plot createPlot(LocalDate start, LocalDate end, String name){
        List<String> dates = new ArrayList<>();
        List<Long> values = new ArrayList<>();
        while (start.isBefore(end)){
            dates.add(start.getDayOfMonth() + "." + start.getMonth().getValue());
            values.add(clientRepo.countAllByRegistration(start));
            start = start.plusDays(1);
        }
        return new Plot(dates,values,name);
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

    @GetMapping(value = "/statistics/pets")
    public String petStats(Model model){
        long counter = petRepo.count();
        Long counter_dogs = petRepo.countAllByType("Собака");
        model.addAttribute("counter",counter);
        model.addAttribute("counter_dogs", counter_dogs);
        model.addAttribute("counter_cats", counter - counter_dogs);
        model.addAttribute("dog_percent", ((double) counter_dogs)*100/counter);
        model.addAttribute("cat_percent", 100d - ((double) counter_dogs)*100/counter);
        return "statsPets";
    }
}
