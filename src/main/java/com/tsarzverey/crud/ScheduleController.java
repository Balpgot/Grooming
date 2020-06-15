package com.tsarzverey.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
public class ScheduleController {
    private INOrderRepository orderRepository;

    public ScheduleController(INOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping(value = "/shedule")
    public String schedulePage(Model model){
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Moscow"));
        LocalDate weekStart = today.minusDays(today.getDayOfWeek().getValue()-1);
        HashMap<String,List<NOrderDAO>> ordersWeek = getOrders(weekStart,weekStart.plusDays(6));
        HashMap<String,List<NOrderDAO>> ordersMonth = getOrders(today.withDayOfMonth(1),
                today.withDayOfMonth(1).plusMonths(1).minusDays(1));
        model.addAttribute("week", ordersWeek);
        model.addAttribute("month", ordersMonth);
        return "schedulePage";
    }

    private HashMap<String,List<NOrderDAO>> getOrders(LocalDate start, LocalDate end){
        HashMap<String,List<NOrderDAO>> orders = new HashMap<>();
        while (start.isBefore(end)){
            orders.put(start.getDayOfMonth()+"."+start.getMonth().getValue(),
                    orderRepository.findAllByDate(start));
            start = start.plusDays(1);
        }
        return orders;
    }
}
