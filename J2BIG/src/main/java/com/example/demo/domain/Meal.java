package com.example.demo.domain;

import javax.persistence.*;
import java.util.Date;

//菜品,id(菜品的id,自增),name(菜品的名称),num(库存),price(单价),Did(餐厅的id,表示这个菜是哪个餐厅的)
//url(菜品的图片)
@Entity
@Table(name="Meal")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int num;

    private double price;

    private String Did;

    private String url;

    private Date enddate;//截止时间

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDid() {
        return Did;
    }

    public void setDid(String did) {
        Did = did;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
