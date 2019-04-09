package com.example.demo.domain;

import javax.persistence.*;

//订单详细信息，id(自增),foodname(餐品名称,中间均用;隔开),foodnum(数量,中间均用;隔开),price(总价,食物单价*数量计算出来的总价,用;隔开)
@Entity
@Table(name="Information")
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String foodname;

    private String foodnum;

    private String price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodnum() {
        return foodnum;
    }

    public void setFoodnum(String foodnum) {
        this.foodnum = foodnum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
