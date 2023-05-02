package com.ulascan.orderservice.controller;

import com.ulascan.orderservice.dto.OrderRequestDTO;
import com.ulascan.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequestDTO orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Places Successfully";
    }
}
