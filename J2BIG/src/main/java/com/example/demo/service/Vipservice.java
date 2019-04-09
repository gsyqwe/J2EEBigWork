package com.example.demo.service;

import com.example.demo.domain.Address;
import com.example.demo.domain.Customers;
import com.example.demo.domain.Information;
import com.example.demo.domain.Record;

import java.util.List;

public interface Vipservice {
    List<Information> getdetail(String lid);
    List<Address> getuseraddress(String userid);
    void unsubscribe(String userid);
    String register(Customers customers,String check);
    void addAddress(String userid,String address,String phone);
    void deviseName(String userid,String username,String email,String sum);
    void devisePhoneAddress(String userid,String phone,String address);
    void order(String userid, String foodid,String foodnum, Record record, Information information);
    String pay(String userid,String rid);
    void cancleorder(String userid,String rid);
    List<Record> checkrecord(String userid);
    void confirm(String uid,String rid);
    List<Record> checktostate(String userid,String state);
    boolean checktime(String userid,String rid);
    void sendcheck(String receivemail);
    List<Customers>getallcustomer();
    Customers checkviptoid(String userid);
    List<Address> findadbyid(String rid);
}
