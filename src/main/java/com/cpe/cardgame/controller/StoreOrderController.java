package com.cpe.cardgame.controller;

import com.cpe.cardgame.model.StoreOrder;
import com.cpe.cardgame.model.UserGame;
import com.cpe.cardgame.service.StoreOrderService;
import com.cpe.cardgame.service.UserService;
import com.cpe.cardgame.utils.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StoreOrderController  extends BaseController{
    private final StoreOrderService storeOrderService;

    public StoreOrderController(StoreOrderService storeOrderService) {
        this.storeOrderService = storeOrderService;
    }

    @PostMapping("/storeOrder")
    public ResponseMessage<StoreOrder> createStoreOrder(StoreOrder storeOrder) {
        return this.storeOrderService.updateStoreOrder(storeOrder);
    }

    @GetMapping("/storeOrder/{id}")
    public ResponseMessage<StoreOrder> getStoreOrderById(@PathVariable("id") int id) {
        return this.storeOrderService.getStoreOrder(id);
    }

    @DeleteMapping("/storeOrder/{id}")
    public ResponseMessage<StoreOrder> deleteStoreOrderById(@PathVariable("id") int id) {
        return this.storeOrderService.deleteStoreOrder(id);
    }

    @GetMapping("/storeOrders")
    public ResponseMessage<List<StoreOrder>> getStores() {
        return this.storeOrderService.getAllStoreOrder(Optional.empty());
    }
    @GetMapping("/storeOrders/{userId}")
    public ResponseMessage<List<StoreOrder>> getStoreOrdersByUserId(@PathVariable Integer userId) {
        return this.storeOrderService.getAllStoreOrderForUserId(Optional.empty(), userId);
    }
    // Other request mappings for CRUD operations or custom queries can be defined here
}