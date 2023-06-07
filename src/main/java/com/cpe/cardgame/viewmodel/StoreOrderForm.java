package com.cpe.cardgame.viewmodel;

import com.cpe.cardgame.model.StoreOrder;
import jakarta.persistence.Column;

public class StoreOrderForm {


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


    private Integer userId;

    private Integer cardId;

    public StoreOrder toStoreOrder()
    {
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setCardId(this.getCardId());
        storeOrder.setUserId(this.getUserId());
        return storeOrder;
    }

}