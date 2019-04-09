<template>
  <div class="layout">
    <Modal v-model="addeditpersonalflag" :closable='false' :mask-closable=false :width="500">
      <h3 slot="header" style="color:#2D8CF0">管理地址</h3>
      <Form ref="saveForm" :model="saveForm" :label-width="100" label-position="right">
        <FormItem label="地址">
          <Input v-model="saveForm.address" ></Input>
        </FormItem>
        <FormItem label="电话" prop="newPass">
          <Input v-model="saveForm.phone"  ></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="text" @click="cancleaddpersonal">取消</Button>
        <Button type="primary"  @click="addsavepersonal">保存</Button>
      </div>
    </Modal>

    <Modal v-model="editpersonalflag" :closable='false' :mask-closable=false :width="500">
      <h3 slot="header" style="color:#2D8CF0">管理地址</h3>
      <Form ref="saveForm" :model="saveForm" :label-width="100" label-position="right">
        <FormItem label="地址">
          <Input v-model="saveForm.address" ></Input>
        </FormItem>
        <FormItem label="电话" prop="newPass">
          <Input v-model="saveForm.phone"  ></Input>
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
      <!-- 用来保存地址和电话 -->
        <div>
          <Table border :columns="columns12" :data="data6">
            <template slot-scope="{ row }" slot="name">
              <strong>{{ row.name }}</strong>
            </template>
            <template slot-scope="{ row, index }" slot="action">
              <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">修改</Button>
            </template>
          </Table>
        </div>

        <ButtonGroup style="margin-top: 10%">
          <Button type="info" style="width: 100px;margin-left: -50px" @click="addaddress">添加地址</Button>
          <Button type="info" style="width: 55px;margin-left: 50px" @click="getback">返回</Button>
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
        addeditpersonalflag:false,
        editpersonalflag:false,
        addressindex:0,
        addressid:0,
        columns12: [
          {
            title: '地址',
            key: 'address'
          },
          {
            title: '电话',
            key: 'phone'
          },
          {
            title: 'Action',
            slot: 'action',
            width: 150,
            align: 'center'
          }
        ],
        data6:[],
        userForm:{
          userid:'',
          address:"",
          phone:"",
        },
        saveForm:{
          address:"",
          phone:"",
        },
      }
    },
    methods : {
      //添加方法
      addaddress(){
        this.addeditpersonalflag=true
        this.saveForm.address=""
        this.saveForm.phone=""
      },
      cancleaddpersonal(){
        this.addeditpersonalflag=false
        this.saveForm.address=""
        this.saveForm.phone=""
      },
      addsavepersonal(){
        var self=this
        if (this.userForm.address!="") {
          var n = this.userForm.address + ";" + this.saveForm.address
          var t = this.userForm.phone + ";" + this.saveForm.phone
          this.userForm.address = n
          this.userForm.phone = t
        }else{
          var n = this.saveForm.address
          var t = this.saveForm.phone
          this.userForm.address = n
          this.userForm.phone = t
        }
        this.$http.get('api/vipcontroller/addphoneaddress/'+this.userForm.userid+"/"+ this.userForm.phone + "/" +
          this.userForm.address,{
        }).then(function(response){
          self.$Message.success("添加成功")
          var m = self
          self.$http.get('api/vipcontroller/getuseraddress/'+self.userForm.userid,{
          }).then(function (response) {
            m.data6=response.data
          })
        })
        this.addeditpersonalflag=false
        this.saveForm.address=""
        this.saveForm.phone=""
      },

      //修改方法
      show(index){
        this.editpersonalflag=true
        this.addressindex=index
        this.addressid=this.data6[index].id
        this.saveForm.address=this.data6[this.addressindex].address
        this.saveForm.phone=this.data6[this.addressindex].phone
      },
      savepersonal(){
        var self=this
        this.$http.get('api/vipcontroller/devisephoneaddress/'+this.addressid+"/"+this.saveForm.phone+"/"+
        this.saveForm.address,{
        }).then(function (response) {
          self.$Message.success("修改成功")
        })
        this.$http.get('api/vipcontroller/getuseraddress/'+this.userForm.userid,{
        }).then(function (response) {
          self.data6=response.data
          var m = self
          self.$http.get('api/vipcontroller/getuseraddress/'+self.userForm.userid,{
          }).then(function (response) {
            m.data6=response.data
          })
        })
        this.editpersonalflag=false
        this.saveForm.address=""
        this.saveForm.phone=""
      },
      canclepersonal(){
        this.editpersonalflag=false
        this.saveForm.address=""
        this.saveForm.phone=""
      },
      getback(){
        this.$router.push({name:"vip"})
      }
    },
    created(){
      this.userForm.userid=window.localStorage.getItem("userID")
      var self=this
      this.$http.get('api/vipcontroller/getuseraddress/'+this.userForm.userid,{
      }).then(function (response) {
        console.log(response.data)
        self.data6=response.data
      })
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
