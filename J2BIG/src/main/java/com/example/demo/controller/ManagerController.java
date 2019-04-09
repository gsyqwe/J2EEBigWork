package com.example.demo.controller;

import com.example.demo.service.Managerservice;
import com.example.demo.serviceImpl.ManagerserviceImpl;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/managercontroller")
public class ManagerController {
    Managerservice managerservice=new ManagerserviceImpl();

    /**
     * @Author : gsy
     * @Desciption : 查看未结算的订单
     */
    @GetMapping("/getallunbalance")
    public String getallunbalance(){
        return new JSONArray(managerservice.getunbalance()).toString();
    }

    /**
     *@Author : gsy
     *@Desciption : 得到全部待审核的餐厅信息
     */
    @GetMapping("/getcheckdin")
    public String getcheckdin(){
        return new JSONArray(managerservice.getcheckdin()).toString();
    }

    /**
     *@Author : gsy
     *@Desciption : 审核通过修改申请
     * @param : meid 信息的id
     * @param : did 餐厅的id
     * */
    @GetMapping("/pass/{meid}/{did}")
    public void pass(@PathVariable("meid")String meid,@PathVariable("did")String did){
        managerservice.pass(meid,did);
    }

    /**
     *@Author : gsy
     *@Desciption : 结算
     */
    @GetMapping("/balance")
    public void balance(){
        managerservice.balance();
    }


    /**
     * @Author:gsy
     * @Desciption : 查看会员的所有订单
     */
    @GetMapping("/checkvipor/{userid}")
    public String checkvipor(@PathVariable("userid")String userid){
        return new JSONArray(managerservice.getvipor(userid)).toString();
    }

    /**
     * @Author:gsy
     * @Desciption : 查看餐厅的所有订单
     */
    @GetMapping("/checkdinor/{did}")
    public String checkdinor(@PathVariable("did")String did){
        return new JSONArray(managerservice.getdinor(did)).toString();
    }

    /**
     * @Author:gsy
     * @Desciption : 查看所有的餐厅
     */
    @GetMapping("/getalldin")
    public String getalldin(){
        return new JSONArray(managerservice.getalldin()).toString();
    }

    /**
     *@Author : gsy
     *@Desciption : 查看yummy财务情况
     */
    @GetMapping("/checkyummy")
    public String checkyummy(){
        return new JSONArray(managerservice.checkyummy()).toString();
    }

}
