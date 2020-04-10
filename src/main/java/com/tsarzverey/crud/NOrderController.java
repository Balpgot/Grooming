package com.tsarzverey.crud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class NOrderController {

    INOrderRepository orderRepo;

    public NOrderController(INOrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping(value = "/orders")
    public String all(Model model){
        model.addAttribute("orderList", orderRepo.findAll());
        return "orderList";
    }

    @GetMapping(value = "/orders/{id}")
    public String orderById(@PathVariable Long id, Model model){
        model.addAttribute("order", orderRepo.findById(id));
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
}
