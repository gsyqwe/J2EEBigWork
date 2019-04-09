<template>
  <transition
    appear
    appear-class="custom-appear-class"
    appear-active-class="custom-appear-active-class"
  >
  <div class="bg" id="bg" :style="{height: mapHeight}">
    <Modal v-model="dinflag">
      餐厅注册成功，登录码为:{{dincode}}
      <div slot="footer">
        <Button type="text" @click="dinconfirm">确认</Button>
      </div>
    </Modal>

    <div class="login" style="margin-top:210px;margin-left: 65%;  " v-if="isshowing">
      <div class="header">
        <div class="switch" id="switch">
          <a class="switch_btn_focus" id="switch_qlogin"  tabindex="7" @click="changetag(0)" >快速登录</a>
          <a class="switch_btn" id="switch_login"  tabindex="8" @click="changetag(1)">快速注册</a><div class="switch_bottom" id="switch_bottom" style="position: absolute; width: 64px; left: 0px;"></div>
        </div>
      </div>

      <Form v-show="tagdisplay.flag1" ref="formCustom" :model="formCustom" :label-width="80">
        <FormItem label="用户名：" prop="userID" style="margin-top: 20px">
            <i-input type="text" v-model="formCustom.userID" style="width: 250px" >
              <Icon type="ios-person-outline" slot="prepend"></Icon>
             </i-input>
        </FormItem>
        <FormItem label="密码：" prop="password">
          <i-input type="password" v-model="formCustom.password" style="width: 250px">
            <Icon type="ios-unlock-outline" slot="prepend"></Icon>
          </i-input>
        </FormItem>

        <FormItem label="验证码："  prop="code">
          <i-input type="text" v-model="formCustom.code" style="width: 100px;float: left"/>
          <codetest style="margin-left: 5%;float: left;margin-top: -2px;border-radius: 5px" ></codetest>
        </FormItem>

        <FormItem >
          <Button type="primary" @click="handleSubmit('formCustom')"  style="margin-left: 2%">登录</Button>
          <Button type="ghost" @click="handleReset('formCustom')" style="margin-left: 5%">重置</Button>
        </FormItem>
      </Form>

      <Form v-show="tagdisplay.flag2" ref="registerformCustom" :model="registerformCustom"  :label-width="80">
        <FormItem label="身份：" >
          <RadioGroup v-model="registerformCustom.registerIdentity" style="margin-left: -100px">
            <Radio label="会员" @click.native="changetovip"></Radio>
            <Radio label="餐厅" @click.native="changetodin"></Radio>
          </RadioGroup>
        </FormItem>
        <FormItem :label="registershow.label1" prop="registeruserID" style="margin-top: 20px">
          <i-input type="text" v-model="registerformCustom.registeruserID" style="width: 250px">
            <Icon type="ios-person-outline" slot="prepend"></Icon>
          </i-input>
        </FormItem>
        <FormItem :label="registershow.label2" prop="registerPassword">
          <i-input type="text" v-model="registerformCustom.registeruserName" style="width: 250px">
            <Icon type="ios-person-outline" slot="prepend"></Icon>
          </i-input>
        </FormItem>
        <FormItem :label="registershow.label3" prop="registerPasswordComfirm">
          <i-input type="password" v-model="registerformCustom.registerPassword" style="width: 250px">
            <Icon type="ios-unlock-outline" slot="prepend"></Icon>
          </i-input>
        </FormItem>
        <FormItem :label="registershow.label4" prop="registerEmail">
          <i-input type="email" id="emailinput" v-model="registerformCustom.registerEmail" style="width: 250px">
            <Icon type="ios-mail" slot="prepend"></Icon>
          </i-input>
        </FormItem>

        <FormItem :label="registershow.label5"  prop="registerCode">
          <i-input type="text" v-show="dinphoneflag" v-model="registerformCustom.registerCode" style="width: 250px;float: left"/>
          <i-input type="text" v-show="vipmailflag" v-model="registerformCustom.registerCode" style="width: 100px;float: left"/>
          <Button id="sendmail" v-show="mailflag" @click="sendEmail" style="margin-left: 10%;" :disabled="canGetIdentifyCode">{{buttonContent}}</Button>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="handleSubmit('registerformCustom')" style="margin-left: 40px">注册</Button>
          <Button type="ghost" @click="handleReset()" style="margin-left: 8px">重置</Button>
        </FormItem>
      </Form>
    </div>
  </div>
  </transition>
