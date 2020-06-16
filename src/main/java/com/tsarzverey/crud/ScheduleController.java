package com.tsarzverey.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

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
        List<List<String>> ordersWeek = getOrders(weekStart,weekStart.plusDays(6));
        List<List<String>> ordersMonth = getOrdersMonth(today.withDayOfMonth(1),
                today.withDayOfMonth(1).plusMonths(1).minusDays(1));
        model.addAttribute("week", ordersWeek);
        model.addAttribute("month", ordersMonth);
        return "schedulePage";
    }

    private List<List<String>> getOrders(LocalDate start, LocalDate end){
        List<List<String>> orders = new ArrayList<>();
        List<String> ordersAsString;
        while (start.isBefore(end) || start.isEqual(end)){
            ordersAsString = new ArrayList<>();
            List<NOrderDAO> dateOrders = orderRepository.findAllByDateOrderByStartTimeAsc(start);
            if(dateOrders.isEmpty()){
                ordersAsString.add("Заказов нет");
            }
            else{
                for (NOrderDAO order: dateOrders) {
                    ordersAsString.add(getOrderString(order));
                }
            }
            orders.add(ordersAsString);
            start = start.plusDays(1);
        }
        System.out.println(orders);
        return orders;
    }

    private List<List<String>> getOrdersMonth(LocalDate start, LocalDate end){
        List<List<String>> orders = new ArrayList<>();
        List<String> ordersAsString;
        List<String> mockDays = null;
        if(!start.getDayOfWeek().equals(DayOfWeek.MONDAY)){
            mockDays = new ArrayList<>();
            for(int i = 0; i<start.getDayOfWeek().getValue()-1; i++){
                mockDays.add(" ");
            }
        }
        while (start.isBefore(end) || start.isEqual(end)){
            ordersAsString = new ArrayList<>();
            if(mockDays != null){
                ordersAsString.addAll(mockDays);
                mockDays = null;
            }
            List<NOrderDAO> dateOrders = orderRepository.findAllByDateOrderByStartTimeAsc(start);
            if(dateOrders.isEmpty()){
                ordersAsString.add("Заказов нет");
            }
            else{
                for (NOrderDAO order: dateOrders) {
                    System.out.println(order);
                    ordersAsString.add(getOrderString(order));
                }
            }
            orders.add(ordersAsString);
            start = start.plusDays(1);
        }
        System.out.println(orders);
        return orders;
    }

    private String getOrderString(NOrderDAO order){
        StringBuilder builder = new StringBuilder();
        builder
                .append(getTimeAsString(order.getStartTime()))
                .append("-")
                .append(getTimeAsString(order.getFinishTime()))
                .append(" ")
                .append(order.getClient().getClientName());
        return builder.toString();
    }

    private String getTimeAsString(LocalTime time){
        StringBuilder builder = new StringBuilder();
        if(time.getHour()<10){
            builder.append(0).append(time.getHour());
        }
        else{
            builder.append(time.getHour());
        }
        builder.append(":");
        if(time.getMinute()<10){
            builder.append(0).append(time.getMinute());
        }
        else{
            builder.append(time.getMinute());
        }
        return builder.toString();
    }
}
