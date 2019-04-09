package com.example.demo.service;

import com.example.demo.domain.*;

import java.util.List;

public interface Dinningservice {
    Dinning checkdin(String did);
    List<Dinning> getalldin();
    String register(Dinning dinning);
    void devisedin(String did,String dname,String address,String kind,String phone);
    List<Meal> getmealbyid(String did);
    List<Combomenu> getcombomealbyid(String did);
    void addmeal(Meal meal);
    void addcombomeal(Combomenu combomenu);
    List<Meal> getdetail(String mid);
    String getmenu(String did);
    List<Combomenu> getcobomenu(String coid);
    void publishmenu(Menu menu);
    List<Record> checkorder(String did);
    void updatemeal(String mid,String mnum);
    boolean getorderflag(String did);
    List<Dinning> getusedin();
}
