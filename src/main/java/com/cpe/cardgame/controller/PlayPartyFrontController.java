package com.cpe.cardgame.controller;

import com.cpe.cardgame.model.Card;
import com.cpe.cardgame.service.PlayPartyService;
import com.cpe.cardgame.viewmodel.AuthDTO;
import com.cpe.cardgame.viewmodel.CardForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlayPartyFrontController extends BaseController {
    private final PlayPartyController playPartyController;
    private final PlayPartyService playPartyService;
    private final CardController cardController;

    public PlayPartyFrontController(PlayPartyController playPartyController, CardController cardController, PlayPartyService playPartyService) {
        this.playPartyController = playPartyController;
        this.cardController = cardController;
        this.playPartyService = playPartyService;
    }


    @RequestMapping(value="/view-player-party/{id}", method = RequestMethod.GET)
    public String createUserAction(Model model, @PathVariable("id") int id, HttpServletRequest httprequest){
        int user = GetByUser(httprequest);
        if(user == 0)
        {
            AuthDTO userform = new AuthDTO();
            model.addAttribute("connectForm", userform);
            return "connectForm";
        }
        /*var test = this.playPartyController.getPlayPartyById(id);
        var cards_player_a = this.cardController.getCardsByUserId(test.getResponse().getUserIdA());
        var cards_player_b = this.cardController.getCardsByUserId(test.getResponse().getUserIdB());
        var this_user_cards = this.cardController.getCardsByUserId(user);

        model.addAttribute("playerParty", test.getResponse());
        model.addAttribute("userCards", this_user_cards.getResponse());
        model.addAttribute("playerCardsA", cards_player_a.getResponse());
        model.addAttribute("playerCardsB", cards_player_b.getResponse());*/
        return "playParty";
    }

    @RequestMapping(value="/view-player-party/second-player/{id}", method = RequestMethod.GET)
    public String SetSecondPlayer(Model model, @PathVariable("id") int id, HttpServletRequest httprequest){
        int user = GetByUser(httprequest);
        if(user == 0)
        {
            AuthDTO userform = new AuthDTO();
            model.addAttribute("connectForm", userform);
            return "connectForm";
        }
        var test = this.playPartyController.getPlayPartyById(id);
        test.getResponse().setUserIdB(user);
        var cards = this.cardController.getCardsByUserId(user);
        test.getResponse().setCardPlayerB(cards.getResponse().get(0).getId());
        this.playPartyService.updatePlayParty(test.getResponse());
        return "";
    }

    @RequestMapping(value="/create-party-of-player", method = RequestMethod.GET)
    public String CreatePartyOfPlayer(Model model, HttpServletRequest httprequest){
        int user = GetByUser(httprequest);
        if(user == 0)
        {
            AuthDTO userform = new AuthDTO();
            model.addAttribute("connectForm", userform);
            return "connectForm";
        }

        return "playPartyCreate";
    }


}