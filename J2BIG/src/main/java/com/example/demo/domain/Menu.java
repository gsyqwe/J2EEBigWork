package com.example.demo.domain;

import javax.persistence.*;
import java.util.Date;

//菜单，id(主键唯一，自增),Dinningid(餐厅的id,用于识别哪个餐厅),startdate,enddate
//类型kind(不同类型的用;),mid(菜品id,同一个类型下的菜品id以,分割,不同类型的用;),mnum(菜品的数量，在选择某个菜品的时候进行保存)
//Cid(套餐的id,多个套餐也用;隔开)
@Entity
@Table(name="Menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Did;

    private Date startdate;//开始时间

    private Date enddate;//结束时间

    private String kind;

    private String mid;

    private String mnum;

    private String Cid;

    public String getMnum() {
        return mnum;
    }

    public void setMnum(String mnum) {
        this.mnum = mnum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDid() {
        return Did;
    }

    public void setDid(String did) {
        Did = did;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }
}
