package com.example.demo.daoImpl;

import com.example.demo.dao.Managerdao;
import com.example.demo.domain.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ManagerdaoImpl implements Managerdao {
    //得到全部待审核的信息
    @Override
    public List<Message> getcheckdin() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Message where state=false");
        List<Message> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    //根据ID得到message
    @Override
    public Message getmessagebyid(String id) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Message where id="+Integer.parseInt(id));
        Message message=(Message)query.list().get(0);
        transaction.commit();
        session.close();
        return message;
    }

    @Override
    public void updatemessage(Message message) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.update(message);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Record> getunbalance() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Record where balance=false ");
        List<Record> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    //得到yummy的统计信息
    @Override
    public List<Yummy> getyummy() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Yummy ");
        List<Yummy> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void insertyummy(Yummy yummy) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.save(yummy);
        transaction.commit();
        session.close();
    }

    //查看所有会员的订单
    @Override
    public List<Record> getvipor(String uid) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Record where Uid="+"'"+uid+"'");
        List list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List<Dinning> getalldin() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query = session.createQuery("from Dinning ");
        List<Dinning> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    //餐厅的订单
    @Override
    public List<Record> checkdinor(String did) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query = session.createQuery("from Record where Did="+"'"+did+"'");
        List<Record> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void updaterecord(Record record) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.update(record);
        transaction.commit();
        session.close();
    }


}
