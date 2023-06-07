package com.cpe.cardgame.service;
import org.springframework.data.domain.Pageable;

import com.cpe.cardgame.model.StoreOrder;
import com.cpe.cardgame.repositories.StoreOrderRepository;
import com.cpe.cardgame.utils.ResponseCode;
import com.cpe.cardgame.utils.ResponseMessage;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreOrderService {
    private final StoreOrderRepository storeOrderRepository;

    public StoreOrderService(StoreOrderRepository storeOrderRepository) {
        this.storeOrderRepository = storeOrderRepository;
    }

    // Example method for updating a user
    public ResponseMessage<StoreOrder> updateStoreOrder(StoreOrder storeOrder) {
        var result =  storeOrderRepository.save(storeOrder);
        ResponseMessage<StoreOrder> responseMessage = new ResponseMessage<>(result);
        if(result == null)
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("StoreOrder not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<StoreOrder> getStoreOrder(Integer id) {
        var result =  storeOrderRepository.findById(id);
        ResponseMessage<StoreOrder> responseMessage = new ResponseMessage<>(result.get());
        if(!result.isPresent())
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("StoreOrder not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<List<StoreOrder>> getAllStoreOrder(Optional<Pageable> filter) {

        ResponseMessage<List<StoreOrder>> responseMessage = null;
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
            responseMessage.setMessage("Failed to get all StoreOrder " +(filter.isPresent()?"(with filter)":"") + " with error :"+e.getMessage());
        }
        return responseMessage;
    }

    public ResponseMessage<List<StoreOrder>> getAllStoreOrderForUserId(Optional<Pageable> filter, Integer userId) {

        ResponseMessage<List<StoreOrder>> responseMessage = null;
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
            responseMessage.setMessage("Failed to get all StoreOrder " +(filter.isPresent()?"(with filter)":"") + " with error :"+e.getMessage());
        }
        return responseMessage;
    }

    public ResponseMessage<StoreOrder> deleteStoreOrder(StoreOrder storeOrder) {
        storeOrderRepository.delete(storeOrder);
        var result = storeOrderRepository.findById(storeOrder.getId());

        if(!result.isPresent())
        {
            ResponseMessage<StoreOrder> responseMessage = new ResponseMessage<>(storeOrder);
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
            return responseMessage;
        }
        else
        {
            ResponseMessage<StoreOrder> responseMessage = new ResponseMessage<>(storeOrder);
            responseMessage.setMessage("StoreOrder deletion failed");
            responseMessage.setResponseCode(ResponseCode.ERROR);
            return responseMessage;
        }

    }

    public ResponseMessage<StoreOrder> deleteStoreOrder(int storeOrder) {
        var result = storeOrderRepository.findById(storeOrder);
        if(result.isPresent())
        {
            return deleteStoreOrder(result.get());
        }
        else
        {
            ResponseMessage<StoreOrder> responseMessage = new ResponseMessage<>(null);
            responseMessage.setMessage("StoreOrder deletion failed");
            responseMessage.setResponseCode(ResponseCode.ERROR);
            return responseMessage;
        }
    }



    // Other methods for CRUD operations or custom queries can be defined here
}