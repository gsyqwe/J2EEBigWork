package com.example.demo.dao;

import com.example.demo.domain.Dinning;
import com.example.demo.domain.Message;
import com.example.demo.domain.Record;
import com.example.demo.domain.Yummy;

import java.util.List;

public interface Managerdao {
    List<Message> getcheckdin();
    Message getmessagebyid(String id);
    void updatemessage(Message message);
    List<Record> getunbalance();
    List<Yummy> getyummy();
    void insertyummy(Yummy yummy);
    List<Record> getvipor(String uid);
    List<Dinning> getalldin();
    List<Record> checkdinor(String did);
    void updaterecord(Record record);
}
