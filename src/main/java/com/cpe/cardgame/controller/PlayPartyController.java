package com.cpe.cardgame.controller;

import com.cpe.cardgame.model.Card;
import com.cpe.cardgame.model.PlayParty;
import com.cpe.cardgame.service.CardService;
import com.cpe.cardgame.service.PlayPartyService;
import com.cpe.cardgame.service.UserService;
import com.cpe.cardgame.utils.ResponseCode;
import com.cpe.cardgame.utils.ResponseMessage;
import com.cpe.cardgame.viewmodel.AuthDTO;
import com.cpe.cardgame.viewmodel.dto.PlayPartyViewModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PlayPartyController extends BaseController {
    private final PlayPartyService playPartyService;
    private final UserService userService;
    private final CardService cardService;

    public PlayPartyController(PlayPartyService playPartyService, UserService userService, CardService cardService) {
        this.playPartyService = playPartyService;
        this.userService = userService;
        this.cardService = cardService;
    }

    @PostMapping("/play-party")
    public ResponseMessage<PlayParty> createCard(PlayParty card) {
        return playPartyService.updatePlayParty(card);
    }

    @PostMapping("/party-player-fight/{playerId}")
    public ResponseMessage<PlayParty> partyPlayerFight(@PathVariable("playerId") int playerId) {
        return playPartyService.playerAttack(playerId);
    }

    @GetMapping("/play-party/{id}")
    public ResponseMessage<PlayParty> getPlayPartyById(@PathVariable("id") int id) {
        return playPartyService.getPlayParty(id);
    }

    @PostMapping("/create-play-party")
    public ResponseMessage<PlayParty> createPlayParty(HttpServletRequest httprequest) {
        int user = GetByUser(httprequest);
        if(user == 0)
        {
            ResponseMessage<PlayParty> playPartyResponseMessage = new ResponseMessage<>(null);
            playPartyResponseMessage.setResponseCode(ResponseCode.SUCCESS);
            playPartyResponseMessage.setMessage("You must be logged in");
            return playPartyResponseMessage;
        }
        var playerCards = this.cardService.getAllCardByUserId(user);
        PlayParty playParty = new PlayParty();
        playParty.setCardPlayerB(0);
        playParty.setWinnerId(0);
        playParty.setCurrentPlayerId(user);
        playParty.setUserIdB(0);
        playParty.setUserIdA(user);
        playParty.setPartyCode("BVBDBVDN");
        playParty.setStarted(Boolean.TRUE);
        if(playerCards.getResponse().size()>0)
        {
            playParty.setCardPlayerA(playerCards.getResponse().get(0).getId());
        }
        var response =  playPartyService.updatePlayParty(playParty);
        var message = "http://localhost:8989/playParty.html?id="+response.getResponse().getId();
        response.setMessage(message);
        return response;
    }

    @PostMapping("/update-user-card-play-party/{playPartyId}/{cardId}")
    public ResponseMessage<PlayParty> createPlayParty(@PathVariable("playPartyId") int playPartyId, @PathVariable("cardId") int cardId, HttpServletRequest httprequest) {
        int user = GetByUser(httprequest);
        if(user == 0)
        {
            ResponseMessage<PlayParty> playPartyResponseMessage = new ResponseMessage<>(null);
            playPartyResponseMessage.setResponseCode(ResponseCode.FORBIDDEN);
            playPartyResponseMessage.setMessage("You must be logged in");
            return playPartyResponseMessage;
        }
        var playParty = playPartyService.getPlayParty(playPartyId);
        var card = cardService.getCard(cardId);
        if(!playParty.isSuccess()||!card.isSuccess())
        {
            ResponseMessage<PlayParty> playPartyResponseMessage = new ResponseMessage<>(null);
            playPartyResponseMessage.setResponseCode(ResponseCode.ERROR);
            playPartyResponseMessage.setMessage("Either the card or the playParty dont exist");
            return playPartyResponseMessage;
        }

        var playerCards = cardService.getAllCardTransactionsForUserId(Optional.empty(), user);
        Boolean cardFound = Boolean.FALSE;

        for(var cardP : playerCards.getResponse())
        {
            if(cardP.getId()==card.getResponse().getId())
            {
                cardFound = Boolean.TRUE;
            }
        }

        if(cardFound == Boolean.FALSE)
        {
            ResponseMessage<PlayParty> playPartyResponseMessage = new ResponseMessage<>(null);
            playPartyResponseMessage.setResponseCode(ResponseCode.ERROR);
            playPartyResponseMessage.setMessage("You are not the card owner");
            return playPartyResponseMessage;
        }

        if(playParty.getResponse().getUserIdA() == user)
        {
            playParty.getResponse().setCardPlayerA(card.getResponse().getId());
            this.playPartyService.updatePlayParty(playParty.getResponse());
            ResponseMessage<PlayParty> playPartyResponseMessage = new ResponseMessage<>(null);
            playPartyResponseMessage.setResponseCode(ResponseCode.SUCCESS);
            return playPartyResponseMessage;
        }
        else if(playParty.getResponse().getUserIdB() == user)
        {
            playParty.getResponse().setCardPlayerB(card.getResponse().getId());
            this.playPartyService.updatePlayParty(playParty.getResponse());
            ResponseMessage<PlayParty> playPartyResponseMessage = new ResponseMessage<>(null);
            playPartyResponseMessage.setResponseCode(ResponseCode.SUCCESS);
            return playPartyResponseMessage;
        }
        else
        {
            ResponseMessage<PlayParty> playPartyResponseMessage = new ResponseMessage<>(null);
            playPartyResponseMessage.setResponseCode(ResponseCode.ERROR);
            playPartyResponseMessage.setMessage("User dont belong to the party");
            return playPartyResponseMessage;
        }
    }

    @GetMapping("/join-play-party/{code}")
    public ResponseMessage<PlayParty> getPlayPartyByCode(@PathVariable("code") String code) {
        return playPartyService.getPlayPartyByCode(code);
    }


    @RequestMapping(value="/view-player-party-data/{id}", method = RequestMethod.GET)
    public ResponseMessage<PlayPartyViewModel> createUserAction( @PathVariable("id") int id, HttpServletRequest httprequest){
        int user = GetByUser(httprequest);
        if(user == 0)
        {
            ResponseMessage<PlayPartyViewModel> responseMessage = new ResponseMessage<>(null);
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("You must be logged in !");
            return responseMessage;
        }

        var test = this.playPartyService.getPlayParty(id);
        if(!test.isSuccess())
        {
            ResponseMessage<PlayPartyViewModel> responseMessage = new ResponseMessage<>(null);
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("Party not found !");
            return responseMessage;
        }
        var cards_player_a = this.cardService.getAllCardByUserId(test.getResponse().getUserIdA());
        var cards_player_b = this.cardService.getAllCardByUserId(test.getResponse().getUserIdB());
        var this_user_cards = this.cardService.getAllCardByUserId(user);

        PlayPartyViewModel playPartyViewModel = new PlayPartyViewModel();
        playPartyViewModel.setPlayParty(test.getResponse());
        playPartyViewModel.setCardUserList(this_user_cards.getResponse());
        playPartyViewModel.setPlayerCardsA(cards_player_a.getResponse());
        playPartyViewModel.setPlayerCardsB(cards_player_b.getResponse());
        ResponseMessage<PlayPartyViewModel> responseMessage = new ResponseMessage<>(playPartyViewModel);
        responseMessage.setResponseCode(ResponseCode.SUCCESS);
        return responseMessage;
    }


}