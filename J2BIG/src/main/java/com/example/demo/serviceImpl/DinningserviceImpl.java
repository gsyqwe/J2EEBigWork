package com.example.demo.serviceImpl;

import com.example.demo.dao.Dinningdao;
import com.example.demo.daoImpl.DinningdaoImpl;
import com.example.demo.domain.*;
import com.example.demo.service.Dinningservice;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DinningserviceImpl implements Dinningservice {
    private Dinningdao dinningdao=new DinningdaoImpl();

    //得到一个餐厅的具体信息
    @Override
    public Dinning checkdin(String did) {
        return dinningdao.getdinbyid(did);
    }

    @Override
    public List<Dinning> getalldin() {
        return dinningdao.getalldin();
    }

    //返回6位登录码
    @Override
    public String register(Dinning dinning) {
        String did="";//生成7位识别码
        Random random=new Random();
        for (int i=0;i<7;i++){
            did=did+random.nextInt(10);
        }
        dinning.setId(did);
        dinningdao.register(dinning);
        Message message=new Message();
        message.setPhone(dinning.getPhone());
        message.setPassword(dinning.getPassword());
        message.setKind(dinning.getKind());
        message.setAddress(dinning.getAddress());
        message.setDid(did);
        message.setName(dinning.getName());
        message.setState(false);
        dinningdao.insertmessage(message);
        return did;
    }

    @Override
    public void devisedin(String did,String dname, String address, String kind, String phone) {
        Dinning dinning=dinningdao.getdinbyid(did);
        Message message=new Message();
        message.setDid(did);
        message.setState(false);
        message.setName(dname);
        message.setAddress(address);
        message.setKind(kind);
        message.setPassword(dinning.getPassword());
        message.setPhone(phone);
        dinningdao.insertmessage(message);
    }

    @Override
    public List<Meal> getmealbyid(String did) {
        return dinningdao.getmealbyid(did);
    }

    @Override
    public List<Combomenu> getcombomealbyid(String did) {
        return dinningdao.getcombbyid(did);
    }

    @Override
    public void addmeal(Meal meal) {
        dinningdao.insertMeal(meal);
    }

    @Override
    public void addcombomeal(Combomenu combomenu) {
        dinningdao.insertCombeMe(combomenu);
    }

    //带有,的mid来得到具体的meal
    @Override
    public List<Meal> getdetail(String mid) {
        String []id=mid.split(",");
        ArrayList<Meal> meals=(ArrayList<Meal>) dinningdao.getmeal();
        ArrayList<Meal> result=new ArrayList<>();
        for (int i =0;i<meals.size();i++){
            for (int j =0;j<id.length;j++){
                if (meals.get(i).getId()==Integer.parseInt(id[j])){
                    result.add(meals.get(i));
                }
            }
        }
        return result;
    }

    //根据did得到当前日期的菜单
    //返回信息以;分割按照
    //菜单id+"."+菜品id+"."菜品数量+"."+各个类型+"."+套餐的id+"."+餐厅的id
    @Override
    public String getmenu(String did) {
        Menu result=new Menu();
        List<Menu> list=dinningdao.getMenubyid(did);
        for (int i =0;i<list.size();i++){
            if (list.get(i).getStartdate().getTime()<new Date().getTime()&&new Date().getTime()<list.get(i).getEnddate().getTime()){
                result=list.get(i);
            }
        }
        String str="";
        str=result.getId()+"."+result.getMid()+"."+result.getMnum()+"."+result.getKind()+"."+result.getCid()+"."+result.getDid();
        return str;
    }

    //带有,的coid得到具体的套餐内容
    @Override
    public List<Combomenu> getcobomenu(String coid) {
        List<Combomenu> list=dinningdao.getcombemenu();
        List<Combomenu> combomenus=new ArrayList<>();
        String id[]=coid.split(",");
        for (int i = 0; i <list.size();i++){
            for (int j = 0 ;j<id.length;j++){
                if (list.get(i).getId()==Integer.parseInt(id[j])){
                    combomenus.add(list.get(i));
                }
            }
        }
        return combomenus;
    }

    @Override
    public void publishmenu(Menu menu) {
        dinningdao.publishmenu(menu);
    }

    @Override
    public List<Record> checkorder(String did) {
        return dinningdao.checkorder(did);
    }

    @Override
    public void updatemeal(String mid, String mnum) {
        Meal meal=dinningdao.findmealbyid(mid);
        meal.setNum(Integer.parseInt(mnum));
        dinningdao.updatemeal(meal);
    }

    @Override
    public boolean getorderflag(String did) {
        Dinning dinning=dinningdao.getdinbyid(did);
        return dinning.isState();
    }

    @Override
    public List<Dinning> getusedin() {
        return dinningdao.getusedin();
    }
}
