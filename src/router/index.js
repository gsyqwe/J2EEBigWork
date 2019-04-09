import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login/Login'
import VipHome from '@/components/vip/VipHome'
import DinningHome from '@/components/dinning/DinningHome'
import ManagerHome from '@/components/manager/ManagerHome'
import VipDetail from '@/components/vip/VipDetail'
import VipAddress from '@/components/vip/VipAddress'
import DinningDetail from '@/components/dinning/DinningDetail'
import VipOrder from '@/components/vip/VipOrder'
import VipPay from '@/components/vip/VipPay'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path:'/VipHome',
      name: 'vip',
      component:VipHome,
    },
    {
      path:'/DinningHome',
      name:'dinning',
      component:DinningHome,
    },
    {
      path:'/ManagerHome',
      name:'manager',
      component:ManagerHome,
    },
    {
      path:'/VipDetail',
      name:'vipdetail',
      component:VipDetail,
    },
    {
      path:'/VipAddress',
      name:'vipaddress',
      component:VipAddress,
    },
    {
      path:'/DinningDetail',
      name:'dinningdetail',
      component:DinningDetail,
    },
    {
      path:'/VipOrder',
      name:'viporder',
      component:VipOrder,
    },
    {
      path:'/VipPay',
      name:'vippay',
      component:VipPay,
    }
  ]
})
