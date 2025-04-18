<template>
    <div>
        <el-container style="height: 100vh">

            <el-header>
                <div class="bg-header">
                    <div class="flex align-center">
                        <img :src="require('@/assets/logo.png')" style="width: 50px; height: 50px;">
                        <span style="margin-left:10px">生鲜冷库管理系统</span>
                    </div>
                    <div style="display: flex;align-items: center">
                        <div>当前冷库:</div>
                        <el-select v-model="CurrentWarehouseId" placeholder="请选择" @change="ChangeWarehouse"
                            style="margin-left: 10px;margin-right: 10px;">
                            <el-option v-for="item in warehouseList" :key="item.Id" :label="item.Name" :value="item.Id">
                            </el-option>
                        </el-select>
                        <el-avatar :size="60" :src="UserInfo.ImageUrls" size="medium">
                            <img :src="require('@/assets/默认头像.png')" />
                        </el-avatar>
                        <div style="margin-left:10px">
                            <el-dropdown>
                                <span class="el-dropdown-link" style="color:white">
                                    {{ UserInfo.Name }}<i class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item>
                                        <div @click="ToPath('/Admin/UserPerson')">个人信息</div>

                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                        <div @click="ToPath('/Admin/PasswordEdit')">修改密码</div>

                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                        <div @click="LoginOut()">退出</div>
                                    </el-dropdown-item>

                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>

                    </div>
                </div>


            </el-header>


            <el-container>
                <el-aside width="200px">

                    <el-menu class="menu-list" :router="true" active-text-color="var(--primary-color)">
                        <el-menu-item index="/Admin/Home">
                            <img class="meun-ico" :src="require('@/assets/控制台.png')"></img>
                            <span>控制台</span>
                        </el-menu-item>
                        <template v-if="RoleType == '管理员'">
                            <el-submenu index="/Admin/UserList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/用户管理.png')"></img>
                                    <span>用户管理</span>
                                </template>
                                <el-menu-item index="/Admin/UserList">
                                    <img class="meun-ico" :src="require('@/assets/用户信息.png')"></img>
                                    <span>用户信息</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/WarehouseRelativeUserList">
                                    <img class="meun-ico" :src="require('@/assets/冷库用户.png')"></img>
                                    <span>冷库用户</span>
                                </el-menu-item>
                            </el-submenu>
                            <el-submenu index="/Admin/WarehouseList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/基础资料.png')"></img>
                                    <span>基础资料</span>
                                </template>
                                <el-menu-item index="/Admin/WarehouseList">
                                    <img class="meun-ico" :src="require('@/assets/冷库.png')"></img>
                                    <span>冷库</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/WarehouseBinList">
                                    <img class="meun-ico" :src="require('@/assets/库位.png')"></img>
                                    <span>库位</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/CustomerList">
                                    <img class="meun-ico" :src="require('@/assets/货主.png')"></img>
                                    <span>货主</span>
                                </el-menu-item>

                            </el-submenu>
                            <el-submenu index="/Admin/CargoTypeList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/商品管理.png')"></img>
                                    <span>商品管理</span>
                                </template>
                                <el-menu-item index="/Admin/CargoTypeList">
                                    <img class="meun-ico" :src="require('@/assets/商品分类.png')"></img>
                                    <span>商品分类</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/CargoList">
                                    <img class="meun-ico" :src="require('@/assets/商品.png')"></img>
                                    <span>商品</span>
                                </el-menu-item>


                            </el-submenu>
                            <el-submenu index="/Admin/StockInList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/入库管理.png')"></img>
                                    <span>入库管理</span>
                                </template>
                                <el-menu-item index="/Admin/StockInList">
                                    <img class="meun-ico" :src="require('@/assets/入库单.png')"></img>
                                    <span>入库单</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/StockInEchart">
                                    <img class="meun-ico" :src="require('@/assets/出入库统计.png')"></img>
                                    <span>入库统计</span>
                                </el-menu-item>

                            </el-submenu>
                            <el-submenu index="/Admin/StockOutList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/出库管理.png')"></img>
                                    <span>出库管理</span>
                                </template>
                                <el-menu-item index="/Admin/StockOutList">
                                    <img class="meun-ico" :src="require('@/assets/出库单.png')"></img>
                                    <span>出库单</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/StockOutEchart">
                                    <img class="meun-ico" :src="require('@/assets/出入库统计.png')"></img>
                                    <span>出库统计</span>
                                </el-menu-item>

                            </el-submenu>


                            <el-submenu index="/Admin/InventoryCheckList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/库内管理.png')"></img>
                                    <span>库内管理</span>
                                </template>
                                <el-menu-item index="/Admin/InventoryCheckList">
                                    <img class="meun-ico" :src="require('@/assets/盘点单.png')"></img>
                                    <span>盘点单</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/InventoryRecordList">
                                    <img class="meun-ico" :src="require('@/assets/库存记录.png')"></img>
                                    <span>库存记录</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/InventoryRuningRecordList">
                                    <img class="meun-ico" :src="require('@/assets/库存流水记录.png')"></img>
                                    <span>库存流水记录</span>
                                </el-menu-item>
                            </el-submenu>
                        </template>
                        <template v-if="RoleType == '员工'">
                            <el-submenu index="/Admin/WarehouseList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/基础资料.png')"></img>
                                    <span>基础资料</span>
                                </template>

                                <el-menu-item index="/Admin/WarehouseBinList">
                                    <img class="meun-ico" :src="require('@/assets/库位.png')"></img>
                                    <span>库位</span>
                                </el-menu-item>


                            </el-submenu>
                            <el-submenu index="/Admin/CargoTypeList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/商品管理.png')"></img>
                                    <span>商品管理</span>
                                </template>
                                <el-menu-item index="/Admin/CargoTypeList">
                                    <img class="meun-ico" :src="require('@/assets/商品分类.png')"></img>
                                    <span>商品分类</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/CargoList">
                                    <img class="meun-ico" :src="require('@/assets/商品.png')"></img>
                                    <span>商品</span>
                                </el-menu-item>


                            </el-submenu>
                            <el-submenu index="/Admin/StockInList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/入库管理.png')"></img>
                                    <span>入库管理</span>
                                </template>
                                <el-menu-item index="/Admin/StockInList">
                                    <img class="meun-ico" :src="require('@/assets/入库单.png')"></img>
                                    <span>入库单</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/StockInEchart">
                                    <img class="meun-ico" :src="require('@/assets/出入库统计.png')"></img>
                                    <span>入库统计</span>
                                </el-menu-item>

                            </el-submenu>
                            <el-submenu index="/Admin/StockOutList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/出库管理.png')"></img>
                                    <span>出库管理</span>
                                </template>
                                <el-menu-item index="/Admin/StockOutList">
                                    <img class="meun-ico" :src="require('@/assets/出库单.png')"></img>
                                    <span>出库单</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/StockOutEchart">
                                    <img class="meun-ico" :src="require('@/assets/出入库统计.png')"></img>
                                    <span>出库统计</span>
                                </el-menu-item>

                            </el-submenu>


                            <el-submenu index="/Admin/InventoryCheckList">
                                <template slot="title">
                                    <img class="meun-ico" :src="require('@/assets/库内管理.png')"></img>
                                    <span>库内管理</span>
                                </template>
                                <el-menu-item index="/Admin/InventoryCheckList">
                                    <img class="meun-ico" :src="require('@/assets/盘点单.png')"></img>
                                    <span>盘点单</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/InventoryRecordList">
                                    <img class="meun-ico" :src="require('@/assets/库存记录.png')"></img>
                                    <span>库存记录</span>
                                </el-menu-item>
                                <el-menu-item index="/Admin/InventoryRuningRecordList">
                                    <img class="meun-ico" :src="require('@/assets/库存流水记录.png')"></img>
                                    <span>库存流水记录</span>
                                </el-menu-item>
                            </el-submenu>
                        </template>





                    </el-menu>
                </el-aside>

                <el-main class="admin-main">
                    <el-breadcrumb separator-class="el-icon-arrow-right" class="margin-bottom-xs">
                        <el-breadcrumb-item v-for="(item, index) in breadList" :key="index" :to="item.path">{{
                            item.meta.title
                        }}</el-breadcrumb-item>
                    </el-breadcrumb>

                    <transition>
                        <router-view></router-view>
                    </transition>

                </el-main>

            </el-container>
        </el-container>
    </div>
