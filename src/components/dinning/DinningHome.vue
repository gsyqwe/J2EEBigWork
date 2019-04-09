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

    <Modal v-model="kindaddflag" :closable='false' :mask-closable=false :width="500">
    <Form ref="saveForm"  :label-width="100" label-position="right">
      <FormItem label="类型名称" prop="rePass">
        <Input v-model="kindtoadd"  ></Input>
      </FormItem>
    </Form>
    <div slot="footer">
      <Button type="text" @click="cancle">取消</Button>
      <Button type="primary"  @click="saveadd">保存</Button>
    </div>
  </Modal>

    <!--添加单品的modal-->
    <Modal v-model="mealkindaddflag" :closable='false' :mask-closable=false :width="800">
      <Table :data="mealtabledata" :columns="mealcolumnskind" style="margin-top: 30px" stripe>
        <template slot-scope="{ row }" slot="name">
          <strong>{{ row.name }}</strong>
        </template>
        <template slot-scope="{ row, index }"  slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="addmealkind(index)" >添加</Button>
        </template>
      </Table>
      <div style="float: right;">
        <Page :total="allmealdata.length" :current="1" :page-size="7" @on-change="changePage"></Page>
      </div>
      <div slot="footer">
        <Button type="primary"  @click="confirmadd">确认</Button>
      </div>
    </Modal>

    <!--添加单品到套餐的modal-->
    <Modal v-model="cobomealflag" :closable='false' :mask-closable=false :width="800">
      <Table :data="mealtabledata" :columns="mealcolumnskind" style="margin-top: 30px" stripe>
        <template slot-scope="{ row }" slot="name">
          <strong>{{ row.name }}</strong>
        </template>
        <template slot-scope="{ row, index }"  slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="addmealtoco(index)" >添加</Button>
        </template>
      </Table>
    </Modal>

    <!--套餐中单品的数量-->
    <Modal v-model="nummealflag">
    <Form ref="saveForm"  :label-width="100" label-position="right">
      <FormItem label="数量" prop="rePass">
        <Input v-model="onenum"></Input>
      </FormItem>
    </Form>
    <div slot="footer">
      <Button type="primary"  @click="numsetco">保存</Button>
    </div>
    </Modal>
    <!--添加套餐的modal-->
    <Modal v-model="comealaddflag" :closable='false' :mask-closable=false :width="800">
      <Table :data="allcombodata" :columns="cobomealcolumnsadd" style="margin-top: 30px" stripe>
        <template slot-scope="{ row }" slot="name">
          <strong>{{ row.name }}</strong>
        </template>
        <template slot-scope="{ row, index }"  slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="addcobo(index)" >添加</Button>
        </template>
      </Table>
      <div slot="footer">
        <Button type="primary"  @click="confirmaddco">确认</Button>
      </div>
    </Modal>

    <!--设定菜品数量的-->
    <Modal v-model="numsetflag" :closable='false' :mask-closable=false :width="500">
      <Form ref="saveForm"  :label-width="100" label-position="right">
        <FormItem label="数量" prop="rePass">
          <Input v-model="num"></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="primary"  @click="numset">保存</Button>
      </div>
    </Modal>

    <Layout>
      <Header>
        <Menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-logo"></div>
          <div class="layout-nav">
            <MenuItem name="1" @click.native="checkpersonal">
              <Icon type="ios-navigate"></Icon>
              餐厅信息
            </MenuItem>
          </div>
          <label style="font-family:'Helvetica Neue' ;font-size:20px; color: white" >
            <Icon type="md-person" size="30"></Icon>
            {{username}}
          </label>
        </Menu>
      </Header>
      <Layout>

        <Sider hide-trigger :style="{background: '#fff'}">
          <Menu  active-name="1-2" theme="dark" width="auto" :open-names="['1']">
            <MenuItem name="1-1" @click.native="checkallmeal">
              <span>全部菜品</span>
            </MenuItem>
            <MenuItem name="1-3" @click.native="changtomenu">
              <span>发布菜单</span>
            </MenuItem>
            <MenuItem name="1-4" @click.native="changtocreatenewmeal">
              <span>添加新品</span>
            </MenuItem>
            <MenuItem name="1-5" @click.native="changtocreatenewcobomeal">
              <span>添加套餐</span>
            </MenuItem>
            <MenuItem name="1-6" @click.native="changtoTongji">
              <span>查看统计信息</span>
            </MenuItem>
          </Menu>
        </Sider>

        <Layout :style="{padding: '0 24px 24px'}">
          <Content :style="{padding: '24px', minHeight: '100%', background: '#fff'}">
            <!--查看全部菜品-->
            <div v-show="allmealflag" id="allmeal">
              <Table :data="mealtabledata" :columns="mealcolumns" stripe></Table>
              <div style="margin: 10px;overflow: hidden">
                <div style="float: right;">
                  <Page :total="allmealdata.length" :current="1" :page-size="7" @on-change="changePage"></Page>
                </div>
              </div>
            </div>

            <!--改变去发布菜单-->
            <div v-show="changemealflag" style="height: 850px">
              <div style="width: 200px;">
                <Sider :style="{position: 'absolute',marginLeft:'250px', left: 0, overflow: 'auto'}">
                  <Menu active-name="1-2" theme="dark" width="auto" :open-names="['1']">
              <Submenu name="2">
                <template slot="title" >
                  <Icon type="ios-keypad" ></Icon>
                  餐品类型
                </template>

                <MenuItem v-for="(item,index) in mealkind" :name="index" @click.native="addtokind(index)">
                  {{item}}
                </MenuItem>

                <MenuItem  @click.native="addkind">
                  添加类型
                </MenuItem>

                <!--添加套餐啊按钮等-->
              </Submenu>

                    <MenuItem @click.native="addtocombomenu" :name="3">
                      套餐
                    </MenuItem>

                    <MenuItem @click.native="publish" name="2">
                      确认发布
                    </MenuItem>
                </Menu>
              </Sider>
              </div>
              <!--用来显示当前类型的餐品-->
              <div style="height: 800px" v-show="currentkindmealflag">
                <div>
                  <Row >
                    <Col span="8" style="margin-left: 300px">
                    <DatePicker format="yyyy-MM-dd" type="date" @on-change="start"  placeholder="开始日期"></DatePicker>
                    </Col>
                    <Col span="8">
                    <DatePicker  format="yyyy-MM-dd" type="date" placeholder="截止日期" @on-change="end"></DatePicker>
                  </Col>
                  </Row>
                </div>

              <Table :data="mealkinddata" :columns="mealcolumns" style="margin-left: 300px;margin-top: 30px" stripe>
              </Table>

              </div>

              <div style="margin-top: 50px" v-show="currentkindmealflag">
                <Button type="primary"  @click="addmeal">添加单品</Button>
              </div>

              <!--用来显示套餐的div-->
              <div style="height: 800px" v-show="currentcommealflag">
                <Table :data="currentcombodata" :columns="cobomealcolumns" style="margin-left: 300px;margin-top: 30px" stripe>
                </Table>

              </div>

              <div style="margin-top: 0px" v-show="currentcommealflag">
                <Button type="primary"  @click="addcobomeal">添加套餐</Button>
              </div>

            </div>
            <!--添加新的单品的内容-->
            <div v-show="newmealflag">
              <DatePicker format="yyyy-MM-dd" type="date" @on-change="endmeal"  placeholder="截止时间"></DatePicker>
              <Upload
                :format="['jpg','jpeg','png']"
                multiple
                type="drag"
                action="//jsonplaceholder.typicode.com/posts/">
                <div style="padding: 20px 0">
                  <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                  <p>Click or drag files here to upload</p>
                </div>
              </Upload>
              <Form :label-width="80">
                <FormItem label="名称：">
                  <i-input type="text" v-model="mealname" style="width: 250px">
                    <Icon type="ios-person-outline" slot="prepend"></Icon>
                  </i-input>
                </FormItem>
                <FormItem label="价格：">
                  <i-input type="text" v-model="mealprice" style="width: 250px">
                    <Icon type="ios-person-outline" slot="prepend"></Icon>
                  </i-input>
                </FormItem>
              </Form>
              <Button type="primary" @click="addnewmeal">添加</Button>
            </div>

            <!--添加新的套餐的内容-->
            <div v-show="newcobomealflag">
              <DatePicker format="yyyy-MM-dd" type="date" @on-change="endcobmeal"  placeholder="截止时间"></DatePicker>
              <Upload
                :format="['jpg','jpeg','png']"
                multiple
                type="drag"
                action="//jsonplaceholder.typicode.com/posts/">
                <div style="padding: 20px 0">
                  <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                  <p>Click or drag files here to upload</p>
                </div>
              </Upload>
              <Form :label-width="80">
                <FormItem label="名称：">
                  <i-input type="text" v-model="cobomealname" style="width: 250px">
                    <Icon type="ios-person-outline" slot="prepend"></Icon>
                  </i-input>
                </FormItem>
              </Form>
              <Table :data="comealdata" :columns="mealcolumns">
              </Table>
              <div style="margin-top: 20px">
              <Button type="primary" @click="addmealtocobo">添加菜品</Button>
              <Button type="primary" @click="confirmcobomeal">确认添加</Button>
              </div>
            </div>

            <!--查看统计信息-->
            <div v-show="staticsflag">
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
        nummealflag:false,//套餐中单品数量的flag
        onenum:0,//添加到套餐中单品的数量
        cobomealflag:false,//添加单品到套餐的flag
        endmealdate:'',//截止单品的时间
        mealname:'',//单品的名称
        cobomealname:'',//套餐的名称
        comealdata:[],//加入套餐中的菜品的数据
        comealindex:0,//加入套餐中菜品的index
        mealprice:0,//单品价格
        mealurl:'',//单品的url
        endcobomealdate:'',//截止套餐的时间
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
        orderdata:[],//订单的数据
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
          }],//订单的列
        staticsflag:false,//统计的flag
        menuflag:false,//是否可以发布
        newmealflag:false,//新单品的内容
        newcobomealflag:false,//新套餐的内容
        startdata:'',
        enddata:'',
        currentkindmealflag:false,//显示当前类型的菜品的表格
        numsetflag:false,
        num:'',//菜品的数量
        //全部菜品的id
        mealtabledata:[],
        allmealdata:[],
        mealcolumnskind:[ {
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
          }
          ],
        mealcolumns: [
          {
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
          }
        ],
        kindindex:0,//当前在为第几个菜品添加数量
        kindmealindex:0,//当前为第几个类型
        mealkindaddflag:false,//给类型添加单品
        allmealflag:false,
        //发布菜品
        kindaddflag:false,
        kindtoadd:'',//输入类型的名称
        changemealflag:false,
        mealkind:[],//meal的kind
        mealid:[],//保存Meal与Kind想对应的id,index相对应，保存时用;分隔
        nummeal:[],//保存Meal的数量与Kind相对于，index对于，用;分隔
        mealkinddata:[],//显示当时的类型所有的餐品的信息
        identity:'',
        username:'',
        userid:'',
        kind:'',
        address:'',
        phone:'',
        money:'',
        //关于如何发布套餐
        cobomealcolumnsadd:[
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
        cobomealcolumns:[{
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
         ],
        currentcombodata:[],
        allcombodata:[],
        comealaddflag:false,
        coid:[],//保存了套餐的id,最后用以特定的格式进行保存
      }
    },
    created(){
      var self = this
      this.userid=window.localStorage.getItem("userID")
      this.identity=window.localStorage.getItem("identity")
      this.$http.get('api/dinningcontroller/checkdin/'+self.userid,{
      }).then(function(response){
        var m =response.data.split(";")
        self.username=m[0]
        self.kind=m[1]
        self.address=m[2]
        self.phone=m[3]
        self.money=m[4]
        if (m[5]=="true"){
          self.menuflag=true
        }else{
          self.menuflag=false
        }
      }).catch(function (error) {
        self.$Message.warning("失败")
      })
      this.$http.get('api/dinningcontroller/getorderflag/'+this.userid,{
      }).then(function (response) {
        self.orderflag=response.data
      })
    },
    methods:{
      numsetco(){//确认将产品添加进去
        var self = this
        this.nummealflag=false
        this.comealdata.push(this.mealtabledata[this.comealindex])
        this.comealdata[this.comealdata.length-1].num=this.onenum
      },
      confirmcobomeal(){//确认添加套餐
        var self = this
        var n =""//菜品的id
        var m =""//菜品的名称
        var d =""//菜品的数量
        var p =""//菜品的价格
        n = this.comealdata[0].id
        m = this.comealdata[0].name
        d = this.comealdata[0].num
        p = this.comealdata[0].num*this.comealdata[0].price
        for (var i =1;i<this.comealdata.length;i++){
          n = n + ";" + this.comealdata[i].id
          m = m + ";" + this.comealdata[i].name
          d = d + ";" + this.comealdata[i].num
          p = p + this.comealdata[i].num*this.comealdata[i].price
        }
        this.$http.get('api/dinningcontroller/addcombomeal/'+this.cobomealname+"/"+encodeURIComponent(n)+"/"+
          encodeURIComponent(m)+"/"+encodeURIComponent(d)+ "/"+p+"/"+"1.jpg"+"/"+this.userid+"/"+this.endcobomealdate,{
        }).then(function (response) {
          self.$Message.success("添加套餐成功")
          self.comealdata.splice(0,self.comealdata.length)
          self.cobomealname=""
      })
      },
      addmealtoco(index){//添加单品到套餐的
        this.comealindex=index
        this.nummealflag=true
      },
      addmealtocobo(){//添加菜品到套餐中
        this.cobomealflag=true
      },
      //添加新品
      addnewmeal(){
        var self=this
        this.mealurl="1.jpg"
        this.$http.get('api/dinningcontroller/addmeal/'+this.mealname+"/"+this.mealprice+"/"+encodeURIComponent(this.mealurl)
          +"/"+this.userid+"/"+this.endmealdate,{
        }).then(function (response) {
          self.$Message.success("添加成功")
          self.mealname=""
          self.mealprice=0
          self.mealurl=""
          self.endmealdate=""
        })
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
      closea(){
        this.addressflag=false
      },
      close(){
        this.detailflag=false
      },
      changtoTongji(){//改变去统计
        this.newmealflag = false
        this.newcobomealflag = false
        this.allmealflag = false
        this.changemealflag = false
        this.staticsflag=true
        var self = this
        this.$http.get('api/managercontroller/checkdinor/'+this.userid,{
        }).then(function (response) {
          self.orderdata = response.data
        })
      },
      //改变去添加新的单品
      changtocreatenewmeal(){
        if(this.menuflag==true) {
          this.newmealflag = true
          this.newcobomealflag = false
          this.allmealflag = false
          this.changemealflag = false
          this.staticsflag=false
        }else{
          this.$Message.warning("未通过审核，不可以发布")
        }
      },
      //改变去添加新的套餐
      changtocreatenewcobomeal(){
        if(this.menuflag==true) {
          this.newmealflag = false
          this.newcobomealflag=true
          this.currentkindmealflag = false
          this.allmealflag = false
          this.changemealflag = false
          this.staticsflag=false
        }else{
          this.$Message.warning("未通过审核,不可以发布")
        }
      },
      endmeal(endtime){
        this.endmealdate=endtime
      },
      endcobmeal(endtime){
        this.endcobomealdate=endtime
      },
      start(starttime){
        this.startdata=starttime
      },
      end(endtime){
        this.enddata=endtime
      },
      publish(){
        var n = ""
        for ( var i =0;i<this.mealkind.length;i++){
          if(i==0){
            n=this.mealkind[i]
          }else{
            var tmp=n
            n=tmp + ";" + this.mealkind[i]
          }
        }
        var n1 = ""
        for (var i = 0 ;i < this.mealid.length; i++){
          if(i == 0){
            n1=this.mealid[i]
          }else{
            var tmp = n1
            n1 = tmp + ";" + this.mealid[i]
          }
        }
        var n2 = ""
        for (var i = 0 ;i < this.nummeal.length; i++){
          if(i == 0){
            n2=this.nummeal[i]
          }else{
            var tmp = n2
            n2 = tmp + ";" + this.nummeal[i]
          }
        }
        var n3=""
        for (var i = 0 ;i < this.coid.length; i++){
          if(i == 0){
            n3=this.coid[i]
          }else{
            var tmp = n3
            n3 = tmp + ";" + this.coid[i]
          }
        }
        var self=this
        if(this.orderflag==false){
          this.$Message.warning("餐厅未审核，不可发布新的菜单请等待审核")
        }else {
          this.$http.get('api/dinningcontroller/publishmenu/' + this.userid + "/" + this.startdata + "/" + this.enddata + "/" +
            encodeURIComponent(n) + "/" + encodeURIComponent(n1) + "/" + encodeURIComponent(n2) + "/" + encodeURIComponent(n3),
            {}).then(function (response) {
            self.$Message.success("成功发布")
          })
        }
      },
      //如何添加套餐的方法
      addcobo(index){//加入一个套餐的方法
        var result=false
        for (var i = 0 ; i<this.currentcombodata.length;i++){
          if(this.allcombodata[index].id==this.currentcombodata[i].id){
            result=true
            break
          }
        }
        if(result==false) {
          this.currentcombodata.push(this.allcombodata[index])
          this.coid.push(this.allcombodata[index].id)
        }else{
          this.$Message.warning("已经存在该套餐")
        }
      },
      addcobomeal(){
        this.comealaddflag=true
      },
      confirmaddco(){
        this.comealaddflag=false
      },
      addtocombomenu(){
        this.currentkindmealflag=true
        this.currentcommealflag=true
        this.currentkindmealflag=false
        var self=this
        //去后台拿去套餐
        this.$http.get('api/dinningcontroller/getcombomealbyid/'+this.userid,{
        }).then(function(response){
          self.allcombodata=response.data
        })
      },
      //将一个产品加入到一个类型
      numset(){
        var m = this.allmealdata[this.kindindex].id;//菜品id
        var self=this
        this.$http.get('api/dinningcontroller/updatemeal/'+m+"/"+this.num,{
        }).then(function (response) {
          //添加mealid和mealnum
          if (self.mealid[self.kindmealindex]==""){
            self.mealid[self.kindmealindex]=m
            self.nummeal[self.kindmealindex]=self.num
          }else{
            var n = self.mealid[self.kindmealindex]
            var t = self.nummeal[self.kindmealindex]
            self.mealid[self.kindmealindex]= n + "," + m
            self.nummeal[self.kindmealindex] = t + "," + self.num
          }
          self.allmealdata[self.kindindex].num= self.num
          self.numsetflag=false
          for (var i = 0 ; i < self.allmealdata.length ; i++){
            if(self.allmealdata[i].id == m){
              self.mealkinddata.push(self.allmealdata[i])
            }
          }
        })
      },
      addmealkind(index){
        this.numsetflag=true
        this.kindindex=index//第几个菜品在操作
      },
      confirmadd(){
        this.mealkindaddflag=false
      },
      //将产品添加到一个类型中
      saveadd(){
        this.mealkind.push(this.kindtoadd)
        this.mealid.push("")
        this.nummeal.push("")
        this.kindtoadd=""
        this.kindaddflag=false
      },
      cancle(){
        this.kindtoadd=""
        this.kindaddflag=false
      },
      //给类型中添加单品
      addmeal(){
        this.mealkindaddflag=true
      },
      addtokind(index){
        this.currentkindmealflag=true
        this.currentcommealflag=false
        this.mealkinddata.splice(0,this.mealkinddata.length)
        //显示已经有的食品
        this.kindmealindex=index
        //mealkinddata
        let n = this.mealid[this.kindmealindex].toString()
        var m = []
        m = n.split(",")
        for ( var i = 0 ;i < m.length ; i++){
          for ( var j =0; j < this.allmealdata.length; j ++){
            if (this.allmealdata[j].id == m[i]){
              this.mealkinddata.push(this.allmealdata[j])
            }
          }
        }
      },
      //添加类型
      addkind(){
        this.kindaddflag=true
      },
      changePage(value){
        this.mealtabledata.splice(0,this.mealtabledata.length)
        if (this.allmealdata.length>7*value-1){
          for ( var i = 7*(value-1) ; i< (7*value);i++){
            this.mealtabledata.push(this.allmealdata[i])
          }
        }else{
          for ( var i = 7*(value-1);i<this.allmealdata.length;i++){
            this.mealtabledata.push(this.allmealdata[i])
          }
        }
      },
      checkallmeal(){
        var self=this
        this.$http.get('api/dinningcontroller/getmealbyid/'+this.userid,{
        }).then(function (response) {
          self.allmealdata=response.data
          self.allmealflag=true
          self.changemealflag=false
          self.staticsflag=false
          self.newmealflag=false
          self.newcobomealflag=false
          self.mealtabledata.splice(0,self.mealtabledata.length)
          if(self.allmealdata.length>7){
            for (var i = 0 ;i<7; i++){
              self.mealtabledata.push(self.allmealdata[i])
            }
          }else{
            for (var i = 0 ;i<self.allmealdata.length; i++){
              self.mealtabledata.push(self.allmealdata[i])
            }
          }
          self.allmealflag=true
          self.changemealflag=false
        })
      },
      changtomenu(){
        if(this.menuflag==false){
          this.$Message.warning("未通过审核不可以发布")
        }else {
          var self = this
          this.allmealflag = false
          this.changemealflag = true
          this.staticsflag=false
          self.newmealflag=false
          self.newcobomealflag=false
        }
      },
      checkpersonal(){
        window.localStorage.setItem("userID",this.userid)
        window.localStorage.setItem("identity",this.identity)
        window.localStorage.setItem("username",this.username)
        window.localStorage.setItem("kind",this.kind)
        window.localStorage.setItem("address",this.address)
        window.localStorage.setItem("phone",this.phone)
        window.localStorage.setItem("money",this.money)
        this.$router.push({
          name:'dinningdetail'
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
