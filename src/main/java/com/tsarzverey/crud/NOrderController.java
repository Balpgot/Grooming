package com.tsarzverey.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Controller
public class NOrderController {

    INOrderRepository orderRepo;
    IClientRepository clientRepo;

    public NOrderController(INOrderRepository orderRepo, IClientRepository clientRepo) {
        this.orderRepo = orderRepo;
        this.clientRepo = clientRepo;
        orderRepo.saveAndFlush(
                new NOrderDAO(
                        clientRepo.findFirstByMobilePhone("+79995359742").get(),
                        LocalDate.of(2020,6,15),
                        LocalTime.of(9,0),
                        LocalTime.of(10,0),
                        1000
                ));
        orderRepo.saveAndFlush(
                new NOrderDAO(
                        clientRepo.findFirstByMobilePhone("+79995359742").get(),
                        LocalDate.of(2020,6,15),
                        LocalTime.of(11,0),
                        LocalTime.of(12,0),
                        1000
                ));
        orderRepo.saveAndFlush(
                new NOrderDAO(
                        clientRepo.findFirstByMobilePhone("+79995359742").get(),
                        LocalDate.of(2020,6,18),
                        LocalTime.of(11,0),
                        LocalTime.of(12,0),
                        1000
                ));
    }

    @GetMapping(value = "/orders")
    public String all(Model model){
        model.addAttribute("orderList", orderRepo.findAll());
        return "orderList";
    }

    @GetMapping(value = "/orders/add")
    public String addOrderPage(Model model){
        model.addAttribute("newOrder", new NOrderDTO());
        return "orderAdd";
    }

    @PostMapping(value = "/orders/add")
    public String addOrder(NOrderDTO order){
        NOrderDAO orderDAO = new NOrderDAO();
        orderDAO.setClient(clientRepo.findFirstByMobilePhone(order.getClientPhone()).get());
        orderDAO.setDate(order.getDate());
        orderDAO.setStartTime(convertTime(order.getStartTime()));
        orderDAO.setFinishTime(convertTime(order.getFinishTime()));
        orderDAO.setPrice(Integer.parseInt(order.getPrice()));
        orderRepo.saveAndFlush(orderDAO);
        Long id = orderRepo.findFirstByClient_MobilePhoneAndDate(order.getClientPhone(), order.getDate()).get().getId();
        return "redirect:/orders/" + id;
    }

    @GetMapping(value = "/clients/{id}/orders")
    public String showClientOrders(@PathVariable Long id, Model model){
        model.addAttribute("orders", orderRepo.findAllByClient_Id(id));
        return "clientOrders";
    }

    @GetMapping(value = "/orders/{id}")
    public String orderById(@PathVariable Long id, Model model){
        model.addAttribute("order", orderRepo.findById(id).get());
        return "orderPage";
    }

    @GetMapping(value = "/orders/{id}/edit")
    public String editOrderPage(@PathVariable Long id, Model model){
        NOrderDAO order = orderRepo.findById(id).get();
        model.addAttribute("order", order);
        model.addAttribute("client", order.getClient());
        return "orderEdit";
    }

    @PostMapping(value = "/orders/{id}/edit")
    public String editOrder(@PathVariable Long id, NOrderDAO order, ClientDAO client){
        NOrderDAO oldOrder = orderRepo.findById(id).get();
        oldOrder.setClient(client);
        oldOrder.setStartTime(order.getStartTime());
        oldOrder.setFinishTime(order.getFinishTime());
        oldOrder.setDate(order.getDate());
        oldOrder.setPrice(order.getPrice());
        orderRepo.saveAndFlush(oldOrder);

        /*
             TODO
                проверка времени при изменении
                проверка номера телефона при изменении
         */
        return "redirect:/orders/"+id;
    }

    private LocalTime convertTime(String s){
        String [] timeString = s.split(":");
        return LocalTime.of(
                Integer.parseInt(timeString[0]),
                Integer.parseInt(timeString[1])
        );
    }
}