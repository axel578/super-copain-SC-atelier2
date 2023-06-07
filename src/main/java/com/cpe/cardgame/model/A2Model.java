package com.cpe.cardgame.model;

public class A2Model {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private Integer id;
    private String color;
    private String name;
    private String imgUrl;
    public A2Model() {
        this.color = "MYCOLOR";
        this.name = "MYNAME";
        this.imgUrl="";
    }
    public A2Model( String name,String color, String imgUrl) {
        this.color = color;
        this.name = name;
        this.imgUrl=imgUrl;
    }
    @Override
    public String toString() {
        return "MyName:"+this.name+this.id+", MyColor:"+this.color;
    }
}