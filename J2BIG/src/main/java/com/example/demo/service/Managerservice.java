package com.example.demo.service;

import com.example.demo.domain.Dinning;
import com.example.demo.domain.Message;
import com.example.demo.domain.Record;
import com.example.demo.domain.Yummy;

import java.util.List;

public interface Managerservice {
    List<Message> getcheckdin();
    void pass(String meid,String did);
    void balance();
    List<Dinning> getalldin();
    List<Record> getvipor(String userid);
    List<Yummy> checkyummy();
    List<Record> getdinor(String did);
    List<Record> getunbalance();
}
