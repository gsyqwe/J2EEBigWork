package com.example.demo.domain;

import javax.persistence.*;
import java.util.Date;

//id(自增),name(套餐名称),mid(菜品id,用;隔开),mname(菜品的名称,用;隔开),mnum(菜品的数量,用;隔开),price(价格),did(表示是哪家餐厅的套餐)
//courl(套餐的图片)
@Entity
@Table(name="Combomenu")
public class Combomenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String mid;

    private String mname;

    private String mnum;

    private double price;

    private String did;

    private String courl;

    private Date enddate;//截止时间

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getCourl() {
        return courl;
    }

    public void setCourl(String courl) {
        this.courl = courl;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
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

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMnum() {
        return mnum;
    }

    public void setMnum(String mnum) {
        this.mnum = mnum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
