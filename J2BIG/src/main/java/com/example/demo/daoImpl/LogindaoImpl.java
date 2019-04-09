package com.example.demo.daoImpl;

import com.example.demo.dao.Logindao;
import com.example.demo.domain.Customers;
import com.example.demo.domain.Dinning;
import com.example.demo.domain.Manager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LogindaoImpl implements Logindao {
    @Override
    public List<Customers> login() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Customers ");
        List<Customers> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List<Dinning> dinlogin() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Dinning ");
        List<Dinning> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List<Manager> mlogin() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Manager ");
        List<Manager> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }
}
