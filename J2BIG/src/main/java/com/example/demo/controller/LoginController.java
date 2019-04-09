package com.example.demo.controller;

import com.example.demo.domain.Customers;
import com.example.demo.domain.Dinning;
import com.example.demo.domain.Message;
import com.example.demo.service.Dinningservice;
import com.example.demo.service.Loginservice;
import com.example.demo.service.Vipservice;
import com.example.demo.serviceImpl.DinningserviceImpl;
import com.example.demo.serviceImpl.LoginserviceImpl;
import com.example.demo.serviceImpl.VipserviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/LoginController")
public class LoginController {
    private Vipservice vipservice=new VipserviceImpl();

    private Loginservice loginservice=new LoginserviceImpl();

    private Dinningservice dinningservice=new DinningserviceImpl();
    /**
     *@Author : gsy
     *@Desciption : 根据用户名和密码登录
     *@return : 身份 / 密码错误
     */
    @GetMapping("/login/{userid}/{password}")
    public String Login(@PathVariable("userid")String userid,@PathVariable("password")String password){
        return loginservice.login(userid,password);
    }

    /**
     *@Author : gsy
     *@Desciption : 发送验证码给会员
     */
    @GetMapping("/SendCheck/{receivemail}")
    public void sendcheck(@PathVariable("receivemail")String receivemail){
        System.out.println(receivemail);
        vipservice.sendcheck(receivemail);
    }

    /**
     *@Author : gsy
     *@Desciption : 会员注册
     *@param check 验证码
     */
    @GetMapping("/vipregister/{userid}/{upassword}/{uname}/{email}/{check}")
    public String vipregister(@PathVariable("userid")String userid,@PathVariable("upassword")String upassword,
                            @PathVariable("uname")String uname,@PathVariable("email")String email,
                            @PathVariable("check")String check){
        Customers customers=new Customers();
        customers.setUserid(userid);
        customers.setPassword(upassword);
        customers.setName(uname);
        customers.setEmail(email);
        customers.setLevel(1);
        customers.setAid("");
        customers.setState(true);
        return vipservice.register(customers,check);
    }

    /**
     * @Author : gsy
     * @Desciption : 餐厅注册
     * @return 返回登录码
     */
    @GetMapping("/dinregister/{dname}/{dpassword}/{daddress}/{dkind}/{phone}")
    public String dinregister(@PathVariable("dname")String dname,@PathVariable("dpassword")String dpassword,
                            @PathVariable("daddress")String daddress,@PathVariable("dkind")String dkind,
                            @PathVariable("phone")String phone){
        Dinning dinning=new Dinning();
        dinning.setName(dname);
        dinning.setPassword(dpassword);
        dinning.setKind(dkind);
        dinning.setPhone(phone);
        dinning.setAddress(daddress);
        dinning.setState(false);
        return dinningservice.register(dinning);
    }
}
