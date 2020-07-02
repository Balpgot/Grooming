package com.tsarzverey.crud.controllers;

import com.tsarzverey.crud.repositories.INOrderRepository;
import com.tsarzverey.crud.entities.NOrderDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
        List<List<String>> ordersWeek = getOrders(weekStart,weekStart.plusDays(6));
        List<List<String>> ordersMonth = getOrdersMonth(today.withDayOfMonth(1),
                today.withDayOfMonth(1).plusMonths(1).minusDays(1));
        List<List<List<String>>> monthOrdersByWeeks = new ArrayList<>();
        int i = 0;
        List<List<String>> week = new ArrayList<>();
        for (List<String> day:ordersMonth) {
            if(i<7){
                week.add(day);
                i++;
            }
            else{
                monthOrdersByWeeks.add(week);
                week = new ArrayList<>();
                week.add(day);
                i=1;
            }
        }
        if(week.size()>0){
            monthOrdersByWeeks.add(week);
        }
        model.addAttribute("week", ordersWeek);
        model.addAttribute("month", monthOrdersByWeeks);
        return "schedulePage";
    }

    private List<List<String>> getOrders(LocalDate start, LocalDate end){
        List<List<String>> orders = new ArrayList<>();
        List<String> ordersAsString;
        while (start.isBefore(end) || start.isEqual(end)){
            ordersAsString = new ArrayList<>();
            List<NOrderDAO> dateOrders = orderRepository.findAllByDateOrderByStartTimeAsc(start);
            if(dateOrders.isEmpty()){
                ordersAsString.add(" ");
            }
            else{
                for (NOrderDAO order: dateOrders) {
                    ordersAsString.add(getOrderString(order));
                }
            }
            orders.add(ordersAsString);
            start = start.plusDays(1);
        }
        return orders;
    }

    private List<List<String>> getOrdersMonth(LocalDate start, LocalDate end){
        List<List<String>> orders = new ArrayList<>();
        List<String> ordersAsString;
        List<String> mockDays = null;
        int mockDaysCounter = 0;
        if(!start.getDayOfWeek().equals(DayOfWeek.MONDAY)){
            mockDays = new ArrayList<>();
            for(int i = 0; i<start.getDayOfWeek().getValue()-1; i++){
                mockDays.add(" ");
            }
            mockDaysCounter = mockDays.size();
        }
        while (start.isBefore(end) || start.isEqual(end)){
            ordersAsString = new ArrayList<>();
            if(mockDays != null){
                ordersAsString.addAll(mockDays);
                mockDays = null;
            }
            List<NOrderDAO> dateOrders = orderRepository.findAllByDateOrderByStartTimeAsc(start);
            if(dateOrders.isEmpty()){
                if(mockDaysCounter>0){
                    ordersAsString.add(" ");
                    mockDaysCounter--;
                }
                else {
                    ordersAsString.add(" ");
                }

            }
            else{
                for (NOrderDAO order: dateOrders) {
                    ordersAsString.add(getOrderString(order));
                }
            }
            orders.add(ordersAsString);
            start = start.plusDays(1);
        }
        return orders;
    }

    private String getOrderString(NOrderDAO order){
        StringBuilder builder = new StringBuilder();
        builder
                .append(getDateAsString(order.getDate()))
                .append("<br />")
                .append(getTimeAsString(order.getStartTime()))
                .append("-")
                .append(getTimeAsString(order.getFinishTime()))
                .append("<br />")
                .append(order.getClient().getClientName());
        return builder.toString();
    }

    private String getDateAsString(LocalDate date){
        StringBuilder builder = new StringBuilder();
        if(date.getDayOfMonth()<10){
            builder.append(0).append(date.getDayOfMonth());
        }
        else{
            builder.append(date.getDayOfMonth());
        }
        builder.append(".");
        if(date.getMonth().getValue()<10){
            builder.append(0).append(date.getMonth().getValue());
        }
        else{
            builder.append(date.getMonth().getValue());
        }
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
