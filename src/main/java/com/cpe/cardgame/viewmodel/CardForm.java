package com.cpe.cardgame.viewmodel;

import com.cpe.cardgame.model.Card;
import jakarta.persistence.Column;

public class CardForm {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //TODO METTRE ID ici pour utiliser sur les card a acheter
    private Integer id;
    private String name;
    private String description;
    private String family;
    private String affinity;
    private String imgUrl;
    private String smallImgUrl;
    private Double energy;
    private Double hp;
    private Double defence;
    private Double attack;
    private Double price;
    private Integer userId;

    public Boolean getToSell() {
        return toSell;
    }

    public void setToSell(Boolean toSell) {
        this.toSell = toSell;
    }

    private Boolean toSell;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getAffinity() {
        return affinity;
    }

    public void setAffinity(String affinity) {
        this.affinity = affinity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }

    public void setSmallImgUrl(String smallImgUrl) {
        this.smallImgUrl = smallImgUrl;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getHp() {
        return hp;
    }

    public void setHp(Double hp) {
        this.hp = hp;
    }

    public Double getDefence() {
        return defence;
    }

    public void setDefence(Double defence) {
        this.defence = defence;
    }

    public Double getAttack() {
        return attack;
    }

    public void setAttack(Double attack) {
        this.attack = attack;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Card toCard() {
        Card card = new Card();
        card.setId(this.getId());
        card.setAffinity(this.getAffinity());
        card.setName(this.getName());
        card.setDescription(this.getDescription());
        card.setFamily(this.getFamily());
        card.setImgUrl(this.getImgUrl());
        card.setSmallImgUrl(this.getSmallImgUrl());
        card.setEnergy(this.getEnergy());
        card.setHp(this.getHp());
        card.setDefence(this.getDefence());
        card.setAttack(this.getAttack());
        card.setPrice(this.getPrice());
        card.setUserId(this.getUserId());
        card.setToSell(this.getToSell());

        return card;
    }
}