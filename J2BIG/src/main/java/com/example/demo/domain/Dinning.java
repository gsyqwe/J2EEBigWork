package com.example.demo.domain;

import javax.persistence.*;
//餐厅,编码(7位识别码，系统自动分配用于登录,唯一),密码,地点(唯一),姓名(唯一),类型(唯一),状态(待审核与上线),电话,收入Money通过结算
@Entity
@Table(name="Dinning")
public class Dinning {
    @Id
    private String id;

    private String password;

    private String address;

    private String name;

    private String kind;

    private String phone;

    private double money;

    private boolean state;//false为不可发布,true为可发布

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
