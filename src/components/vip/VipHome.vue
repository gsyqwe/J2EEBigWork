<template>

  <div class="layout">
    <Modal v-model="addressflag">
      <Table :data="addressdata" :columns="addresscolumns">
      </Table>
      <div slot="footer">
        <Button type="text" @click="closea">关闭</Button>
      </div>
    </Modal>

    <Modal v-model="detailflag">
      <Table :data="informationdata" :columns="informationcolumns">
      </Table>
      <div slot="footer">
        <Button type="text" @click="close">关闭</Button>
      </div>
    </Modal>

    <Layout>
      <Header >
        <Menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo"></div>
          <div class="layout-nav">
            <MenuItem name="1" @click.native="manageraddress">
              <Icon type="md-add-circle"></Icon>
              管理地址
            </MenuItem>
            <MenuItem name="1" @click.native="checkpersonal">
              <Icon type="ios-navigate"></Icon>
              个人信息
            </MenuItem>
          </div>
          <label style="font-family:'Helvetica Neue' ;font-size:20px; color: white;" >
            <Icon type="md-person" size="30"></Icon>
            欢迎您 {{level}}级会员   {{username}}
          </label>
        </Menu>
      </Header>
      <Layout>

        <Sider hide-trigger :style="{background: '#fff'}">
          <Menu  active-name="1-1" theme="dark" width="auto" :open-names="['1']">
            <MenuItem name="1-1" @click.native="showalldin">
              <span>全部餐厅</span>
            </MenuItem>
            <MenuItem name="1-2" @click.native="showallorder">
              <span>查看统计信息</span>
            </MenuItem>
          </Menu>
        </Sider>

        <Layout :style="{padding: '0 24px 24px'}">
          <Content :style="{padding: '24px', minHeight: '100%', background: '#fff'}">

            <!--查看餐厅的内容-->
            <div v-show="dinflag">
              <Table :data="dinningdata" :columns="dinningcolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="order(index)">点餐</Button>
                </template>
              </Table>
            </div>

            <!--查看订单的内容-->
            <div v-show="orderflag">
              <Table :data="orderdata" :columns="ordercolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }" slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="checkad(index)">查看地址</Button>
                  <Button type="primary" size="small" style="margin-right: 5px" @click="checkdetail(index)">查看详情</Button>
                </template>
              </Table>
            </div>

          </Content>
        </Layout>
      </Layout>
    </Layout>
  </div>
</template>
<script>
  export default {
    data(){
      return {
        detailflag:false,
        informationdata:[],//详情的数据
        informationcolumns:[{
          title: '菜品',
          key: 'foodname'
        },
          {
            title: '数量',
            key: 'foodnum'
          },
          {
            title:'金额',
            key:'price',
          },],//详情的列
        addressflag:false,
        addressdata:[],//地址数据
        addresscolumns:[{
          title: '地址',
          key: 'address'
        },
          {
            title: '电话',
            key: 'phone'
          },],//地址列
        identity:'',
        username:'',
        userid:'',
        level:0,
        email:'',
        money:0,//消费
        sum:0,//余额
        //餐厅信息
        dinflag:false,
        dinningcolumns:[
          {
            title:'名称',
            key:'name',
          },
          {
            title:'类型',
            key:'kind'
          },
          {
            title: '操作',
            slot: 'action',
            width: 150,
            align: 'center'
          }],
        dinningdata:[],
        //订单信息
        ordercolumns:[{
          title: '餐厅名称',
          key: 'dname',
          "sortable":true
        },
          {
            title: '金额',
            key: 'money',
            "sortable":true
          },
          {
            title:'时间',
            key:'date',
            "sortable":true
          },{
            title: 'Action',
            slot: 'action',
            width: 550,
            align: 'center'
          }],
        orderdata:[],
        orderflag:false,
      }
    },
    created(){
      var self = this
      this.userid=window.localStorage.getItem("userID")
      this.identity=window.localStorage.getItem("identity")
      this.$http.get('api/vipcontroller/checkviptoid/'+self.userid,{
      }).then(function(response){
        var m =response.data.split(";")
        self.username=m[0]
        self.level=m[1]
        self.email=m[2]
        self.money=m[3]
        self.sum=m[4]
      }).catch(function (error) {
        self.$Message.warning("失败")
      })
    },
    methods:{
      close(){
        this.detailflag=false
      },
      closea(){
        this.addressflag=false
      },
      checkdetail(index){//查看详情
        this.detailflag=true
        var self  = this
        this.$http.get('api/vipcontroller/getdetail/'+this.orderdata[index].iid,{
        }).then(function (response) {
          self.informationdata=response.data
        })
      },
      checkad(index){//查看地址
        var self = this
        this.$http.get('api/vipcontroller/findadbyid/'+this.orderdata[index].aid,{
        }).then(function (response) {
          self.addressdata = response.data
          self.addressflag=true
        })
      },
      //查看所有订单
      showallorder(){
        var self=this
        this.orderflag=true;
        this.dinflag=false
        this.$http.get('api/managercontroller/checkvipor/'+this.userid,{
        }).then(function (response) {
          self.orderdata = response.data
        })
      },
      //查看所有餐厅
      showalldin(){
        var self=this
        this.orderflag=false
        this.dinflag=true
        this.$http.get('api/dinningcontroller/getusedin',{
        }).then(function(response){
          self.dinningdata=response.data
        })
      },
      //点餐跳转的方法
      order(index){
        window.localStorage.setItem("dinId",this.dinningdata[index].id)
        window.localStorage.setItem("dinName",this.dinningdata[index].name)
        window.localStorage.setItem("dinPhone",this.dinningdata[index].phone)
        window.localStorage.setItem("userID",this.userid)
        this.$router.push({
          name:'viporder'
        })
      },
      checkpersonal(){
        window.localStorage.setItem("userID",this.userid)
        window.localStorage.setItem("identity",this.identity)
        window.localStorage.setItem("username",this.username)
        window.localStorage.setItem("level",""+this.level)
        window.localStorage.setItem("email",this.email)
        window.localStorage.setItem("money",""+this.money)
        window.localStorage.setItem("sum",""+this.sum)
        this.$router.push({
          name:'vipdetail',
        })
      },
      manageraddress(){
        window.localStorage.setItem("userID",this.userid)
        this.$router.push({
          name:'vipaddress',
        })
      },
    }
  }
</script>
<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    overflow: hidden;
  }
  .layout-nav{
    width: 420px;
  }
</style>
