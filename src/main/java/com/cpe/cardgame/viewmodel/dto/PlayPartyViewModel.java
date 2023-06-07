package com.cpe.cardgame.viewmodel.dto;

import com.cpe.cardgame.model.Card;
import com.cpe.cardgame.model.PlayParty;

import java.util.List;

public class PlayPartyViewModel {


    public PlayParty getPlayParty() {
        return playParty;
    }

    public void setPlayParty(PlayParty playParty) {
        this.playParty = playParty;
    }

    public List<Card> getCardUserList() {
        return cardUserList;
    }

    public void setCardUserList(List<Card> cardUserList) {
        this.cardUserList = cardUserList;
    }

    public List<Card> getPlayerCardsA() {
        return playerCardsA;
    }

    public void setPlayerCardsA(List<Card> playerCardsA) {
        this.playerCardsA = playerCardsA;
    }

    public List<Card> getPlayerCardsB() {
        return playerCardsB;
    }

    public void setPlayerCardsB(List<Card> playerCardsB) {
        this.playerCardsB = playerCardsB;
    }

    private PlayParty playParty;
    private List<Card> cardUserList;
    private List<Card> playerCardsA;
    private List<Card> playerCardsB;

}