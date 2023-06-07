package com.cpe.cardgame.service;

import com.cpe.cardgame.model.StoreTransaction;
import com.cpe.cardgame.repositories.StoreTransactionRepository;
import com.cpe.cardgame.utils.ResponseCode;
import com.cpe.cardgame.utils.ResponseMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreTransactionService {
    private final StoreTransactionRepository storeOrderRepository;

    public StoreTransactionService(StoreTransactionRepository storeOrderRepository) {
        this.storeOrderRepository = storeOrderRepository;
    }

    // Example method for updating a user
    public ResponseMessage<StoreTransaction> updateStoreTransaction(StoreTransaction storeOrder) {
        var result =  storeOrderRepository.save(storeOrder);
        ResponseMessage<StoreTransaction> responseMessage = new ResponseMessage<>(result);
        if(result == null)
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("StoreTransaction not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<StoreTransaction> getStoreTransaction(Integer id) {
        var result =  storeOrderRepository.findById(id);
        ResponseMessage<StoreTransaction> responseMessage = new ResponseMessage<>(result.get());
        if(!result.isPresent())
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("StoreTransaction not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<List<StoreTransaction>> getAllStoreTransaction(Optional<Pageable> filter) {

        ResponseMessage<List<StoreTransaction>> responseMessage = null;
        try {
            if(filter.isPresent())
            {
                var data = storeOrderRepository.findAll(filter.get());
                responseMessage = new ResponseMessage<>(data.getContent());
                responseMessage.setResponseCode(ResponseCode.SUCCESS);
            }
            else
            {
                var data = storeOrderRepository.findAll();
                responseMessage = new ResponseMessage<>(data);
                responseMessage.setResponseCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e)
        {
            responseMessage = new ResponseMessage<>(new ArrayList<>());
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("Failed to get all StoreTransaction " +(filter.isPresent()?"(with filter)":"") + " with error :"+e.getMessage());
        }
        return responseMessage;
    }

    public ResponseMessage<StoreTransaction> deleteStoreTransaction(StoreTransaction storeOrder) {
        storeOrderRepository.delete(storeOrder);
        var result = storeOrderRepository.findById(storeOrder.getId());

        if(!result.isPresent())
        {
            ResponseMessage<StoreTransaction> responseMessage = new ResponseMessage<>(storeOrder);
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
            return responseMessage;
        }
        else
        {
            ResponseMessage<StoreTransaction> responseMessage = new ResponseMessage<>(storeOrder);
            responseMessage.setMessage("StoreTransaction deletion failed");
            responseMessage.setResponseCode(ResponseCode.ERROR);
            return responseMessage;
        }

    }

    public ResponseMessage<StoreTransaction> deleteStoreTransaction(int storeOrder) {
        var result = storeOrderRepository.findById(storeOrder);
        if(result.isPresent())
        {
            return deleteStoreTransaction(result.get());
        }
        else
        {
            ResponseMessage<StoreTransaction> responseMessage = new ResponseMessage<>(null);
            responseMessage.setMessage("StoreTransaction deletion failed");
            responseMessage.setResponseCode(ResponseCode.ERROR);
            return responseMessage;
        }
    }

    public ResponseMessage<List<StoreTransaction>> getAllStoreTransactionForUserId(Optional<Pageable> filter, Integer userId) {

        ResponseMessage<List<StoreTransaction>> responseMessage = null;
        try {
            if(filter.isPresent())
            {
                var data = storeOrderRepository.findAllByUserId(userId, filter.get());
                responseMessage = new ResponseMessage<>(data.getContent());
                responseMessage.setResponseCode(ResponseCode.SUCCESS);
            }
            else
            {
                var data = storeOrderRepository.findAllByUserId(userId);
                responseMessage = new ResponseMessage<>(data);
                responseMessage.setResponseCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e)
        {
            responseMessage = new ResponseMessage<>(new ArrayList<>());
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("Failed to get all StoreTransaction " +(filter.isPresent()?"(with filter)":"") + " with error :"+e.getMessage());
        }
        return responseMessage;
    }


    // Other methods for CRUD operations or custom queries can be defined here
}