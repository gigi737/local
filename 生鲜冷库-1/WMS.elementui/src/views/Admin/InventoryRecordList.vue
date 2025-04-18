<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">

                    <el-form-item label="货主">
                        <SigleSelect url="/Customer/List" columnName="OwnerName" :clearable="true" columnValue="Id"
                            v-model="searchForm.CustomerId" :where="{ WarehouseId: CurrentWarehouseId }">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="商品">
                        <SigleSelect url="/Cargo/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.CargoId" :where="{ WarehouseId: CurrentWarehouseId }">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="库位">
                        <SigleSelect url="/WarehouseBin/List" columnName="BinCode" :clearable="true" columnValue="Id"
                            v-model="searchForm.WarehouseBinId" :where="{ WarehouseId: CurrentWarehouseId }">
                        </SigleSelect>
                    </el-form-item>
                </el-form>
            </div>

        </el-card>





        <PaginationTable ref="PaginationTableId" url="/InventoryRecord/List" :column="dataColum" :where="where">

        </PaginationTable>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex';
export default {
    name: "InventoryRecordList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", "CurrentWarehouseId"
        ])
    },
    data() {

        return {
            where: {},
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                },
                {
                    key: "WarehouseId",
                    hidden: true,
                },
                {
                    key: "WarehouseDto.Name",
                    title: "冷库名称",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "CustomerId",
                    hidden: true,
                },
                {
                    key: "CustomerDto.OwnerName",
                    title: "货主",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "WarehouseBinDto.BinCode",
                    title: "库位",


                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "CargoId",
                    hidden: true,
                },
                {
                    key: "CargoDto.Name",
                    title: "商品名称",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "WarehouseBinId",
                    hidden: true,
                },
                {
                    key: "StockQty",
                    title: "库存数量",

                    type: store.getters.ColumnType.SHORTTEXT,
                },

            ],
            editModalFormRules: {
                "WarehouseId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CustomerId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CargoId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "WarehouseBinId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "StockQty": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,

        };
    },
    created() {
        this.where.WarehouseId = this.CurrentWarehouseId;
    },
    methods: {
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/InventoryRecord/Get`, `/InventoryRecord/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/InventoryRecord/Get`, { Id: Id });
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/InventoryRecord/CreateOrEdit`, this.formData);

                    if (Success) {
                        this.editorShow = false;
                        this.$refs.PaginationTableId.Reload(this.searchForm);
                    }
                }
            })
        },
        /**
         * 单个删除的时候会触发
         */
        async ShowDeleteModal(Id) {
            await this.$PostDelete(`/InventoryRecord/Delete`, { Id: Id });
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 批量删除的时候会触发
         */
        async ShowBatchDeleteModal() {
            var ids = this.$refs.PaginationTableId.GetSelectionRow().map(x => x.Id);
            if (ids.length == 0) {
                this.$message.error("你选择需要删除的记录");
                return;
            }
            await this.$PostDelete(`/InventoryRecord/BatchDelete`, { Ids: ids });

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击表格搜索按钮会触发
         */
        async SearchClick() {
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击清空表单会触发
         */
        async ResetClick() {
            this.searchForm = {};
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },




    },
};
</script>