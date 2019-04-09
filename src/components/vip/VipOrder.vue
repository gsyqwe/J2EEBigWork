<template>
  <div class="layout">
    <Modal v-model="editflag" :closable='false' :mask-closable=false :width="500">
      <h3 slot="header" style="color:#2D8CF0">选择数量</h3>
      <InputNumber :min="1" :step="1" :max="nummax" v-model="num"></InputNumber>
      <div slot="footer">
        <Button type="text" @click="cancle">取消</Button>
        <Button type="primary"  @click="save">保存</Button>
      </div>
    </Modal>

    <Modal v-model="orderflag" :closable='false' :mask-closable=false :width="500">
      总价为 : {{money}}
      送货时间为 : {{sendtime}}
      <div slot="footer">
        <Button type="text" @click="cancleorder">取消</Button>
        <Button type="primary"  @click="createorder">保存</Button>
      </div>
    </Modal>

    <Layout>
      <Header>
        <Menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo"></div>
          <label style="margin-left:500px;font-family:'Helvetica Neue' ;font-size:20px; color: white" >
            <Icon type="md-person" size="30"></Icon>
            餐厅名称：{{dinname}}  联系电话:{{dinphone}}
          </label>
        </Menu>
      </Header>
      <Layout>

        <Sider hide-trigger :style="{background: '#fff'}">
          <Menu  active-name="1-1" theme="dark" width="auto" :open-names="['1']">
            <Submenu name="2">
              <template slot="title" >
                <Icon type="ios-keypad" ></Icon>
                餐品类型
              </template>

              <MenuItem v-for="(item,index) in kind" :name="index" @click.native="showtokind(index)">
                {{item}}
              </MenuItem>
            </Submenu>

            <MenuItem name="1-2" @click.native="showallcombomenu">
              <span>套餐(只可以点一份)</span>
            </MenuItem>

            <MenuItem name="1-3" @click.native="showalredy">
              <span>查看已点餐品</span>
            </MenuItem>

            <MenuItem name="1-4" @click.native="showalredyco">
              <span>查看已点套餐</span>
            </MenuItem>

            <MenuItem name="1-5" @click.native="makeorder">
              <span>确认下单</span>
            </MenuItem>

            <MenuItem name="1-6" @click.native="back">
              <span>返回首页</span>
            </MenuItem>
          </Menu>
        </Sider>

        <Layout :style="{padding: '0 24px 24px'}">
          <Content :style="{padding: '24px', minHeight: '100%', background: '#fff'}">

            <!--查看想要类型的内容-->
            <div v-show="mealflag">
              <Table :data="mealdata" :columns="mealcolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="order(index)">添加</Button>
                </template>
              </Table>
            </div>

            <!--查看套餐的内容-->
            <div v-show="cobomenuflag">
              <Table :data="cobomealdata" :columns="cobomealcolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="orderco(index)">添加</Button>
                </template>
              </Table>
            </div>

            <!--查看已点餐品的内容-->
            <div v-show="mealalflag">
              <Table :data="mealorderdata" :columns="mealcolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="remove(index)">删除</Button>
                </template>
              </Table>
            </div>

            <!--查看已点套餐的内容-->
            <div v-show="cobomealalflag">
              <Table :data="cobomealorderdata" :columns="cobomealcolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }"  slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="removeco(index)">删除</Button>
                </template>
              </Table>
            </div>

            <!--选择地址-->
            <div v-show="addressflag">
              <Table :data="addressdata" :columns="addresscolumns">
                <template slot-scope="{ row }" slot="name">
                  <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }" slot="action">
                  <Button type="primary" size="small" style="margin-right: 5px" @click="select(index)">选择地址</Button>
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
        return{
          addressindex:0,//地址的Index
          addressflag:false,//选择地址的flag
          sendtime:0,//送货时间
          userid:'',//点单用户的id
          orderflag:false,//下单的flag
          money:0,//总价
          num:0,//选择数量
          nummax:0,//当前的库存数量
          editflag:false,
          index:0,//表示当前点餐时选了第几个
          mealflag:false,//查看相应类型
          cobomenuflag:false,//查看相应套餐
          mealalflag:false,//查看已点的餐品
          cobomealalflag:false,//查看已点的套餐
          dinid:'',//餐厅id
          dinphone:'',//餐厅电话
          dinname:'',//餐厅名称
          mealid:[],//菜品id
          mealnum:[],//菜品数量
          kind:[],//各个类型
          combomenuid:[],//套餐id
          mealorderdata:[],//菜品点单的数据
          cobomealorderdata:[],//套餐点单的数据
          mealdata:[],//菜品数据
          addresscolumns:[ {
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
            }],//地址的列
          addressdata:[],//地址的数据
          mealcolumns:[{
            title: '参考图',
            align: 'center',
            render: (h,params) => {
              return h('img', {
                attrs: {
                  src: params.row.url,style: 'width: 100px;height: 100px;border-radius: 2px;'
                },
                style: {
                  marginRight: '5px'
                },
              });
            }
          },
            {
              title:'名称',
              key:'name',
            },
            {
              title:'库存',
              key:'num',
            },
            {
              title:'单价',
              key:'price'
            },
            {
              title: '操作',
              slot: 'action',
              width: 150,
              align: 'center'
            }],//菜品的列
          //套餐的数据
          cobomealdata:[],
          //套餐的列
          cobomealcolumns:[
            {
              title: '参考图',
              align: 'center',
              render: (h,params) => {
                return h('img', {
                  attrs: {
                    src: params.row.courl,style: 'width: 100px;height: 100px;border-radius: 2px;'
                  },
                  style: {
                    marginRight: '5px'
                  },
                });
              }
            },
            {
              title:'名称',
              key:'mname',
            },
            {
              title:'数量',
              key:'mnum',
            },
            {
              title:'套餐单价',
              key:'price'
            },
            {
              title: '操作',
              slot: 'action',
              width: 150,
              align: 'center'
            }
          ],
        }
      },
      methods:{
        back(){
          this.$router.push({
            name:'vip'
          })
        },
        //选择地址
        select(index){
          this.addressindex = index
          this.sendtime= 45
          //计算时间
          this.orderflag=true
        },
        cancleorder(){
          this.orderflag=false
        },
        //下单
        createorder(){
          var self = this
          var foodname = ""
          //先做foodname
          if(this.mealorderdata.length == 0){
          }else {
            foodname = this.mealorderdata[0].name
            if(this.mealorderdata.length>1) {
              for (var i = 1; i < this.mealorderdata.length; i++) {
                foodname = foodname + ";" + this.mealorderdata[i].name
              }
            }
          }
          if(this.cobomealorderdata.length == 0){
            //没点套餐
          }else{
            if(this.mealorderdata.length>=1){
              //有点单品,但有套餐
              for (var i = 0 ; i < this.cobomealorderdata.length ; i++){
                foodname = foodname + ";" + this.cobomealorderdata[i].name
              }
            }else{
              //没点单品,但有套餐
              foodname = this.cobomealorderdata[0].name
              if(this.cobomealorderdata.length>1){
                for(var i = 1;i<this.cobomealorderdata.length;i++){
                  foodname = foodname + ";" +this.cobomealorderdata[i].name
                }
              }
            }
          }
          //在做foodprice,单价*数量
          var foodprice = ""
          if(this.mealorderdata.length == 0){
          }else {
            foodprice = this.mealorderdata[0].price*this.mealorderdata[0].num
            if(this.mealorderdata.length>1) {
              for (var i = 1; i < this.mealorderdata.length; i++) {
                foodprice = foodprice + ";" + this.mealorderdata[i].price*this.mealorderdata[i].num
              }
            }
          }
          if(this.cobomealorderdata.length == 0){
            //没点套餐
          }else{
            if(this.mealorderdata.length>=1){
              //有点单品,但有套餐
              for (var i = 0 ; i < this.cobomealorderdata.length ; i++){
                foodprice = foodprice + ";" + this.cobomealorderdata[i].price
              }
            }else{
              //没点单品,但有套餐
              foodprice = this.cobomealorderdata[0].price
              if(this.cobomealorderdata.length>1){
                for(var i = 1;i<this.cobomealorderdata.length;i++){
                  foodprice = foodprice + ";" +this.cobomealorderdata[i].price
                }
              }
            }
          }
          //在做foodid
          var foodid = ""
          if(this.mealorderdata.length == 0){
          }else {
            foodid = this.mealorderdata[0].id
            if(this.mealorderdata.length>1) {
              for (var i = 1; i < this.mealorderdata.length; i++) {
                foodid = foodid + ";" + this.mealorderdata[i].id
              }
            }
          }
          if(this.cobomealorderdata.length == 0){
            //没点套餐
          }else{
            if(this.mealorderdata.length>=1){
              //有点单品,但有套餐
              for (var i = 0 ; i < this.cobomealorderdata.length ; i++){
                foodid = foodid + ";" + this.cobomealorderdata[i].id
              }
            }else{
              //没点单品,但有套餐
              foodid = this.cobomealorderdata[0].id
              if(this.cobomealorderdata.length>1){
                for(var i = 1;i<this.cobomealorderdata.length;i++){
                  foodid = foodid + ";" +this.cobomealorderdata[i].id
                }
              }
            }
          }
          //在做foodnum
          var foodnum = ""
          if(this.mealorderdata.length == 0){
          }else {
            foodnum = this.mealorderdata[0].num
            if(this.mealorderdata.length>1) {
              for (var i = 1; i < this.mealorderdata.length; i++) {
                foodnum = foodnum + ";" + this.mealorderdata[i].num
              }
            }
          }
          if(this.cobomealorderdata.length == 0){
            //没点套餐
          }else{
            if(this.mealorderdata.length>=1){
              //有点单品,但有套餐
              for (var i = 0 ; i < this.cobomealorderdata.length ; i++){
                foodnum = foodnum + ";" + "1"
              }
            }else{
              //没点单品,但有套餐
              foodnum = "1"
              if(this.cobomealorderdata.length>1){
                for(var i = 1;i<this.cobomealorderdata.length;i++){
                  foodnum = foodnum + ";" +"1"
                }
              }
            }
          }
          var self = this
          //计算时间
          this.$http.get('api/vipcontroller/order/'+this.addressdata[this.addressindex].id+"/"+this.userid+"/"+this.dinid+"/"+
            this.dinname+"/"+this.money+"/"+encodeURIComponent(foodid)+
            "/"+encodeURIComponent(foodname)+"/"+encodeURIComponent(foodnum) +"/"+encodeURIComponent(foodprice),{
          }).then(function (response) {
            self.orderflag=false
            self.$router.push({
              name:'vippay'
            })
          })
        },
        //下单
        makeorder(){
          var self = this
          self.mealflag=false
          self.cobomenuflag=false
          self.mealalflag=false
          self.cobomealalflag=false
          this.addressflag=true
          this.money = 0
          //计算总价
          for (var i = 0 ; i < this.mealorderdata.length; i++){
            this.money = this.money + this.mealorderdata[i].num * this.mealorderdata[i].price
          }
          for (var i = 0 ; i < this.cobomealorderdata.length; i++){
            this.money = this.money + this.cobomealorderdata[i].price
          }
          //拿去地址
          this.$http.get('api/vipcontroller/getuseraddress/'+this.userid,{
          }).then(function (response) {
            self.addressdata=response.data
          })
        },
        //删除点的餐品
        remove(index){
          this.mealorderdata.splice(index,index+1)
        },
        //删除套餐
        removeco(index){
          this.cobomealdata.push(this.cobomealorderdata[index])
          this.cobomealorderdata.splice(index,index+1)
        },
        //根据类型查看相应类型的菜品
        showtokind(index){
          var self=this
          this.$http.get('api/dinningcontroller/getdetail/'+this.mealid[index],{
          }).then(function (response) {
            self.mealdata=response.data
            self.mealflag=true
            self.cobomenuflag=false
            self.mealalflag=false
            self.cobomealalflag=false
          })
        },
        //查看所有的套餐
        showallcombomenu(){
          var self=this
          self.mealflag=false
          self.cobomenuflag=true
          self.mealalflag=false
          self.cobomealalflag=false
          this.addressflag=false
        },
        //套餐只可以点一份
        orderco(index){
          this.cobomealorderdata.push(this.cobomealdata[index])
          this.cobomealdata.splice(index,index+1)
        },
        order(index){
          this.index=index
          this.nummax=this.mealdata[index].num
          this.editflag=true
        },
        cancle(){
          this.editflag=false
        },
        //确认数量保存
        save(){
          this.mealorderdata.push(this.mealdata[this.index])
          var n = this.mealdata[this.index].num
          this.mealorderdata[this.mealorderdata.length-1].num = this.num
          this.num = 1
          this.editflag=false
        },
        //查看已点的餐品
        showalredy(){
          var self=this
          self.mealflag=false
          self.cobomenuflag=false
          self.mealalflag=true
          self.cobomealalflag=false
          this.addressflag=false
        },
        //查看已点的套餐
        showalredyco(){
          var self=this
          self.mealflag=false
          self.cobomenuflag=false
          self.mealalflag=false
          self.cobomealalflag=true
          this.addressflag=false
        }
      },
      created(){
        var self=this
        this.dinid=window.localStorage.getItem("dinId")
        this.dinname=window.localStorage.getItem("dinName")
        this.dinphone=window.localStorage.getItem("dinPhone")
        this.userid=window.localStorage.getItem("userID")
        this.$http.get('api/dinningcontroller/getmenu/'+this.dinid,{
        }).then(function(response){
          var m = response.data.split(".")
          self.mealid = m[1].split(";")
          self.mealnum = m[2].split(";")
          self.kind = m[3].split(";")
          self.combomenuid = m[4].split(";")
          var m = self
          var result="";
          result = self.combomenuid[0]
          for (var i = 1 ;i<self.combomenuid.length;i++){
            result = result + "," + self.combomenuid[i]
          }
          self.$http.get('api/dinningcontroller/getcobomenu/'+ result,{
          }).then(function (response) {
            m.cobomealdata=response.data
          })
        })
      },
    }
</script>

