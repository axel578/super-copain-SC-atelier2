package com.cpe.cardgame.service;

import com.cpe.cardgame.model.Card;
import com.cpe.cardgame.repositories.CardRepository;
import com.cpe.cardgame.utils.ResponseCode;
import com.cpe.cardgame.utils.ResponseMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository storeOrderRepository;

    public CardService(CardRepository storeOrderRepository) {
        this.storeOrderRepository = storeOrderRepository;
    }

    // Example method for updating a user
    public ResponseMessage<Card> updateCard(Card storeOrder) {
        var result =  storeOrderRepository.save(storeOrder);
        ResponseMessage<Card> responseMessage = new ResponseMessage<>(result);
        if(result == null)
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("Card not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<Card> getCard(Integer id) {
        var result =  storeOrderRepository.findById(id);
        ResponseMessage<Card> responseMessage = new ResponseMessage<>(result.get());
        if(!result.isPresent())
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("Card not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<List<Card>> getAllCard(Optional<Pageable> filter) {

        ResponseMessage<List<Card>> responseMessage = null;
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
            responseMessage.setMessage("Failed to get all Card " +(filter.isPresent()?"(with filter)":"") + " with error :"+e.getMessage());
        }
        return responseMessage;
    }


    public ResponseMessage<List<Card>> getAllCardCanBuy() {

        ResponseMessage<List<Card>> responseMessage = null;
        try {
            var data = storeOrderRepository.findAllByUserIdZero();
            responseMessage = new ResponseMessage<>(data);
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        catch (Exception e)
        {
            responseMessage = new ResponseMessage<>(new ArrayList<>());
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("Failed to get all Card that can be bought with error :"+e.getMessage());
        }
        return responseMessage;
    }

    public ResponseMessage<List<Card>> getAllCardByUserId(int user_id) {

        ResponseMessage<List<Card>> responseMessage = null;
        if(user_id==0)
        {
            responseMessage = new ResponseMessage<>(new ArrayList<>());
            responseMessage.setMessage("No cards for user id 0");
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
            return responseMessage;
        }
        try {
            var data = storeOrderRepository.findAllByUserId(user_id);
            responseMessage = new ResponseMessage<>(data);
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        catch (Exception e)
        {
            responseMessage = new ResponseMessage<>(new ArrayList<>());
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("Failed to get all Card that the user own with error :"+e.getMessage());
        }
        return responseMessage;
    }

    public ResponseMessage<List<Card>> getAllCardByUserIdAvailable(int user_id) {

        ResponseMessage<List<Card>> responseMessage = null;
        if(user_id==0)
        {
            responseMessage = new ResponseMessage<>(new ArrayList<>());
            responseMessage.setMessage("No cards for user id 0");
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
            return responseMessage;
        }
        try {
            var data = storeOrderRepository.findAllByUserIdCardAvailable(user_id);
            responseMessage = new ResponseMessage<>(data);
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        catch (Exception e)
        {
            responseMessage = new ResponseMessage<>(new ArrayList<>());
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("Failed to get all Card that the user own and are available with error :"+e.getMessage());
        }
        return responseMessage;
    }

    public ResponseMessage<Card> deleteCard(Card storeOrder) {
        storeOrderRepository.delete(storeOrder);
        var result = storeOrderRepository.findById(storeOrder.getId());

        if(!result.isPresent())
        {
            ResponseMessage<Card> responseMessage = new ResponseMessage<>(storeOrder);
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
            return responseMessage;
        }
        else
        {
            ResponseMessage<Card> responseMessage = new ResponseMessage<>(storeOrder);
            responseMessage.setMessage("Card deletion failed");
            responseMessage.setResponseCode(ResponseCode.ERROR);
            return responseMessage;
        }

    }

    public ResponseMessage<Card> deleteCard(int cardId) {
        var result = storeOrderRepository.findById(cardId);
        if(result.isPresent())
        {
            return deleteCard(result.get());
        }
        else
        {
            ResponseMessage<Card> responseMessage = new ResponseMessage<>(null);
            responseMessage.setMessage("StoreTransaction deletion failed");
            responseMessage.setResponseCode(ResponseCode.ERROR);
            return responseMessage;
        }
    }

    public ResponseMessage<List<Card>> getAllCardTransactionsForUserId(Optional<Pageable> filter, Integer userId) {

        ResponseMessage<List<Card>> responseMessage = null;
        if(userId==0)
        {
            responseMessage = new ResponseMessage<>(new ArrayList<>());
            responseMessage.setMessage("No cards for user id 0");
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
            return responseMessage;
        }
        try {
            if (filter.isPresent()) {
                var data = storeOrderRepository.findAllByUserId(userId, filter.get());
                responseMessage = new ResponseMessage<>(data.getContent());
                responseMessage.setResponseCode(ResponseCode.SUCCESS);
            } else {
                var data = storeOrderRepository.findAllByUserId(userId);
                responseMessage = new ResponseMessage<>(data);
                responseMessage.setResponseCode(ResponseCode.SUCCESS);
            }
        } catch (Exception e) {
            responseMessage = new ResponseMessage<>(new ArrayList<>());
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("Failed to get all Card transactions " + (filter.isPresent() ? "(with filter)" : "") + " with error: " + e.getMessage());
        }
        return responseMessage;
    }
}