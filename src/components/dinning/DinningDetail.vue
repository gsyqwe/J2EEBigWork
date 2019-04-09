<template>
  <div class="layout">
    <Modal v-model="editpersonalflag" :closable='false' :mask-closable=false :width="500">
      <h3 slot="header" style="color:#2D8CF0">修改个人信息</h3>
      <Form ref="saveForm" :model="saveForm" :label-width="100" label-position="right">
        <FormItem label="名称">
          <Input v-model="saveForm.username" ></Input>
        </FormItem>
        <FormItem label="类型">
          <Input v-model="saveForm.kind" ></Input>
        </FormItem>
        <FormItem label="电话" prop="newPass">
          <Input v-model="saveForm.phone"  ></Input>
        </FormItem>
        <FormItem label="地址" prop="rePass">
          <Input v-model="saveForm.address"  ></Input>
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
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">餐厅编号</label>
          <Input id="useridInput" type="textarea" :rows="2" style="width: 250px;margin-left: 3px;" v-model="userForm.userid" readonly />
        </div>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">餐厅名称</label>
          <Input id="usernameInput" type="textarea" :rows="2"  style="width: 250px;margin-left: 3px;" v-model="userForm.username" readonly />
        </div>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">餐厅类型</label>
          <Input id="sumInput" type="textarea" :rows="2"  style="width: 250px;margin-left: 3px;" v-model="userForm.kind" readonly />
        </div>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">电　　话</label>
          <Input id="identityInput" type="textarea" :rows="2"  style="width: 250px;margin-left: 3px;" v-model="userForm.phone" readonly />
        </div>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">地　　址</label>
          <Input id="briefcaseInput" type="textarea" :rows="2"  style="width: 250px;margin-left: 3px;" v-model="userForm.address" readonly />
        </div>
        <div style="margin-left: -500px;margin-top: 2%">
          <label style="width: 10px;margin-left:2px;height: 2%;font-size: 20px">收　　入</label>
          <Input id="schollInput" type="textarea" :rows="2"  style="width: 250px;margin-left: 3px;" v-model="userForm.money" readonly />
        </div>
        <ButtonGroup style="margin-top: 10%">
          <Button type="info" style="width: 55px;margin-left: -50px" @click="editpersonal">编辑</Button>
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
          phone:'',
          money:'',
          address:'',
          kind:'',
        },
        saveForm:{
          username:'',
          phone:'',
          address:'',
          kind:'',
        },
      }
    },
    methods : {
      editpersonal(){
        this.editpersonalflag=true
      },
      savepersonal(){
        var self=this
        this.$http.get('api/dinningcontroller/devisedin/'+this.userForm.userid+"/"+this.saveForm.username+"/"+this.saveForm.address+"/"+
        this.saveForm.kind+"/"+this.saveForm.phone,{
        }).then(function(response){
          self.$Message.success("保存成功,信息待审核请等待")
          self.editpersonalflag=false
        })
      },
      canclepersonal(){
        this.editpersonalflag=false
        this.saveForm.kind=this.userForm.kind
        this.saveForm.username=this.userForm.username
        this.saveForm.phone=this.userForm.phone
        this.saveForm.address=this.userForm.address
      },
      getback(){
        this.$router.push({name:"dinning"})
      },
    },
    created(){
      this.userForm.userid=window.localStorage.getItem("userID")
      this.userForm.username=window.localStorage.getItem("username")
      this.userForm.kind=window.localStorage.getItem("kind")
      this.userForm.money=window.localStorage.getItem("money")
      this.userForm.address=window.localStorage.getItem("address")
      this.userForm.phone=window.localStorage.getItem("phone")
      this.saveForm.kind=this.userForm.kind
      this.saveForm.username=this.userForm.username
      this.saveForm.phone=this.userForm.phone
      this.saveForm.address=this.userForm.address
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
