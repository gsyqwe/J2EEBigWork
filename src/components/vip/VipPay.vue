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
      <Header>
        <Menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo"></div>
          <label style="font-family:'Helvetica Neue' ;font-size:20px; color: white" >
            <Icon type="md-person" size="30"></Icon>
            欢迎您 {{level}}级会员   {{username}}
          </label>
        </Menu>
      </Header>
      <Layout>

        <Sider hide-trigger :style="{background: '#fff'}">
          <Menu  active-name="1-1" theme="dark" width="auto" :open-names="['1']">
            <MenuItem name="1-1" @click.native="showunpay">
              <span>未支付</span>
            </MenuItem>
            <MenuItem name="1-2" @click.native="showallpay">
              <span>已支付</span>
            </MenuItem>
            <MenuItem name="1-3" @click.native="showconfirm">
              <span>已收货</span>
            </MenuItem>
            <MenuItem name="1-4" @click.native="showunsub">
              <span>已退款</span>
            </MenuItem>
            <MenuItem name="1-5" @click.native="back">
              <span>返回首页</span>
            </MenuItem>
          </Menu>
        </Sider>

        <Layout :style="{padding: '0 24px 24px'}">
          <Content :style="{padding: '24px', minHeight: '100%', background: '#fff'}">
            <!--未支付的订单-->
            <div v-show="unpayflag">
              <Table :data="unpaydata" :columns="ordercolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="adunpay(index)">查看地址</Button>
                  <Button type="primary" size="small" style="margin-right: 5px" @click="checkunpay(index)">查看详情</Button>
                  <Button type="primary" size="small" style="margin-right: 5px" @click="tuiunpay(index)">退款</Button>
                  <Button type="primary" size="small" style="margin-right: 5px" @click="pay(index)">支付</Button>
                </template>
              </Table>
            </div>

            <!--支付的订单-->
            <div v-show="payflag">
              <Table :data="paydata" :columns="ordercolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="adpay(index)">查看地址</Button>
                  <Button type="primary" size="small" style="margin-right: 5px" @click="checkpay(index)">查看详情</Button>
                  <Button type="primary" size="small" style="margin-right: 5px" @click="tuipay(index)">退款</Button>
                  <Button type="primary" size="small" style="margin-right: 5px" @click="confirm(index)">确认收货</Button>
                </template>
              </Table>
            </div>

            <!--已收货的订单-->
            <div v-show="confirmflag">
              <Table :data="confirmdata" :columns="ordercolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="adconfirm(index)">查看地址</Button>
                  <Button type="primary" size="small" style="margin-right: 5px" @click="checkconfirm(index)">查看详情</Button>
                </template>
              </Table>
            </div>

            <!--已退款的订单-->
            <div v-show="unsubflag">
              <Table :data="unsubdata" :columns="ordercolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="adunsub(index)">查看地址</Button>
                  <Button type="primary" size="small" style="margin-right: 5px" @click="checkunsub(index)">查看详情</Button>
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
  import Spin from "iview/src/components/spin/spin";

  export default {
    components: {Spin},
    data() {
      return {
        addressflag:false,//查看地址
        addressdata:[],//地址数据
        addresscolumns:[{
          title: '地址',
          key: 'address'
        },
          {
            title: '电话',
            key: 'phone'
          },
          ],//地址列
        detailflag:false,
        unpayflag:false,
        payflag:false,
        confirmflag:false,
        unsubflag:false,
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
        informationdata:[],//详情的数据
        ordercolumns:[{
          title: '餐厅名称',
          key: 'dname'
        },
          {
            title: '金额',
            key: 'money'
          },
          {
            title:'时间',
            key:'date',
          },
          {
            title: 'Action',
            slot: 'action',
            width: 550,
            align: 'center'
          }],//订单的数据
        unpaydata:[],//未支付的数据
        paydata:[],//已支付的数据
        confirmdata:[],//收货的数据
        unsubdata:[],//已退款的数据
        identity: '',//身份
        username: '',//名称
        userid: '',//会员id
        money:0,//消费
        sum:0,//总额
        level: 0,//等级
      }
    },
    created() {
      var self=this
      this.userid=window.localStorage.getItem("userID")
      this.identity=window.localStorage.getItem("identity")
      this.$http.get('api/vipcontroller/checkviptoid/'+self.userid,{
      }).then(function(response){
        var m =response.data.split(";")
        self.username=m[0]
        self.level=m[1]
        self.money=m[3]
        self.sum=m[4]
      }).catch(function (error) {
        self.$Message.warning("失败")
      })
    },
    methods:{
      back(){
        this.$router.push({
          name:'vip'
        })
      },
      adunsub(index){//已退款的地址
        var self = this
        this.addressflag=true
        this.$http.get('api/vipcontroller/findadbyid/'+this.unsubdata[index].aid,{
        }).then(function (response) {
          self.addressdata = response.data
        })
      },
      adconfirm(index){//确认收货的地址
        var self = this
        this.addressflag=true
        this.$http.get('api/vipcontroller/findadbyid/'+this.confirmdata[index].aid,{
        }).then(function (response) {
          self.addressdata = response.data
        })
      },
      tuipay(index){//已经支付的退款方法
        var self = this
        this.$http.get('api/vipcontroller/cancleorder/'+this.userid+"/"+this.paydata[index].id,{
        }).then(function (response) {
          self.$Message.success("退款成功")
          self.paydata.splice(index,index+1)
        })
      },
      adpay(index){//已经支付订单的地址
        var self = this
        this.addressflag=true
        this.$http.get('api/vipcontroller/findadbyid/'+this.paydata[index].aid,{
        }).then(function (response) {
          self.addressdata = response.data
        })
      },
      adunpay(index){//未支付订单的地址
        var self = this
        this.addressflag=true
        this.$http.get('api/vipcontroller/findadbyid/'+this.unpaydata[index].aid,{
        }).then(function (response) {
          self.addressdata = response.data
        })
      },
      closea(){
        this.addressflag=false
      },
      tuiunpay(index){//未支付订单的退款
        var self = this
        this.$http.get('api/vipcontroller/cancleorder/'+this.userid+"/"+this.unpaydata[index].id,{
        }).then(function (response) {
          self.$Message.success("退款成功")
          self.unpaydata.splice(index,index+1)
        })
      },
      close(){
        this.detailflag=false
      },
      confirm(index){//确认收货
        var self = this.$http.get('api/vipcontroller/confirm/'+this.userid+"/"+this.paydata[index].id,{
        }).then(function (response) {
          self.$Message.success("收货成功")
          self.paydata.splice(index,index+1)
        })
      },
      checkconfirm(index){//查看收货订单的详情
        this.detailflag=true
        var self  = this
        this.$http.get('api/vipcontroller/getdetail/'+this.confirmdata[index].iid,{
        }).then(function (response) {
          self.informationdata=response.data
        })
      },
      checkpay(index){//查看支付订单的详情
        this.detailflag=true
        var self  = this
        this.$http.get('api/vipcontroller/getdetail/'+this.paydata[index].iid,{
        }).then(function (response) {
          self.informationdata=response.data
        })
      },
      checkunsub(index){//查看退款的订单详情
        this.detailflag=true
        var self  = this
        this.$http.get('api/vipcontroller/getdetail/'+this.unsubdata[index].iid,{
        }).then(function (response) {
          self.informationdata=response.data
        })
      },
      //查看未支付的订单详情
      checkunpay(index){
        this.detailflag=true
        var self  = this
        this.$http.get('api/vipcontroller/getdetail/'+this.unpaydata[index].iid,{
        }).then(function (response) {
          self.informationdata=response.data
        })
      },
      //支付
      pay(index){
        var self = this
        this.$http.get("api/vipcontroller/pay/"+this.userid+"/"+this.unpaydata[index].id,{
        }).then(function (response) {
          self.$Message.warning(response.data)
          if (response.data == "余额不足"){

          }else{
            self.unpaydata.splice(index,index+1)
          }
        })
      },
      //展示未支付的订单
      showunpay(){
        var self = this
        this.unpayflag=true
        this.payflag=false
        this.confirmflag=false
        this.unsubflag=false
        this.$http.get('api/vipcontroller/checktostate/'+this.userid+"/"+"未支付",{
        }).then(function (response) {
          self.unpaydata = response.data
        })
      },
      //展示已经支付的订单
      showallpay(){
        var self = this
        this.unpayflag=false
        this.payflag=true
        this.confirmflag=false
        this.unsubflag=false
        this.$http.get('api/vipcontroller/checktostate/'+this.userid+"/"+"已支付",{
        }).then(function (response) {
          self.paydata = response.data
        })
      },
      //展示已经确定收货的订单
      showconfirm(){
        var self = this
        this.unpayflag=false
        this.payflag=false
        this.confirmflag=true
        this.unsubflag=false
        this.$http.get('api/vipcontroller/checktostate/'+this.userid+"/"+"已收货",{
        }).then(function (response) {
          self.confirmdata = response.data
        })
      },
      //展示已经退订的订单
      showunsub(){
        var self = this
        this.unpayflag=false
        this.payflag=false
        this.confirmflag=false
        this.unsubflag=true
        this.$http.get('api/vipcontroller/checktostate/'+this.userid+"/"+"已退款",{
        }).then(function (response) {
          self.unsubdata = response.data
        })
      }
    }
  }
</script>
