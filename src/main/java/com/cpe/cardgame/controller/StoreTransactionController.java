package com.cpe.cardgame.controller;

import com.cpe.cardgame.model.StoreTransaction;
import com.cpe.cardgame.service.StoreTransactionService;
import com.cpe.cardgame.utils.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StoreTransactionController extends BaseController {
    private final StoreTransactionService storeTransactionService;

    public StoreTransactionController(StoreTransactionService storeTransactionService) {
        this.storeTransactionService = storeTransactionService;
    }

    @PostMapping("/storeTransaction")
    public ResponseMessage<StoreTransaction> createStoreTransaction(StoreTransaction storeTransaction) {
        return this.storeTransactionService.updateStoreTransaction(storeTransaction);
    }

    @GetMapping("/storeTransaction/{id}")
    public ResponseMessage<StoreTransaction> getStoreTransactionById(@PathVariable("id") int id) {
        return this.storeTransactionService.getStoreTransaction(id);
    }

    @DeleteMapping("/storeTransaction/{id}")
    public ResponseMessage<StoreTransaction> deleteStoreTransactionById(@PathVariable("id") int id) {
        return this.storeTransactionService.deleteStoreTransaction(id);
    }

    @GetMapping("/storeTransactions")
    public ResponseMessage<List<StoreTransaction>> getStoreTransactions() {
        return this.storeTransactionService.getAllStoreTransaction(Optional.empty());
    }

    @GetMapping("/storeTransactions/{userId}")
    public ResponseMessage<List<StoreTransaction>> getStoreTransactionsByUserId(@PathVariable Integer userId) {
        return this.storeTransactionService.getAllStoreTransactionForUserId(Optional.empty(), userId);
    }

    // Other request mappings for CRUD operations or custom queries can be defined here
}