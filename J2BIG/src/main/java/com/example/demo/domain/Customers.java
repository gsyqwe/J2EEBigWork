package com.example.demo.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//会员具有属性:Id(用于登录，唯一),密码,邮箱(用于注册和修改密码),
//电话,姓名,住址,金额(消费总金额，累积),会员级别(初始为1，最高为4，根据消费金额自动升级),余额
//状态(注销与可登陆),如果选择注销则以后不可以登录。
@Entity
@Table(name = "Customers")
public class Customers {
    @Id
    private String userid;

    private String password;

    private String email;

    private String name;

    private double money;//金额(消费总金额,累计)

    private double sum;//余额

    private int level;//4个等级，分别为1，2，3，4

    private boolean state;//分为true和false,true则没有注销可以登录，false是选择了注销，不能登录

    private String aid;//保存address的id,多个address用;分隔

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
