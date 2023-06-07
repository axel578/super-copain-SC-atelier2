package com.cpe.cardgame.model;

import jakarta.persistence.*;

@Entity
public class PlayParty {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserIdA() {
        return userIdA;
    }

    public void setUserIdA(Integer userIdA) {
        this.userIdA = userIdA;
    }

    public Integer getUserIdB() {
        return userIdB;
    }

    public void setUserIdB(Integer userIdB) {
        this.userIdB = userIdB;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id_a")
    private Integer userIdA;

    @Column(name = "user_id_b")
    private Integer userIdB;

    private String partyCode;

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Integer getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    public Integer getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(Integer currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public Integer getCardPlayerA() {
        return cardPlayerA;
    }

    public void setCardPlayerA(Integer cardPlayerA) {
        this.cardPlayerA = cardPlayerA;
    }

    public Integer getCardPlayerB() {
        return cardPlayerB;
    }

    public void setCardPlayerB(Integer cardPlayerB) {
        this.cardPlayerB = cardPlayerB;
    }

    private Boolean started;

    @Column(name = "winner_id")
    private Integer winnerId;

    @Column(name = "curren_player_id")
    private Integer currentPlayerId;

    @Column(name = "card_player_a")
    private Integer cardPlayerA;

    @Column(name = "card_player_b")
    private Integer cardPlayerB;


}