</template>

<script>

import { adminRouters } from "@/router/index";
import { mapGetters } from "vuex";
export default {
    name: 'Layout',
    data() {
        return {
            routerMenu: [],
            breadList: [],
            warehouseList: [],
            "CurrentWarehouseId": null,

        }
    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'RoleTypeFormat', 'HasUserInfo', 'ColumnType', "UserId",
        ])
    },
    watch: {
        $route() {
            this.getBreadcrumb();
        },
    },

    created() {
        this.CurrentWarehouseId = this.$store.getters.CurrentWarehouseId;

        this.routerMenu = adminRouters;
        this.getBreadcrumb();
        this.GetWarehouseList();
    },
    methods: {
        //跳转
        async ToPath(url) {
            this.$router.push({
                path: url
            })
        },
        async GetWarehouseList() {
            let { Data: { Items } } = await this.$Post("/WarehouseRelativeUser/List", { UserId: this.UserId })
            let list = Items.map(item => item.WarehouseDto);
            for (let item of list) {
                item.Id = item.Id.toString();
            }
            this.warehouseList = list;
            if (!this.CurrentWarehouseId) {

                this.CurrentWarehouseId = this.warehouseList[0].Id;
                this.$store.dispatch("SetCurrentWarehouseId", this.warehouseList[0].Id);
            }
        },
        async ChangeWarehouse(id) {

            this.$store.dispatch("SetCurrentWarehouseId", id);
            window.location.reload();
        },
        async LoginOut() {

            await this.$store.dispatch('Logout')
            window.location.reload('/Login')
        },
        isHome(route) {
            return route.path === "/Admin";
        },
        getBreadcrumb() {
            let matched = this.$route.matched;
            if (!this.isHome(matched[0])) {
                matched = [{ path: "/Admin", meta: { title: "控制台" } }].concat(matched);
            }
            this.breadList = matched;

        }
    }
}
</script>

<style>
.el-header,
.el-footer {

    text-align: center;
    line-height: 60px;
    padding: 0px !important;
}

.el-aside {

    color: #333;
    text-align: center;
    line-height: 200px;
}


.menu-list {
    height: calc(100vh - 60px);
}

.el-submenu__title {
    text-align: left
}

.el-menu-item {
    text-align: left;
}

.admin-main {
    height: calc(100vh - 60px) !important;
}

.meun-ico {
    width: 25px;
    height: 25px;
    margin-right: 5px;
}
</style>