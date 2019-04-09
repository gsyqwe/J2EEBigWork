package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.Vipservice;
import com.example.demo.serviceImpl.VipserviceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/vipcontroller")
public class VipController {
    Vipservice vipservice=new VipserviceImpl();

    /**
     * @Author : gsy
     * @Desciption :根据用户id去注销用户
     */
    @GetMapping("/unsubscribe/{userid}")
    public void unsubscribe(@PathVariable("userid")String userid){
        vipservice.unsubscribe(userid);
    }

    /**
     * @Author : gsy
     * @Desciption : 查看详情
     */
    @GetMapping("/getdetail/{lid}")
    public String getdetail(@PathVariable("lid")String lid){
        return new JSONArray(vipservice.getdetail(lid)).toString();
    }

    /**
     * @Desciption : 根据用户id订餐
     * @param userid 用户id
     * @param did 餐厅id
     * @param money 价格
     * @param foodid 食物id 多个食物以;隔开，便于根据食物id去减少食物的库存，防止超卖
     * @param foodname 食物名称 用以保存为详情的名称
     * @param foodnum 食物数量
     * @param price 价格，一种食物数量*其单价
     */
    @GetMapping("/order/{addressid}/{userid}/{did}/{dname}/{money}/{foodid}/{foodname}/{foodnum}/{price}")
    public void order(@PathVariable("addressid")String addressid,@PathVariable("userid")String userid,
                      @PathVariable("did")String did, @PathVariable("money")String money,
                      @PathVariable("foodid")String foodid, @PathVariable("foodname")String foodname,
                      @PathVariable("foodnum")String foodnum, @PathVariable("price")String price,
                      @PathVariable("dname")String dname){
        Information information=new Information();
        try {
        information.setFoodname(java.net.URLDecoder.decode(foodname,"UTF-8"));
        information.setFoodnum(java.net.URLDecoder.decode(foodnum,"UTF-8"));
        information.setPrice(java.net.URLDecoder.decode(price,"UTF-8"));
        Record record=new Record();
        record.setAid(Integer.parseInt(addressid));
        record.setDid(did);
        record.setMoney(Double.parseDouble(money));
        record.setState(State.SUBSCRIBE);
        record.setUid(userid);
        record.setDname(dname);
        Date subtime = new Date();//创建时间
        record.setDate(subtime);
        record.setBalance(false);
        vipservice.order(userid,java.net.URLDecoder.decode(foodid,"UTF-8"),foodnum,record,information);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : gsy
     * @Desciption : 根据用户id和订单的id去支付
     * @return : 是否足够支付
     */
    @GetMapping("/pay/{userid}/{rid}")
    public String pay(@PathVariable("userid")String userid,@PathVariable("rid")String rid){
        if (vipservice.checktime(userid,rid)) {
            return vipservice.pay(userid, rid);
        }else{
            vipservice.cancleorder(userid,rid);
            return "订单超时,已经自动取消";
        }
    }

    /**
     * @Author : gsy
     * @Desciption : 根据用户id和订单的id取消订单
     */
    @GetMapping("/cancleorder/{userid}/{rid}")
    public void cancleorder(@PathVariable("userid")String userid,@PathVariable("rid")String rid){
        vipservice.cancleorder(userid,rid);
    }

    /**
     * @Author : gsy
     * @Desciption : 确认收货
     */
    @GetMapping("/confirm/{uid}/{rid}")
    public void confirm(@PathVariable("uid")String uid,@PathVariable("rid")String rid){
        vipservice.confirm(uid,rid);
    }

    /**
     * @Author : gsy
     * @Desciption : 根据userid和状态来查看订单
     */
    @GetMapping("/checktostate/{userid}/{state}")
    public String checktostate(@PathVariable("userid")String userid,@PathVariable("state")String state){
        return new JSONArray(vipservice.checktostate(userid,state)).toString();
    }


    /**
     * @Author : gsy
     * @Desciption : 增加地址和电话
     */
    @GetMapping("/addphoneaddress/{userid}/{phone}/{address}")
    public void addphoneaddress(@PathVariable("userid")String userid,@PathVariable("phone")String phone,
                                @PathVariable("address")String address){
        vipservice.addAddress(userid,address,phone);
    }

    /**
     * @Author : gsy
     * @Desciption : 修改姓名
     */
    @GetMapping("/devisename/{userid}/{username}/{email}/{sum}")
    public void devisename(@PathVariable("userid")String userid,@PathVariable("username")String username,
                           @PathVariable("email")String email,@PathVariable("sum")String sum){
        vipservice.deviseName(userid,username,email,sum);
    }

    /**
     * @Author : gsy
     * @Desciption : 修改地址和电话
     */
    @GetMapping("/devisephoneaddress/{aid}/{phone}/{address}")
    public void devisephoneaddress(@PathVariable("aid")String aid,@PathVariable("phone")String phone,
                                   @PathVariable("address")String address){
        vipservice.devisePhoneAddress(aid,phone,address);
    }

    /**
     * @Author : gsy
     * @Desciption : 查看会员订单记录，包括退订，支付等所有信息
     */
    @GetMapping("/checkrecorder/{userid}")
    public String checkorder(@PathVariable("userid")String userid){
        return new JSONArray(vipservice.checkrecord(userid)).toString();
    }

    /**
     *@Author : gsy
     *@Desciption : 根据userid得到具体的会员信息
     */
    @GetMapping("/checkviptoid/{userid}")
    public String checkviptoid(@PathVariable("userid")String userid){
        Customers customers=vipservice.checkviptoid(userid);
        return customers.getName()+";"+customers.getLevel()+";"+customers.getEmail()+";"+customers.getMoney()+";"+customers.getSum();
    }

    /**
     *@Author : gsy
     *@Desciption : 根据userid查看user的地址和电话
     */
    @GetMapping("/getuseraddress/{userid}")
    public String getuseraddress(@PathVariable("userid")String userid){
        return new JSONArray(vipservice.getuseraddress(userid)).toString();
    }

    /**
     *@Author : gsy
     *@Desciption : 查看全部可选的会员
     */
    @GetMapping("/getallcustomer")
    public String getallcustomer(){
        return new JSONArray(vipservice.getallcustomer()).toString();
    }

    /**
     * @Author : gsy
     * @Desciption : 得到地址byid
     */
    @GetMapping("/findadbyid/{aid}")
    public List<Address> findadbyid(@PathVariable("aid")String aid){
        return vipservice.findadbyid(aid);
    }
}
