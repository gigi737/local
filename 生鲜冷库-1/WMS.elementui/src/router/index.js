import store from "@/store";
import { getToken } from "@/utils/cache";
import AdminLayout from "@/views/Admin/Layout/index.vue";
import NProgress from "nprogress"; // progress bar
import "nprogress/nprogress.css"; // progress bar style
import Vue from "vue";
import VueRouter from "vue-router";
NProgress.inc(0.2);
NProgress.configure({
  easing: "ease",
  speed: 1000,
  showSpinner: false,
  trickle: false,
});
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: "/Login",
  },
  {
    path: "/Login",
    component: () => import("@/views/Login.vue"),
  },

  {
    path: "/ForgetPassword",
    component: () => import("@/views/ForgetPassword.vue"),
  },
];

export const adminRouters = [
  {
    path: "/Admin",
    redirect: "/Admin/Home",
    component: AdminLayout,
    meta: {
      title: "控制台",
      isAdmin: true,
    },
    children: [
      {
        path: "/Admin/Home",
        meta: {
          isAdmin: true,
        },
        component: () => import("@/views/Admin/Home"),
      },
      {
        path: "/Admin/UserList",
        meta: {
          title: "用户信息",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/UserList"),
      },
      {
        path: "/Admin/UserPerson",
        meta: {
          title: "个人信息",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/UserPerson"),
      },
      {
        path: "/Admin/PasswordEdit",
        meta: {
          title: "修改密码",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/PasswordEdit"),
      },

      {
        path: "/Admin/CargoList",
        meta: {
          title: "商品",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/CargoList"),
      },

      {
        path: "/Admin/CargoTypeList",
        meta: {
          title: "商品分类",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/CargoTypeList"),
      },

      {
        path: "/Admin/CustomerList",
        meta: {
          title: "货主",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/CustomerList"),
      },

      {
        path: "/Admin/InventoryCheckList",
        meta: {
          title: "盘点单",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/InventoryCheckList"),
      },

      {
        path: "/Admin/InventoryCheckDetList",
        meta: {
          title: "盘点明细",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/InventoryCheckDetList"),
      },

      {
        path: "/Admin/InventoryRecordList",
        meta: {
          title: "库存记录",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/InventoryRecordList"),
      },

      {
        path: "/Admin/InventoryRuningRecordList",
        meta: {
          title: "库存流水记录",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/InventoryRuningRecordList"),
      },

      {
        path: "/Admin/StockInList",
        meta: {
          title: "入库单",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/StockInList"),
      },

      {
        path: "/Admin/StockInDetList",
        meta: {
          title: "入库明细",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/StockInDetList"),
      },

      {
        path: "/Admin/StockOutList",
        meta: {
          title: "出库单",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/StockOutList"),
      },

      {
        path: "/Admin/StockOutDetList",
        meta: {
          title: "出库明细",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/StockOutDetList"),
      },

      {
        path: "/Admin/WarehouseList",
        meta: {
          title: "冷库",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/WarehouseList"),
      },

      {
        path: "/Admin/WarehouseBinList",
        meta: {
          title: "库位",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/WarehouseBinList"),
      },

      {
        path: "/Admin/WarehouseRelativeUserList",
        meta: {
          title: "冷库用户",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/WarehouseRelativeUserList"),
      },
      {
        path: "/Admin/StockInEchart",
        meta: {
          title: "入库统计",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/StockInEchart"),
      },
      {
        path: "/Admin/StockOutEchart",
        meta: {
          title: "出库统计",
          isAdmin: true,
        },
        component: () => import("@/views/Admin/StockOutEchart"),
      },
    ],
  },
];

const router = new VueRouter({
  routes: [...routes, ...adminRouters], // (缩写) 相当于 routes: routes
});

// 编写导航路由
router.beforeEach(async (to, from, next) => {
  NProgress.start();
  const hasToken = getToken();

  if (hasToken) {
    if (store.getters.HasUserInfo == false) {
      await store.dispatch("GetInfo");
      if (store.getters.UserId == 0 || store.getters.UserId == null) {
        store.dispatch("Logout");
      }
    }
    //如果去往的页面是后台
    if (to.meta && to.meta.isAdmin) {
      if (
        store.getters.RoleType == "管理员" ||
        store.getters.RoleType == "员工"
      ) {
        next();
      } else {
        next({ path: "/" });
      }
    } else {
      if (
        store.getters.RoleType == "管理员" ||
        store.getters.RoleType == "员工"
      ) {
        next({ path: "/Admin" });
      } else {
        next();
      }
    }
  } else {
    if (to.meta && to.meta.isAdmin) {
      next({ path: "/Login" });
    } else {
      next();
    }
  }
  NProgress.done();
});
router.afterEach(() => {
  // finish progress bar
  window.scrollTo({ top: 0 });
  NProgress.done();
});

export default router;
