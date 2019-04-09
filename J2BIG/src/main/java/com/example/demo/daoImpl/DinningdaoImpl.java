package com.example.demo.daoImpl;

import com.example.demo.dao.Dinningdao;
import com.example.demo.domain.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DinningdaoImpl implements Dinningdao {

    @Override
    public List<Dinning> getalldin() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Dinning ");
        List list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public Dinning getdinbyid(String did) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Dinning where id="+"'"+did+"'");
        Dinning dinning=(Dinning)query.list().get(0);
        transaction.commit();
        session.close();
        return dinning;
    }

    @Override
    public void updatedin(Dinning dinning) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.update(dinning);
        transaction.commit();
        session.close();
    }

    @Override
    public void register(Dinning dinning) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.save(dinning);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Meal> getmealbyid(String did) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Meal where Did="+"'"+did+"'" + "and enddate > current_date");
        List<Meal> meals=query.getResultList();
        transaction.commit();
        session.close();
        return meals;
    }

    //根据did得到套餐
    @Override
    public List<Combomenu> getcombbyid(String did) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Combomenu where did="+"'"+did+"'" + "and enddate > current_date");
        List<Combomenu> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void insertMeal(Meal meal) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.save(meal);
        transaction.commit();
        session.close();
    }

    @Override
    public void insertCombeMe(Combomenu combomenu) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.save(combomenu);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Meal> getmeal() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Meal ");
        List<Meal> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List<Combomenu> getcombemenu() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Combomenu ");
        List<Combomenu> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List<Menu> getMenubyid(String did) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Menu where Did="+"'"+did+"'");
        List<Menu> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void publishmenu(Menu menu) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.save(menu);
        transaction.commit();
        session.close();
    }

    @Override
    public Meal findmealbyid(String mid) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Meal where id="+Integer.parseInt(mid));
        Meal meal=(Meal)query.list().get(0);
        transaction.commit();
        session.close();
        return meal;
    }

    @Override
    public void updatemeal(Meal meal) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.update(meal);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Record> checkorder(String did) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Record where Did="+"'"+did+"'");
        List<Record> list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void insertmessage(Message message) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.save(message);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Dinning> getusedin() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Dinning where state = true");
        List list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }
}
