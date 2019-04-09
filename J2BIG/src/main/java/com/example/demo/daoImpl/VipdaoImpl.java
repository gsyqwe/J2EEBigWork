package com.example.demo.daoImpl;

import com.example.demo.dao.Vipdao;
import com.example.demo.domain.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class VipdaoImpl implements Vipdao {
    @Override
    public List<Address> getuseraddress(String userid) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Customers where userid="+"'"+userid+"'");
        Customers customers=(Customers)query.list().get(0);
        List<Address> addresses=new ArrayList<>();
        if (customers.getAid().equals("")){

        }else {
            String aid[] = customers.getAid().split(";");
            for (int i = 0 ; i <aid.length;i++){
                Query query1=session.createQuery("from Address where id="+Integer.parseInt(aid[i]));
                Address address=(Address)query1.list().get(0);
                addresses.add(address);
            }
        }
        transaction.commit();
        session.close();
        return addresses;
    }

    @Override
    public List<Information> getdetail(int lid) {
        Session session=HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Information where id="+lid);
        List list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void deviseaddress(int aid, String phone, String address) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Address where id="+aid);
        Address address1=(Address)query.list().get(0);
        address1.setAddress(address);
        address1.setPhone(phone);
        session.update(address1);
        transaction.commit();
        session.close();
    }

    //注册方法
    @Override
    public void register(Customers customers) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.save(customers);
        transaction.commit();
        session.close();
    }

    //得到全部用户的方法
    @Override
    public List<Customers> getallcustomer() {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Customers ");
        List list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    //注销用户的方法
    @Override
    public void unsubscribe(String userid) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("UPDATE Customers c SET c.state=false where c.userid = "+"'"+userid+"'");
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    //添加电话和住址
    @Override
    public void addaddress(String userid,String address, String phone) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Customers where userid = "+"'"+userid+"'");
        Customers customers=(Customers)query.list().get(0);
        Address address1=new Address();
        address1.setAddress(address);
        address1.setPhone(phone);
        session.save(address1);
        Query query1=session.createQuery("from Address");
        List<Address> addresses=query1.getResultList();
        if (customers.getAid().equals("")){
            customers.setAid(""+addresses.size());
        }else {
            customers.setAid(customers.getAid() + ";" + addresses.get(addresses.size()-1).getId());
        }
        session.update(customers);
        transaction.commit();
        session.close();
    }

    //修改姓名
    @Override
    public void devisename(String userid, String username,String email,String sum) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Customers where userid="+"'"+userid+"'");
        Customers customers=(Customers)query.list().get(0);
        customers.setName(username);
        customers.setEmail(email);
        customers.setSum(Double.parseDouble(sum));
        session.update(customers);
        transaction.commit();
        session.close();
    }

    //根据有;的foodid来去减少库存
    @Override
    public void deletenum(String foodid,String foodnum) {
        String id[]=foodid.split(";");
        String num[]=foodnum.split(";");
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Meal ");
        List<Meal> meals=query.getResultList();
        for (int i=0;i<meals.size();i++){
            Meal meal=meals.get(i);
            for (int j =0 ;j < id.length;j++){
                if (meal.getId()==Integer.parseInt(id[j])){
                    meal.setNum(meal.getNum()-Integer.parseInt(num[j]));
                }
            }
            session.update(meal);
        }
        transaction.commit();
        session.close();
    }

    //保存information并返回information的id
    @Override
    public int saveInformation(Information information) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.save(information);
        Query query=session.createQuery("from Information ");
        int size=query.list().size();
        transaction.commit();
        session.close();
        return size+1;
    }

    @Override
    public void insertRecord(Record record) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.save(record);
        transaction.commit();
        session.close();
    }

    @Override
    public Customers findcustomerbyid(String userid) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Customers where id="+"'"+userid+"'");
        Customers customers=(Customers)query.list().get(0);
        transaction.commit();
        session.close();
        return customers;
    }

    @Override
    public Record findrecordbyid(String rid) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        int id = Integer.parseInt(rid);
        Query query=session.createQuery("from Record where id="+id);
        Record record=(Record) query.list().get(0);
        transaction.commit();
        session.close();
        return record;
    }

    //支付
    @Override
    public void pay(Customers customers,Record record) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.update(customers);
        session.update(record);
        transaction.commit();
        session.close();
    }

    //收货
    @Override
    public void updaterecord(Record record) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.update(record);
        transaction.commit();
        session.close();
    }

    @Override
    public void updatevip(Customers customers) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        session.update(customers);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Record> checkrecordtostate(String userid, State state) {
        List<Record> results = new ArrayList<>();
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Record where Uid="+"'"+userid+"'");
        List list=query.getResultList();
        ArrayList<Record> records=(ArrayList<Record>) list;
        for(int i = 0 ;i<records.size();i++){
            if (records.get(i).getState() == state){
                results.add(records.get(i));
            }
        }
        transaction.commit();
        session.close();
        return results;
    }

    @Override
    public List<Record> checkrecord(String userid) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Record where Uid="+"'"+userid+"'");
        List list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List<Address> findadbyid(String rid) {
        Session session=HibernateUtils.getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("from Address where id="+Integer.parseInt(rid));
        List list=query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }


}
