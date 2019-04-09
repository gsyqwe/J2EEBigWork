<template>
  <div class="layout">

    <Modal v-model="recordflag">
      <Table :data="recorddata" :columns="recordcolumns">
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
             欢迎您Yummy经理
          </label>
        </Menu>
      </Header>
      <Layout>

        <Sider hide-trigger :style="{background: '#fff'}">
          <Menu theme="dark" width="auto" :open-names="['1']">
            <MenuItem name="1-1" @click.native="checkallvip">
              <span>全部会员</span>
            </MenuItem>
            <MenuItem name="1-2" @click.native="checkalldin">
              <span>全部餐厅</span>
            </MenuItem>
            <MenuItem name="1-3" @click.native="checkallmessage">
              <span>查看待审核信息</span>
            </MenuItem>
            <MenuItem name="1-4" @click.native="checkyummy">
              <span>Yummy财务情况</span>
            </MenuItem>
            <MenuItem name="1-5" @click.native="unbalance">
              <span>未结算订单</span>
            </MenuItem>
            <MenuItem name="1-6" @click.native="conductbalance">
              <span>进行结算</span>
            </MenuItem>
          </Menu>
        </Sider>

        <Layout :style="{padding: '0 24px 24px'}">
          <Content :style="{padding: '24px', minHeight: '100%', background: '#fff'}">
            <!--全部会员的表格-->
            <div v-show="vipflag">
              <Table :data="vipdata" :columns="vipcolumns" style="margin-top: 30px" stripe>
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="checkvip(index)" >查看统计信息</Button>
                </template>
              </Table>
            </div>

            <!--全部餐厅的表格-->
            <div v-show="dinflag">
              <Table :data="dindata" :columns="dincolumns" style="margin-top: 30px" stripe>
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="checkdin(index)" >查看统计信息</Button>
                </template>
              </Table>
            </div>

            <!--Yummy财务情况-->
            <div v-show="yummy">
              <Table :data="yummydata" :columns="yummycolumns">

              </Table>
            </div>

            <!--未结算的订单-->
            <div v-show="unbalanceflag">
              <Table :data="unbalancedata" :columns="unbalancecolumns">

              </Table>
            </div>

            <!--餐厅待审核信息-->
            <div v-show="messageflag">
              <Table :data="messagedata" :columns="messagecolumns" style="margin-top: 30px" stripe>
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="devisedin(index)" >通过</Button>
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
        recordflag:false,//订单的flag
        unbalanceflag:false,//未结算的订单flag
        messageflag:false,//审核条件
        vipflag:false,//会员
        dinflag:false,//餐厅
        yummy:false,//yummy财务情况
        unbalancedata:[],//没有结算订单的数据
        yummydata:[],//yummy数据
        yummycolumns:[{
          title:'时间',
          key:'date'
        },{
          title:'收入',
          key:'money'
        }],//yummy的列
        unbalancecolumns:[{
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
            title:'状态',
            key:'state'
          }],//没有结算订单的列
        dindata:[],//餐厅数据
        dincolumns:[{
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
          }],//餐厅列
        recorddata:[],//订单数据
        recordcolumns:[{
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
            title:'状态',
            key:'state'
          }
        ],//订单列
        vipdata:[],//会员的数据
        vipcolumns:[{
          title: '会员名称',
          key: 'name'
        },
          {
            title: '金额',
            key: 'money'
          },
          {
            title:'余额',
            key:'sum',
          },
          {
            title:'邮件',
            key:'email'
          },
          {
            title: 'Action',
            slot: 'action',
            width: 550,
            align: 'center'
          }],//会员的列
        messagecolumns:[{
          title:'餐厅编号',
          key:'did'
        },
          {
            title:'餐厅名称',
            key:'name',
          },
          {
            title:'餐厅类型',
            key:'kind',
          },
          {
            title:'餐厅地址',
            key:'address'
          },
          {
            title:'餐厅电话',
            key:'phone'
          },
          {
            title: '操作',
            slot: 'action',
            width: 150,
            align: 'center'
          }],//待审核信息的column
        messagedata:[],//待审核信息的数据
      }
    },
    methods:{
      //进行结算
      conductbalance(){
        this.messageflag=false
        this.vipflag=false
        this.dinflag=false
        this.yummy=false
        this.unbalanceflag=false
        var self = this
        this.$http.get('api/managercontroller/balance',{
        }).then(function () {
          self.$Message.success("结算成功")
        })
      },
      close(){
        this.recordflag=false
      },
      checkvip(index){//查看会员统计信息
        var self = this
        this.$http.get('api/managercontroller/checkvipor/'+this.vipdata[index].userid,{
        }).then(function (response) {
          self.recorddata=response.data
          self.recordflag=true
        })
      },
      checkdin(index){//查看餐厅统计信息
        var self = this
        this.$http.get('api/managercontroller/checkdinor/'+this.dindata[index].id,{
        }).then(function (response) {
          self.recorddata=response.data
          self.recordflag=true
        })
      },
      //审核通过待审核信息
      devisedin(index){
        var self=this
        this.$http.get('api/managercontroller/pass/'+this.messagedata[index].id+"/"+this.messagedata[index].did,{
        }).then(function (response) {
          self.messagedata.splice(index,index+1)
          self.$Message.success("成功修改")
        })
      },
      //查看所有message
      checkallmessage(){
        this.messageflag=true
        this.vipflag=false
        this.dinflag=false
        this.yummy=false
        this.unbalanceflag=false
        var self=this
        this.$http.get('api/managercontroller/getcheckdin',{
        }).then(function (response) {
          self.messagedata=response.data
        })
      },
      checkallvip(){//查看所有会员
        var self = this
        this.vipflag=true
        this.dinflag=false
        this.yummy=false
        this.messageflag=false
        this.unbalanceflag=false
        this.$http.get('api/vipcontroller/getallcustomer',{
        }).then(function (response) {
          self.vipdata=response.data
        })
      },
      checkalldin(){//查看所有餐厅
        var self = this
        this.dinflag=true
        this.vipflag=false
        this.yummy=false
        this.messageflag=false
        this.unbalanceflag=false
        this.$http.get('api/managercontroller/getalldin',{
        }).then(function (response) {
          self.dindata=response.data
        })
      },
      checkyummy(){//查看yummy财务情况
        this.yummy=true
        this.vipflag=false
        this.dinflag=false
        this.messageflag=false
        this.unbalanceflag=false
        var self = this
        this.$http.get('api/managercontroller/checkyummy',{
        }).then(function (response) {
          self.yummydata=response.data
        })
      },
      unbalance(){//查看未结算订单
        var self = this
        this.yummy=false
        this.vipflag=false
        this.dinflag=false
        this.messageflag=false
        this.unbalanceflag=true
        this.$http.get('api/managercontroller/getallunbalance',{
        }).then(function (response) {
          self.unbalancedata = response.data
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
    margin: 0 auto;
    margin-right: 20px;
  }
</style>
