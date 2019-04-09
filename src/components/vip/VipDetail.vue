<template>
  <div class="layout">
    <Modal v-model="editpersonalflag" :closable='false' :mask-closable=false :width="500">
      <h3 slot="header" style="color:#2D8CF0">修改个人信息</h3>
      <Form ref="saveForm" :model="saveForm" :label-width="100" label-position="right">
        <FormItem label="昵称">
          <Input v-model="saveForm.username" ></Input>
        </FormItem>
        <FormItem label="邮箱" prop="newPass">
          <Input v-model="saveForm.email"  ></Input>
        </FormItem>
        <FormItem label="余额" prop="rePass">
          <Input v-model="saveForm.sum"  ></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" @click="canclepersonal">取消</Button>
        <Button type="primary"  @click="savepersonal">保存</Button>
      </div>
    </Modal>
    <Layout>
      <Header>
        <div class="layout-nav">
          <label style="font-family:'Helvetica Neue' ;font-size:20px; color: white" >
            <Icon type="md-person" size="30"></Icon>
            {{userForm.username}}
          </label>
        </div>
      </Header>
      <Content>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">用户名</label>
          <Input id="useridInput" type="textarea" :rows="2" style="width: 250px;margin-left: 3px;" v-model="userForm.userid" readonly />
        </div>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">昵　称</label>
          <Input id="usernameInput" type="textarea" :rows="2"  style="width: 250px;margin-left: 3px;" v-model="userForm.username" readonly />
        </div>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">电子邮件</label>
          <Input id="identityInput" type="textarea" :rows="2"  style="width: 250px;margin-left: 3px;" v-model="userForm.email" readonly />
        </div>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">会员等级</label>
          <Input id="briefcaseInput" type="textarea" :rows="2"  style="width: 250px;margin-left: 3px;" v-model="userForm.level" readonly />
        </div>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">消费总额</label>
          <Input id="schollInput" type="textarea" :rows="2"  style="width: 250px;margin-left: 3px;" v-model="userForm.money" readonly />
        </div>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">余　额</label>
          <Input id="sumInput" type="textarea" :rows="2"  style="width: 250px;margin-left: 3px;" v-model="userForm.sum" readonly />
        </div>
        <ButtonGroup style="margin-top: 10%">
          <Button type="info" style="width: 55px;margin-left: -50px" @click="editpersonal">编辑</Button>
          <Button type="info" style="width: 55px;margin-left: 50px" @click="canclevip">注销</Button>
          <Button type="info" style="width: 55px;margin-left: 100px" @click="getback">返回</Button>
        </ButtonGroup>
      </Content>
    </Layout>
  </div>
</template>
<script>
  import MenuItem from "iview/src/components/menu/menu-item";

  export default {
    components: {MenuItem},
    data(){
      return {
        editpersonalflag:false,
        userForm:{
          userid:'',
          username:'',
          email:'',
          level:0,
          money:0,
          sum:0,
        },
        saveForm:{
          username:'',
          email:'',
          sum:0,
        },
      }
    },
    methods : {
      editpersonal(){
        this.editpersonalflag=true
      },
      savepersonal(){
        var self=this
        this.$http.get('api/vipcontroller/devisename/'+this.userForm.userid+"/"+this.saveForm.username+"/"+
        this.saveForm.email+"/"+this.saveForm.sum,{
        }).then(function (response) {
          self.$Message.success("保存成功")
          self.userForm.username=self.saveForm.username
          self.userForm.email=self.saveForm.email
          self.userForm.sum=self.saveForm.sum
          self.editpersonalflag=false
        })
      },
      canclepersonal(){
        this.editpersonalflag=false
        this.saveForm.username=this.userForm.username
        this.saveForm.email=this.userForm.email
        this.saveForm.sum=this.userForm.sum
      },
      getback(){
        this.$router.push({name:"vip"})
      },
      canclevip(){
        var self=this
        this.$http.get('api/vipcontroller/unsubscribe/'+this.userForm.userid,{
        }).then(function (response) {
          self.$Message.success("注销成功")
          self.$router.push({
            name:'Login'
          })
        })
      }
    },
    created(){
      this.userForm.userid=window.localStorage.getItem("userID")
      this.userForm.username=window.localStorage.getItem("username")
      this.userForm.email=window.localStorage.getItem("email")
      this.userForm.level=window.localStorage.getItem("level")
      this.userForm.money=window.localStorage.getItem("money")
      this.userForm.sum=window.localStorage.getItem("sum")
      this.saveForm.username=this.userForm.username
      this.saveForm.email=this.userForm.email
      this.saveForm.sum=this.userForm.sum
    },

  }
</script>
<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
  }

  .layout-nav{
    width: 420px;
    margin: 0 auto;
    margin-right: 20px;
  }
</style>
