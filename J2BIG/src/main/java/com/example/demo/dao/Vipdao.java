package com.example.demo.dao;

import com.example.demo.domain.*;

import java.util.List;

public interface Vipdao {
    List<Address>getuseraddress(String userid);
    List<Information> getdetail(int lid);
    void deviseaddress(int aid,String phone,String address);
    void register(Customers customers);
    List<Customers> getallcustomer();
    void unsubscribe(String userid);
    void addaddress(String userid,String address,String phone);
    void devisename(String userid,String username,String email,String sum);
    void deletenum(String foodid,String foodnum);
    int saveInformation(Information information);
    void insertRecord(Record record);
    Customers findcustomerbyid(String userid);
    Record findrecordbyid(String rid);
    void pay(Customers customers,Record record);
    void updaterecord(Record record);
    void updatevip(Customers customers);
    List<Record>checkrecordtostate(String userid,State state);
    List<Record>checkrecord(String userid);
    List<Address> findadbyid(String rid);
}
