package com.cpe.cardgame.model;

import com.cpe.cardgame.viewmodel.CardForm;
import jakarta.persistence.*;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String family;
    private String affinity;
    private String imgUrl;
    private String smallImgUrl;
    private Double energy;
    private Double hp;

    public Double getOriginalHp() {
        return OriginalHp;
    }

    public void setOriginalHp(Double originalHp) {
        OriginalHp = originalHp;
    }

    private Double OriginalHp;
    private Double defence;
    private Double attack;
    private Double price;

    public Boolean getToSell() {
        return toSell;
    }

    public void setToSell(Boolean toSell) {
        this.toSell = toSell;
    }

    private Boolean toSell;


    @Column(name = "user_id")
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public CardForm toCardForm()
    {
        CardForm cardForm = new CardForm();
        cardForm.setId(this.getId());
        cardForm.setAffinity(this.getAffinity());
        cardForm.setAttack(this.getAttack());
        cardForm.setDefence(this.getDefence());
        cardForm.setEnergy(this.getEnergy());
        cardForm.setHp(this.getHp());
        cardForm.setDescription(this.getDescription());
        cardForm.setName(this.getName());
        cardForm.setImgUrl(this.getImgUrl());
        cardForm.setSmallImgUrl(this.getSmallImgUrl());
        cardForm.setToSell(this.getToSell());
        cardForm.setUserId(this.getUserId());
        cardForm.setFamily(this.getFamily());
        cardForm.setPrice(this.getPrice());
        return cardForm;
    }
// Getters and setters
}