package com.example.demo.dao;

import com.example.demo.domain.*;

import java.util.List;

public interface Dinningdao {
    List<Dinning> getalldin();
    Dinning getdinbyid(String did);
    void updatedin(Dinning dinning);
    void register(Dinning dinning);
    List<Meal>getmealbyid(String did);
    List<Combomenu> getcombbyid(String did);
    void insertMeal(Meal meal);
    void insertCombeMe(Combomenu combomenu);
    List<Meal> getmeal();
    List<Combomenu>getcombemenu();
    List<Menu> getMenubyid(String did);
    void publishmenu(Menu menu);
    Meal findmealbyid(String mid);
    void updatemeal(Meal meal);
    List<Record> checkorder(String did);
    void insertmessage(Message message);
    List<Dinning> getusedin();
}
