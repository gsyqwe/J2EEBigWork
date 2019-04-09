package com.example.demo.domain;

import javax.persistence.*;
import java.util.Date;

//订单,id(自增),Uid(用于识别是哪个顾客订的),Did(用于识别是哪家餐厅),Iid(用于查看详情的信息)
//aid 地址的id
//State state,Money(金额,订单价格),balance结算结果

@Entity
@Table(name="Record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int aid;//地址的id

    private String Dname;//餐厅名称

    private String Uid;

    private String Did;

    private int Iid;

    private State state;

    private boolean balance;//false未结算，true结算过了

    private double money;

    private Date date;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public boolean isBalance() {
        return balance;
    }

    public String getDname() {
        return Dname;
    }

    public void setDname(String dname) {
        Dname = dname;
    }

    public void setBalance(boolean balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getDid() {
        return Did;
    }

    public void setDid(String did) {
        Did = did;
    }

    public int getIid() {
        return Iid;
    }

    public void setIid(int iid) {
        Iid = iid;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
