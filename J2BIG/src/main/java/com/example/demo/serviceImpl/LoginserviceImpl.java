package com.example.demo.serviceImpl;

import com.example.demo.dao.Logindao;
import com.example.demo.daoImpl.LogindaoImpl;
import com.example.demo.domain.Customers;
import com.example.demo.domain.Dinning;
import com.example.demo.domain.Manager;
import com.example.demo.service.Loginservice;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class LoginserviceImpl implements Loginservice {
    Logindao logindao=new LogindaoImpl();
    @Override
    public String login(String userid, String upassword) {
        ArrayList<Customers> customersArrayList=(ArrayList)logindao.login();
        for (int i = 0;i<customersArrayList.size();i++){
            if (customersArrayList.get(i).isState()&&customersArrayList.get(i).getUserid().equals(userid)&&
                    customersArrayList.get(i).getPassword().equals(upassword)){
                return "vip";
            }
        }
        ArrayList<Dinning> dinningArrayList=(ArrayList)logindao.dinlogin();
        for (int i = 0;i<dinningArrayList.size();i++){
            if (dinningArrayList.get(i).getId().equals(userid)&&
            dinningArrayList.get(i).getPassword().equals(upassword)){
                return "dinning";
            }
        }
        ArrayList<Manager> managerArrayList=(ArrayList)logindao.mlogin();
        for (int i = 0 ;i<managerArrayList.size();i++){
            if (managerArrayList.get(i).getId().equals(userid)&&
                    managerArrayList.get(i).getPassword().equals(upassword)){
                return "manager";
            }
        }
        return "false";
    }
}
