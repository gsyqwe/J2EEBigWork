package com.example.demo.domain;

import javax.persistence.*;
import java.util.Date;

//保存yummy的统计信息
@Entity
@Table(name = "yummy")
public class Yummy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double money;//收入通过结算
    private Date date;//记录结算的时间

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
