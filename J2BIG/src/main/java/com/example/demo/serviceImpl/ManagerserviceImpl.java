package com.example.demo.serviceImpl;

import com.example.demo.dao.Dinningdao;
import com.example.demo.dao.Managerdao;
import com.example.demo.dao.Vipdao;
import com.example.demo.daoImpl.DinningdaoImpl;
import com.example.demo.daoImpl.ManagerdaoImpl;
import com.example.demo.daoImpl.VipdaoImpl;
import com.example.demo.domain.*;
import com.example.demo.service.Managerservice;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagerserviceImpl implements Managerservice {
    Managerdao managerdao=new ManagerdaoImpl();
    Dinningdao dinningdao=new DinningdaoImpl();
    Vipdao vipdao=new VipdaoImpl();
    //得到所有待审核的修改信息
    @Override
    public List<Message> getcheckdin() {
        return managerdao.getcheckdin();
    }

    //审核通过其修改信息
    //meid是信息的id,did是餐厅的id
    @Override
    public void pass(String meid, String did) {
        Dinning dinning=dinningdao.getdinbyid(did);
        Message message=managerdao.getmessagebyid(meid);
        dinning.setPhone(message.getPhone());
        dinning.setKind(message.getKind());
        dinning.setAddress(message.getAddress());
        dinning.setName(message.getName());
        dinning.setPassword(message.getPassword());
        dinning.setState(true);
        dinningdao.updatedin(dinning);
        message.setState(true);
        managerdao.updatemessage(message);
    }

    //结算
    @Override
    public void balance() {
        double money=0;
        ArrayList<Record> records=(ArrayList<Record>) managerdao.getunbalance();
        for (int i=0;i<records.size();i++){
            if (records.get(i).getState() == State.CONFIRM) {
                Dinning dinning = dinningdao.getdinbyid(records.get(i).getDid());
                dinning.setMoney(records.get(i).getMoney() * 0.9);
                dinningdao.updatedin(dinning);
                money = dinning.getMoney() * 0.1;
                Record record = records.get(i);
                record.setBalance(true);
                managerdao.updaterecord(record);
            }
        }
        Yummy yummy=new Yummy();
        yummy.setMoney(money);
        yummy.setDate(new Date());
        managerdao.insertyummy(yummy);
    }

    @Override
    public List<Dinning> getalldin() {
        return managerdao.getalldin();
    }

    @Override
    public List<Record> getvipor(String userid) {//找会员的所有订单
        return managerdao.getvipor(userid);
    }

    @Override
    public List<Yummy> checkyummy() {
        return managerdao.getyummy();
    }

    @Override
    public List<Record> getdinor(String did) {
        return managerdao.checkdinor(did);
    }

    @Override
    public List<Record> getunbalance() {
        ArrayList<Record> records=(ArrayList<Record>) managerdao.getunbalance();
        return changtounbalance(records);
    }

    private List<Record> changtounbalance(ArrayList<Record> records){
        List<Record> recordslist = new ArrayList<>();
        for (int i = 0 ;i<records.size();i++){
            if (records.get(i).getState() == State.CONFIRM){
                recordslist.add(records.get(i));
            }
        }
        return recordslist;
    }
}