</template>

<script>
  import codetest from '@/components/login/Codetest'
  import $ from 'jquery'

  export default {
    created(){
      this.init()
    },

    components:{
      codetest
    },
    data () {
      return {
        dinphoneflag:false,
        vipmailflag:true,
        mailflag:true,
        dinflag:false,
        dincode:'',
        isshowing:false,
        buttonContent:'发送验证码',
        canGetIdentifyCode:false,
        tagdisplay:{
          flag1:true,
          flag2:false
        },
        formCustom: {
          userID: '',
          password: '',
          code:'',
        },
        //用来显示前面的提示输入符号
        registershow:{
          label1:'用户名:',
          label2:'用户昵称:',
          label3:'密码:',
          label4:'邮箱:',
          label5:'验证码:',
        },
        //注册信息保留
        registerformCustom:{
          registeruserID:'',
          registeruserName:'',
          registerPassword:'',
          registerIdentity:'会员',
          registerEmail:'',
          registerCode:''
        },
      }
    },
    computed: {
      //计算浏览器的高度
      mapHeight() {return $(window).height() +'px'}

    },
    methods: {
      changetovip(){
        this.registershow.label1="用户名:"
        this.registershow.label2="用户昵称:"
        this.registershow.label3="密码:"
        this.registershow.label4="邮箱:"
        this.registershow.label5="验证码:"
        this.mailflag=true
        this.vipmailflag=true
        this.dinphoneflag=false
        this.handleReset(this.registerformCustom)
      },
      changetodin(){
        this.registershow.label1="餐厅名称:"
        this.registershow.label2="餐厅地址:"
        this.registershow.label3="餐厅密码:"
        this.registershow.label4="餐厅类型:"
        this.registershow.label5="餐厅电话:"
        this.mailflag=false
        this.vipmailflag=false
        this.dinphoneflag=true
        this.handleReset(this.registerformCustom)
      },
      dinconfirm(){
        this.dinflag=false
        this.changetag(0)
      },
      init(){
        this.isshowing=true
      },

      show(){
        console.log("show"+window.localStorage.getItem("code"))
      },

      sendEmail(){
        if(this.registerformCustom.registerEmail===''){
          this.$Message.warning("邮箱不能为空！")
          return
        }
        var m=this;
        this.$http.get('api/LoginController/SendCheck/'+m.registerformCustom.registerEmail,{
        }).then(function (response) {
          m.$Message.success("发送成功");
        })

        this.wait()
      },

      //登录方法
      login(){
        var self=this
        var identity;
        var userID=this.formCustom.userID
        this.$http.get('api/LoginController/login/'+this.formCustom.userID+"/"+this.formCustom.password,{
        })
          .then(function (response) {
            identity=response.data
            window.localStorage.removeItem("identity")
            if(identity=='vip') {
              window.localStorage.setItem("identity","vip")
              self.$router.push({
                name: 'vip',
              })
            }
            else if(identity=='dinning'){
              window.localStorage.setItem("identity","dinning")
              self.$router.push({
                name: 'dinning',
              })
            }
            else if(identity=='manager'){
              window.localStorage.setItem("identity","manager")
              self.$router.push({
                name: 'manager',
              })
            }
            else {
              self.$Message.warning("密码或账号错误或已被注销")
            }
          } )
          .catch(function (error) {alert(error) })

        window.localStorage.removeItem("userID");
        window.localStorage.setItem("userID",userID)
        var curtime = new Date().getTime()
        window.localStorage.setItem("time",JSON.stringify(curtime))
      },

      //注册
      register(){
        if(this.registerformCustom.registerIdentity=='会员'){
          var self=this
          this.$http.get('api/LoginController/vipregister/'+this.registerformCustom.registeruserID+"/"+
          this.registerformCustom.registerPassword+"/"+this.registerformCustom.registeruserName+"/"+
          this.registerformCustom.registerEmail+"/"+this.registerformCustom.registerCode,{
          }).then(function (response) {
            if (response.data=="验证码错误"){
              self.$Message.warning("验证码错误")
            }else{
              self.$Message.success("注册成功")
              self.tagdisplay.flag1=true
              self.tagdisplay.flag2=false
              self.handleReset()
            }
          })
        }else if (this.registerformCustom.registerIdentity=='餐厅'){
          var self=this
          this.$http.get('api/LoginController/dinregister/'+this.registerformCustom.registeruserID+"/"+
          this.registerformCustom.registerPassword+"/"+this.registerformCustom.registeruserName+"/"+
          this.registerformCustom.registerEmail+"/"+this.registerformCustom.registerCode,{
          }).then(function (response) {
            self.dincode=response.data
            self.dinflag=true
            self.handleReset()
          })
        }
      },


      wait(){
        this.canGetIdentifyCode = true;
        let timeLast = 60;
        let timer = setInterval(() => {
          if (timeLast >= 0) {
            this.buttonContent = timeLast + '秒后重试';
            timeLast -= 1;
          } else {
            clearInterval(timer);
            this.buttonContent = '获取验证码';
            this.canGetIdentifyCode = false;
          }
        }, 1000);
      },

      changetag(num){
        if (num==0){
            this.tagdisplay.flag1=true
            this.tagdisplay.flag2=false
        }
        else {
          this.tagdisplay.flag1=false
          this.tagdisplay.flag2=true
        }
      },
      handleSubmit (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            if(this.tagdisplay.flag1==true)
              this.login()
            else
              this.register()
          } else {
            this.$Message.error('Fail!');
          }
        })
      },
      handleReset () {
        this.registerformCustom.registerPassword=""
        this.registerformCustom.registeruserName=""
        this.registerformCustom.registerCode=""
        this.registerformCustom.registerEmail=""
        this.registerformCustom.registeruserID=""
        this.registerformCustom.registerIdentity=""
        this.formCustom.userID=""
        this.formCustom.password=""
        this.formCustom.code=""
      }
    }
  }

  $(function(){

    $('#switch_qlogin').click(function(){
      $('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
      $('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
      $('#switch_bottom').animate({left:'0px',width:'70px'});
      $('#qlogin').css('display','none');
      $('#web_qr_login').css('display','block');

    });
    $('#switch_login').click(function(){

      $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
      $('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
      $('#switch_bottom').animate({left:'154px',width:'70px'});

      $('#qlogin').css('display','block');
      $('#web_qr_login').css('display','none');
    });
    if(getParam("a")=='0')
    {
      $('#switch_login').trigger('click');
    }

  });

</script>

<style scoped>
  .custom-appear-class{
    transition: opacity 5s;
  }

  .custom-appear-active-class{
    opacity: 0;
  }

  .bg{
    background-image: url('../../images/login_bg.jpg');
    background-size: cover;
    border: 1px solid white;
  }

 html,body,.bg{height: 100%;}
  ul {
    padding:0;
    margin:0;
  }
  ul li {
    list-style-type:none;
  }
  a {
    text-decoration:none;
  }
  a:hover {
    text-decoration:none;color:#f00;
  }
  .cl{ clear: both;}
  input[type="text"]:focus, input[type="password"]:focus {
    outline:none;
  }
  input::-ms-clear {
    display:none;
  }
  .login {
    margin:0 auto;
    width:370px;
    border:2px solid #eee;
    border-bottom:none;
    position:relative;
  }
  .header {
    height:50px;
    border-bottom:1px solid #e2e2e2;
    position:relative;
    font-family:"Microsoft Yahei";
  }
  .header .switch {
    height:45px;
    position:absolute;
    left:60px;
    bottom:0;
    font-size:16px;
  }
  .header .switch #switch_qlogin {
    margin-right:85px;
  }
  .header .switch .switch_btn {
    color:#999;
    display:inline-block;
    height:45px;
    line-height:45px;
    outline:none;
    *hide-focus:expression(this.hideFocus=true);
  }
  .header .switch .switch_btn_focus {
    color:#333;
    display:inline-block;
    height:45px;
    line-height:45px;
    outline:none;
    *hide-focus:expression(this.hideFocus=true);
  }
  .header .switch .switch_btn:hover {
    color:#333;
    text-decoration:none;
  }
  .header .switch .switch_btn_focus:hover {
    text-decoration:none;
  }
  #switch_bottom {
    position:absolute;
    bottom:-1px;_bottom:-2px;
    border-bottom:2px solid #848484;
  }

  .web_login {
    width:370px;
    position:relative;
  }
  #web_login{_left:60px;*left:0;}
  .web_login .login_form {
    width:272px;
    margin:0 auto;
  }
  .web_login .reg_form {
    width:300px;
    margin:0 auto;
  }
  .web_login .input-tips {
    float:left;
    margin-top:10px;
    width:50px;
    height:42px;
    font-size:16px;
    line-height:42px;
    font-family:"Hiragino Sans GB", "Microsoft Yahei";
  }
  .web_login .input-tips2 {
    float:left;
    text-align:right;
    padding-right:10px;
    width:75px;
    height:30px;
    font-size:16px;
    margin-top:10px;
    clear:both;
    line-height:30px;
    font-family:"Hiragino Sans GB", "Microsoft Yahei";
  }
  .web_login .inputOuter {
    width:200px;
    height:42px;
    margin-top:10px;
    float:left;

  }
  .web_login .inputOuter2 {
    width:200px;
    margin-top:6px;margin-top:5px\9;
    float:left;

  }
  .web_login .inputstyle {
    width:200px;
    height:38px;
    padding-left:5px;
    line-height:30px;line-height:38px;
    border:1px solid #D7D7D7;
    background:rgba(255, 255, 255, 0.32);;
    color:#333;border-radius:2px;
    font-family:Verdana, Tahoma, Arial;
    font-size:16px;
    ime-mode:disabled;
  }
  .web_login input.inputstyle2:focus,.web_login input.inputstyle:focus{border:1px solid #198BD4;box-shadow:0 0 2px #198BD4;}
  .web_login .inputstyle2 {
    width:200px;
    height:34px;
    padding-left:5px;
    line-height:34px;
    border:1px solid #D7D7D7;
    background:rgba(255, 255, 255, 0.32);
    color:#333;border-radius:2px;
    font-family:Verdana, Tahoma, Arial;
    font-size:16px;
    ime-mode:disabled;
  }
  .web_login .uinArea {
    height:55px;
    position:relative;
    z-index:10;
  }
  .web_login .pwdArea {
    height:55px;
    margin-bottom:10px;
    position:relative;
    z-index:3;
  }
  .web_qr_login {
    position:relative;

    overflow:hidden;
  }

  .login {
    background-color:rgba(255, 255, 255, 0.32);
  }

  h1{margin:80px auto 50px auto;text-align:center;color:#fff;margin-left:-25px;font-size:35px;font-weight: bold;text-shadow: 0px 1px 1px #555;}
  h1 sup{
    font-size: 18px;
    font-style: normal;
    position: absolute;
    margin-left: 10px;}
  .login {border:0;padding:5px 0;
    background: rgba(255,255,255,0.5);;
    margin: 0 auto;
    -webkit-box-shadow: 1px 1px 2px 0 rgba(0, 0, 0, .3);
    box-shadow: 1px 1px 2px 0 rgba(0, 0, 0, .3);}

  .web_login{padding-bottom:20px;}

  .jianyi{color:#fff;text-align:center;margin-top:25px;color:#B3B8C4;}
  .reg_form li {
    height: 55px;
  }

  .web_login input.inputstyle2:focus, .web_login input.inputstyle:focus {
    border: 1px solid #5796f;
    box-shadow: 0 0 0;
  }
  .web_login .reg_form {
    width: 300px;
    margin: 0 auto;
  }
  .web_login .inputstyle2 {border-radius:2px;width:210px;}
  .web_login .input-tips2 {
    padding-right: 5px;
    width: 80px;_width: 75px;_font-size:12px;}
  .button_blue
  {
    display:inline-block;
    float:left;
    height:41px;border-radius:4px;
    background:#2795dc;border:none;cursor:pointer;
    border-bottom:3px solid #0078b3;*border-bottom:none;
    color:#fff;
    font-size:16px;padding:0 10px;*width:140px;
    text-align:center;outline:none;font-family: "Microsoft Yahei",Arial, Helvetica, sans-serif;
  }
  input.button_blue:hover
  {
    background:#0081c1;
    border-bottom:3px solid #006698;*border-bottom:none;
    color:#fff;
    text-decoration:none;
  }
  a.zcxy {text-decoration: underline;line-height:58px;margin-left:15px;color: #959ca8;}
  .web_login .login_form {margin-top:30px;}
  .web_login .uinArea {
    height: 60px;}
  .header .switch{left:70px;}


</style>
