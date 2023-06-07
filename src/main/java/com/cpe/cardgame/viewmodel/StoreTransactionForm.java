package com.cpe.cardgame.viewmodel;

import com.cpe.cardgame.model.StoreTransaction;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class StoreTransactionForm {

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    private Integer userId;

    private Integer cardId;

    private String action;

    private LocalDateTime timestamp;

    public StoreTransaction toStoreTransaction() {
        StoreTransaction storeTransaction = new StoreTransaction();
        storeTransaction.setUserId(this.getUserId());
        storeTransaction.setCardId(this.getCardId());
        storeTransaction.setAction(this.getAction());
        storeTransaction.setTimestamp(this.getTimestamp());

        return storeTransaction;
    }
}