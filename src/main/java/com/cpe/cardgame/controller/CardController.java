package com.cpe.cardgame.controller;

import com.cpe.cardgame.model.Card;
import com.cpe.cardgame.model.StoreTransaction;
import com.cpe.cardgame.service.CardService;
import com.cpe.cardgame.service.StoreTransactionService;
import com.cpe.cardgame.service.UserService;
import com.cpe.cardgame.utils.ResponseCode;
import com.cpe.cardgame.utils.ResponseMessage;
import com.cpe.cardgame.viewmodel.AuthDTO;
import com.cpe.cardgame.viewmodel.CardForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CardController extends BaseController {
    private final CardService cardService;
    private final UserService userService;

    public CardController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @PostMapping("/card")
    public ResponseMessage<Card> createCard(Card card) {
        return cardService.updateCard(card);
    }

    @GetMapping("/card/{id}")
    public ResponseMessage<Card> getCardById(@PathVariable("id") int id) {
        return cardService.getCard(id);
    }



    @PostMapping("/buy-card/{cardId}")
    public ResponseMessage<Card> buyCardByUser(
            @PathVariable("cardId") int cardid,
            HttpServletRequest httprequest
            )
    {

        int userId = GetByUser(httprequest);
        var user = userService.getUser(userId);
        var card = cardService.getCard(cardid);
        if(!card.isSuccess())
        {
            return card;
        }
        if(!user.isSuccess())
        {
            ResponseMessage<Card> responseMessage = new ResponseMessage<>(card.getResponse());
            responseMessage.setResponseCode(user.getResponseCode());
            responseMessage.setMessage(user.getMessage());
            return responseMessage;
        }
        if(user.getResponse().getId()==card.getResponse().getUserId())
        {
            ResponseMessage<Card> responseMessage = new ResponseMessage<>(card.getResponse());
            responseMessage.setResponseCode(ResponseCode.FORBIDDEN);
            responseMessage.setMessage("You already own this item !");
            return responseMessage;
        }
        if(user.getResponse().getAccount()<card.getResponse().getPrice())
        {
            ResponseMessage<Card> responseMessage = new ResponseMessage<>(card.getResponse());
            responseMessage.setResponseCode(ResponseCode.FORBIDDEN);
            responseMessage.setMessage("You don't have enough balance on your account to buy this item !");
            return responseMessage;
        }
        else
        {
            var new_user = user.getResponse();
            var new_card = card.getResponse();

            if(new_card.getUserId()!=0)
            {
                var r_user = userService.getUser(new_card.getUserId());
                if(r_user.isSuccess())
                {
                    r_user.getResponse().setAccount(r_user.getResponse().getAccount()+new_card.getPrice());
                    var upd = userService.updateUser(r_user.getResponse());
                }

            }
            new_user.setAccount(new_user.getAccount()-new_card.getPrice());
            new_card.setUserId(new_user.getId());
            new_card.setToSell(Boolean.FALSE);
            var result_user = userService.updateUser(new_user);

            if(!result_user.isSuccess())
            {
                ResponseMessage<Card> responseMessage = new ResponseMessage<>(new_card);
                responseMessage.setResponseCode(result_user.getResponseCode());
                responseMessage.setMessage(result_user.getMessage());
                return responseMessage;
            }
            var result_card = cardService.updateCard(new_card);
            if(!result_card.isSuccess())
            {
                return result_card;
            }

            ResponseMessage<Card> responseMessage = new ResponseMessage<>(result_card.getResponse());
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
            return responseMessage;
        }
    }

    @PostMapping("/sell-card/{cardId}")
    public ResponseMessage<Card> sellCardByUser(
            @PathVariable("cardId") int cardid,
            HttpServletRequest httprequest
    )
    {
        var data = httprequest.getSession().getAttribute("USER");
        if(data == null)
        {
            ResponseMessage<Card> responseMessage = new ResponseMessage<>(null);
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("Not authentificated");
            return responseMessage;
        }
        var id = (Integer)data;
        if(id == null)
        {
            ResponseMessage<Card> responseMessage = new ResponseMessage<>(null);
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("Not authentificated");
            return responseMessage;
        }
        int userId = id;
        var user = userService.getUser(userId);
        var card = cardService.getCard(cardid);
        if(!card.isSuccess())
        {
            return card;
        }
        if(!user.isSuccess())
        {
            ResponseMessage<Card> responseMessage = new ResponseMessage<>(card.getResponse());
            responseMessage.setResponseCode(user.getResponseCode());
            responseMessage.setMessage(user.getMessage());
            return responseMessage;
        }
        if(user.getResponse().getId()==card.getResponse().getUserId())
        {
            card.getResponse().setToSell(Boolean.TRUE);
            return cardService.updateCard(card.getResponse());
        }
        else
        {
            ResponseMessage<Card> responseMessage = new ResponseMessage<>(card.getResponse());
            responseMessage.setResponseCode(ResponseCode.FORBIDDEN);
            responseMessage.setMessage("You are not the card owner");
            return responseMessage;
        }

    }

    @DeleteMapping("/card/{id}")
    public ResponseMessage<Card> deleteCardById(@PathVariable("id") int id) {
        return cardService.deleteCard(id);
    }

    @GetMapping("/cards")
    public ResponseMessage<List<Card>> getAllCards() {
        return cardService.getAllCard(Optional.empty());
    }

    @GetMapping("/cards-of-user")
    public ResponseMessage<List<Card>> getAllCardsByUserId(HttpServletRequest httprequest) {
        var user = GetByUser(httprequest);
        return cardService.getAllCardByUserId(user);
    }

    @GetMapping("/cards-of-user/available/{user_id}")
    public ResponseMessage<List<Card>> getAllCardsByUserIdAvailable(int user_id) {

        return cardService.getAllCardByUserIdAvailable(user_id);
    }
    private List<CardForm> convertToCardForms(List<Card> cards) {
        List<CardForm> cardForms = new ArrayList<>();
        for (Card card : cards) {
            CardForm cardForm = card.toCardForm();
            cardForms.add(cardForm);
        }
        return cardForms;
    }
    @GetMapping("/cards-buyable")
    public ResponseMessage<List<Card>> getAllCardsBuyable() {
        ResponseMessage<List<Card>> cardForms = new ResponseMessage<>(
                /*convertToCardForms(*/cardService.getAllCardCanBuy().getResponse()/*)*/
        );
        cardForms.setResponseCode(ResponseCode.SUCCESS);
        return cardForms;
    }

    @GetMapping("/cards/user/{userId}")
    public ResponseMessage<List<Card>> getCardsByUserId(@PathVariable("userId") Integer userId) {
        return cardService.getAllCardTransactionsForUserId(Optional.empty(), userId);
    }

    @GetMapping("/buy-card")
    public ResponseMessage<List<CardForm>> buyCardList(HttpServletRequest request) {
        var user = GetByUser(request);
        if (user == 0) {
            ResponseMessage<List<CardForm>> listResponseMessage = new ResponseMessage<>(null);
            listResponseMessage.setResponseCode(ResponseCode.ERROR);
            listResponseMessage.setMessage("Error your are not logged in");
            return listResponseMessage;
        }

        var cardList = this.cardService.getAllCardCanBuy();
        List<CardForm> cardViewModelList = new ArrayList<>();

        for (Card card : cardList.getResponse()) {
            cardViewModelList.add(card.toCardForm());
        }
        ResponseMessage<List<CardForm>> listResponseMessage = new ResponseMessage<>(cardViewModelList);
        listResponseMessage.setResponseCode(ResponseCode.SUCCESS);
        return listResponseMessage;
    }


}