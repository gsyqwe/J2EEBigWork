package com.example.demo.dao;

import com.example.demo.domain.Customers;
import com.example.demo.domain.Dinning;
import com.example.demo.domain.Manager;

import java.util.List;

public interface Logindao {
    List<Customers> login();
    List<Dinning> dinlogin();
    List<Manager> mlogin();
}
