package com.example.demo.controller;

import com.example.demo.domain.Combomenu;
import com.example.demo.domain.Dinning;
import com.example.demo.domain.Meal;
import com.example.demo.domain.Menu;
import com.example.demo.service.Dinningservice;
import com.example.demo.serviceImpl.DinningserviceImpl;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/dinningcontroller")
public class DinningController {

    private Dinningservice dinningservice=new DinningserviceImpl();


    /**
     * @Author : gsy
     * @Desciption : 得到餐厅是否可以发布信息
     */
    @GetMapping("/getorderflag/{did}")
    public boolean getorderflag(@PathVariable("did")String did){
        return dinningservice.getorderflag(did);
    }
    /**
     *@Author : gsy
     *@Desciption : 修改餐厅信息
     */
    @GetMapping("/devisedin/{did}/{dname}/{address}/{kind}/{phone}")
    public void devisedin(@PathVariable("did")String did,@PathVariable("dname")String dname,
                          @PathVariable("address")String address, @PathVariable("kind")String kind,
                          @PathVariable("phone")String phone){
        dinningservice.devisedin(did,dname,address,kind,phone);
    }

    /**
     *@Author : gsy
     *@Desciption : 根据餐厅id得到餐厅的菜品Meal,得到该餐厅有什么样的菜品，用于发布
     */
    @GetMapping("/getmealbyid/{did}")
    public String getmealbyid(@PathVariable("did")String did){
        return new JSONArray(dinningservice.getmealbyid(did)).toString();
    }

    /**
     *@Author : gsy
     *@Desciption : 根据餐厅id,得到该餐厅的套餐
     */
    @GetMapping("/getcombomealbyid/{did}")
    public String getcombomealbyid(@PathVariable("did")String did){
        return new JSONArray(dinningservice.getcombomealbyid(did)).toString();
    }

    /**
     *@Author : gsy
     *@Desciption : 根据餐厅id,添加菜品
     */
    @GetMapping("/addmeal/{mname}/{mprice}/{murl}/{did}/{enddate}")
    public void addmeal(@PathVariable("mname")String mname,@PathVariable("mprice")String price,
                        @PathVariable("murl")String murl,@PathVariable("did")String did,
                        @PathVariable("enddate")String enddate){
        Meal meal=new Meal();
        meal.setDid(did);
        meal.setName(mname);
        meal.setPrice(Double.parseDouble(price));
        meal.setNum(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            meal.setUrl("http://localhost:9090/13.jpg");
            meal.setEnddate(sdf.parse(enddate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dinningservice.addmeal(meal);
    }

    /**
     *@Author : gsy
     *@Desciption : 根据餐厅id,添加套餐
     */
    @GetMapping("/addcombomeal/{coname}/{comid}/{comname}/{comnum}/{coprice}/{courl}/{did}/{enddate}")
    public void addcombomeal(@PathVariable("coname")String coname,@PathVariable("comid")String comid,
                             @PathVariable("comname")String comname,@PathVariable("comnum")String comnum,
                             @PathVariable("coprice")String coprice,@PathVariable("did")String did,
                             @PathVariable("courl")String courl,@PathVariable("enddate")String enddate){
        Combomenu combomenu=new Combomenu();
        combomenu.setDid(did);
        combomenu.setName(coname);
        combomenu.setPrice(Double.parseDouble(coprice));
        combomenu.setCourl("http://localhost:9090/14.jpg");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            combomenu.setMnum(java.net.URLDecoder.decode(comnum,"UTF-8"));
            combomenu.setMid(java.net.URLDecoder.decode(comid,"UTF-8"));
            combomenu.setMname(java.net.URLDecoder.decode(comname,"UTF-8"));
            combomenu.setEnddate(sdf.parse(enddate));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dinningservice.addcombomeal(combomenu);
    }

    /**
     *@Author : gsy
     *@Desciption : 根据did得到当前日期的菜单
     */
    @GetMapping("/getmenu/{did}")
    public String getMenu(@PathVariable("did")String did){
        return dinningservice.getmenu(did);
    }

    /**
     *@Author : gsy
     *@Desciption : 根据mid得到具体信息,mid中带有逗号，得到一个类别的所有的餐品的具体信息
     */
    @GetMapping("/getdetail/{mid}")
    public String getdetail(@PathVariable("mid")String mid){
        return new JSONArray(dinningservice.getdetail(mid)).toString();
    }

    /**
     *@Author : gsy
     *@Desciption : 根据coid,带有逗号的coid,得到具体套餐
     */
    @GetMapping("/getcobomenu/{coid}")
    public String getcobomenu(@PathVariable("coid")String coid){
        return new JSONArray(dinningservice.getcobomenu(coid)).toString();
    }

    /**
     *@Author : gsy
     *@Desciption : 根据mid和mnum去更新菜品
     */
    @GetMapping("/updatemeal/{mid}/{mnum}")
    public void updatemeal(@PathVariable("mid")String mid,@PathVariable("mnum")String mnum){
        dinningservice.updatemeal(mid,mnum);
    }

    /**
     *@Author : gsy
     *@Desciption : 发布菜单
     */
    @GetMapping("/publishmenu/{did}/{startdate}/{enddate}/{kind}/{mid}/{mnum}/{cid}")
    public void publishmenu(@PathVariable("did")String did,@PathVariable("startdate")String startdate,
                            @PathVariable("enddate")String enddate,@PathVariable("kind")String kind,
                            @PathVariable("mid")String mid,@PathVariable("mnum")String mnum,
                            @PathVariable("cid")String cid){
        Menu menu=new Menu();
        try {
            menu.setKind(java.net.URLDecoder.decode(kind,"UTF-8"));
            menu.setMid(java.net.URLDecoder.decode(mid,"UTF-8"));
            menu.setMnum(java.net.URLDecoder.decode(mnum,"UTF-8"));
            menu.setCid(java.net.URLDecoder.decode(cid,"UTF-8"));
            menu.setDid(did);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            menu.setStartdate(sdf.parse(startdate));
            menu.setEnddate(sdf.parse(enddate));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dinningservice.publishmenu(menu);
    }

    /**
     *@Author : gsy
     *@Desciption : 根据did查看一个餐厅的具体的信息
     */
    @GetMapping("/checkdin/{did}")
    public String checkdin(@PathVariable("did")String did){
        Dinning dinning=dinningservice.checkdin(did);
        return dinning.getName()+";"+dinning.getKind()+";"+dinning.getAddress()+";"+dinning.getPhone()
                +";"+dinning.getMoney()+";"+dinning.isState();
    }

    /**
     *@Author : gsy
     *@Desciption : 根据did查看送餐记录
     */
    @GetMapping("/checkorder/{did}")
    public String checkorder(@PathVariable("did")String did){
        return new JSONArray(dinningservice.checkorder(did)).toString();
    }

    /**
     * @Author : gsy
     * @Desciption : 得到可被点餐的餐厅既state = true
     */
    @GetMapping("/getusedin")
    public String getuserdin(){
        return new JSONArray(dinningservice.getusedin()).toString();
    }

    /**
     *@Author : gsy
     *@Desciption : 得到全部可选的餐厅,用于给manager查看所有餐厅
     */
    @GetMapping("/getalldin")
    public String getalldin(){
        return new JSONArray(dinningservice.getalldin()).toString();
    }
}
