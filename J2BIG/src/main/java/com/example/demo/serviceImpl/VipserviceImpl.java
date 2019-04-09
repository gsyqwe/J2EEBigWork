package com.example.demo.serviceImpl;

import com.example.demo.dao.Vipdao;
import com.example.demo.daoImpl.VipdaoImpl;
import com.example.demo.domain.*;
import com.example.demo.service.Mailservice;
import com.example.demo.service.Vipservice;
import java.util.Date;
import java.util.List;
import java.util.Random;

//Pstate是支付，true支付，false未支付
//state是收货，true是收货，false未收货
public class VipserviceImpl implements Vipservice {

    private static String sendcheck;//验证码,用于发送与验证

    Vipdao vipdao=new VipdaoImpl();
    Mailservice mailservice=new MailserviceImpl();

    @Override
    public List<Information> getdetail(String lid) {
        return vipdao.getdetail(Integer.parseInt(lid));
    }

    @Override
    public List<Address> getuseraddress(String userid) {
        return vipdao.getuseraddress(userid);
    }

    //注销
    @Override
    public void unsubscribe(String userid) {
        vipdao.unsubscribe(userid);
    }

    @Override
    public String register(Customers customers, String check) {
        if (sendcheck.equals(check)){
            vipdao.register(customers);
            return "注册成功";
        }else{
            return "验证码错误";
        }
    }

    //将整个phone和addres传输到后端
    @Override
    public void addAddress(String userid,String address, String phone) {
        vipdao.addaddress(userid,address,phone);
    }

    @Override
    public void deviseName(String userid,String username,String email,String sum) {
        vipdao.devisename(userid,username,email,sum);
    }

    //将整个修改的phone和address传输到后端
    @Override
    public void devisePhoneAddress(String aid,String phone, String address) {
        vipdao.deviseaddress(Integer.parseInt(aid),phone,address);
    }

    //根据foodid去每个食物中减少对应数量的库存
    //先保存information,然后返回information的id
    //record保存information的id后，再将record插入数据库
    @Override
    public void order(String userid, String foodid,String foodnum, Record record, Information information) {
        //第一步先去每个里面减少库存
        vipdao.deletenum(foodid,foodnum);
        //第二部保存information并得到Infromation的id
        int mid=vipdao.saveInformation(information);
        record.setIid(mid);
        vipdao.insertRecord(record);
    }

    @Override
    public String pay(String userid, String rid) {
        //2布先去找到customer,然后减去对应的前
        //将record变为支付状态
        Customers customers=vipdao.findcustomerbyid(userid);
        Record record=vipdao.findrecordbyid(rid);
        int level = customers.getLevel();
        double discount = 0;
        if (level == 1){
            discount = 1;
        }else if(level ==2){
            discount = 0.95;
        }else if(level ==3){
            discount = 0.9;
        }else if(level ==4){
            discount = 0.85;
        }
        if (customers.getSum()>record.getMoney()*discount){
            customers.setSum(customers.getSum()-record.getMoney()*discount);
            record.setMoney(record.getMoney()*discount);
            record.setState(State.PAY);
            vipdao.pay(customers,record);
            return "支付成功";
        }else{
            return "余额不足";
        }
    }

    //需要进行检查，如果是已经支付的订单的，需要进行退款
    //未支付的订单的，无需退钱
    @Override
    public void cancleorder(String userid, String rid) {
        Customers customers=vipdao.findcustomerbyid(userid);
        Record record=vipdao.findrecordbyid(rid);
        double discount = 0;
        int level = customers.getLevel();
        if(level == 1){
            discount = 1;
        }else if (level ==2){
            discount = 0.95;
        }else if(level ==3){
            discount = 0.9;
        }else{
            discount = 0.85;
        }
        //先检查record状态
        if (record.getState().equals(State.PAY)){
            //已经支付的退款方案
            Date date=new Date();
            long min=(date.getTime()-record.getDate().getTime())/1000/60;//计算现在与订餐的时候的时间差
            customers.setSum(customers.getSum()+record.getMoney()*discount*min/15);//进行退款
            record.setState(State.UNSUBSCRIBE);//表示退订
            vipdao.updatevip(customers);
            vipdao.updaterecord(record);
        }else{
            //位支付的退款方案
            record.setState(State.UNSUBSCRIBE);//表示被退订
            vipdao.updaterecord(record);
        }
    }

    //查看订单
    @Override
    public List<Record> checkrecord(String userid) {
        return vipdao.checkrecord(userid);
    }

    //确认收货
    //uid来升级,rid来收货
    @Override
    public void confirm(String uid,String rid) {
        Customers customers=vipdao.findcustomerbyid(uid);
        Record record=vipdao.findrecordbyid(rid);
        customers.setMoney(customers.getMoney()+record.getMoney());
        if (customers.getMoney()>100){
            customers.setLevel(2);
        }else if (customers.getMoney()>1000){
            customers.setLevel(3);
        }else if (customers.getMoney()>100000){
            customers.setLevel(4);
        }
        record.setState(State.CONFIRM);
        vipdao.updatevip(customers);
        vipdao.updaterecord(record);
    }

    //根据状态查看订单
    @Override
    public List<Record> checktostate(String userid, String state) {
        if (state.equals("已支付")){
            return vipdao.checkrecordtostate(userid,State.PAY);
        }else if (state.equals("未支付")){
            return vipdao.checkrecordtostate(userid,State.SUBSCRIBE);
        }else if (state.equals("已退款")){
            return vipdao.checkrecordtostate(userid,State.UNSUBSCRIBE);
        }else{
            return vipdao.checkrecordtostate(userid,State.CONFIRM);
        }
    }

    //检查是否在15分钟(演示时需要改成2分钟)内进行付款,如果在15分钟内未完成支付，则自动取消
    //取消为false,不去想为true
    @Override
    public boolean checktime(String userid, String rid) {
        Date date=new Date();
        Record record=vipdao.findrecordbyid(rid);
        Date date1=record.getDate();
        long a = date.getTime();
        long b = date1.getTime();
        System.out.println(a-b);
        if (a-b > 120000){
            return false;
        }
        return true;
    }

    //发送验证码
    @Override
    public void sendcheck(String receivemail) {
        //随机生成一个identityCode
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < 5; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        sendcheck=val;
        try {
            mailservice.sendMessage(receivemail,sendcheck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //得到所有的用户
    @Override
    public List<Customers> getallcustomer() {
        return vipdao.getallcustomer();
    }

    @Override
    public Customers checkviptoid(String userid) {
        return vipdao.findcustomerbyid(userid);
    }

    @Override
    public List<Address> findadbyid(String rid) {
        return vipdao.findadbyid(rid);
    }
}
