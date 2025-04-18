<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">

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


        <el-dialog :title="formData.Id ? '修改出库明细' : '添加出库明细'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody">
                    <el-col :span="24">
                        <el-form-item label="出库单" prop="StockOutId">
                            <SigleSelect url="/StockOut/List" columnName="No" columnValue="Id" :disabled="true"
                                v-model="formData.StockOutId">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="商品" prop="CargoId">
                            <SigleSelect url="/Cargo/List" columnName="Name" columnValue="Id"
                                @Change="InventoryQueryChange" v-model="formData.CargoId">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="库位" prop="WarehouseBinId">
                            <SigleSelect url="/WarehouseBin/List" columnName="BinCode" columnValue="Id"
                                @Change="InventoryQueryChange" v-model="formData.WarehouseBinId">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="数量" prop="Qty">
                            <el-input-number v-model="formData.Qty" :min="0" :max="formData.MaxQty"
                                label="描述文字"></el-input-number>
                            <span style="color:red;font-size:12px;">当前库位商品数量剩余:{{ formData.MaxQty }}</span>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row type="flex" justify="end" align="bottom">
                    <el-form-item>
                        <el-button type="primary" plain @click="CreateOrEditForm()">确 定</el-button>
                        <el-button @click="editorShow = false">取 消</el-button>
                    </el-form-item>
                </el-row>
            </el-form>

        </el-dialog>


        <PaginationTable ref="PaginationTableId" url="/StockOutDet/List" :column="dataColum" :where="where">
            <template v-slot:header>
                <el-button type="primary" v-if="StockOutStatus != 5" size="mini" plain icon="el-icon-edit"
                    @click="ShowEditModal()">新 增</el-button>
                <el-button type="danger" v-if="StockOutStatus != 5" size="mini" icon="el-icon-delete"
                    @click="ShowBatchDeleteModal()">批 量 删
                    除</el-button>
            </template>
            <template v-slot:Operate="scope">
                <el-button class="margin-top-xs" v-if="StockOutStatus != 5" type="primary" size="mini"
                    @click="ShowEditModal(scope.row.Id)">修
                    改</el-button>
                <el-button class="margin-top-xs" v-if="StockOutStatus != 5" type="danger" size="mini"
                    @click="ShowDeleteModal(scope.row.Id)">删
                    除</el-button>
            </template>
        </PaginationTable>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex';
export default {
    name: "StockOutDetList",
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
                    key: "StockOutId",
                    hidden: true,
                },
                {
                    key: "StockOutDto.No",
                    title: "单号",


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
                    key: "WarehouseId",
                    hidden: true,
                },
                {
                    key: "WarehouseDto.Name",
                    title: "冷库名称",


                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Qty",
                    title: "数量",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "WarehouseBinId",
                    hidden: true,
                },
                {
                    title: "操作",
                    width: "300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
                "StockOutId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CargoId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "WarehouseId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "WarehouseBinId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Qty": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                    { type: 'number', min: 0, message: '数量不能为负数', trigger: 'blur' },

                ],
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,
            StockOutStatus: null,
        };
    },
    created() {
        this.where.StockOutId = this.$route.query.StockOutId;
        this.StockOutStatus = this.$route.query.StockOutStatus;
    },
    methods: {
        async InventoryQueryChange() {
            if (this.formData.CargoId && this.formData.WarehouseBinId) {
                let { Data } = await this.$Post(`/InventoryRecord/Get`, {
                    Id: 0,
                    WarehouseBinId: this.formData.WarehouseBinId,
                    CargoId: this.formData.CargoId,
                    WarehouseId: this.CurrentWarehouseId,
                });
                if (Data.Id) {
                    this.formData.MaxQty = Data.StockQty;
                }
                else {
                    this.formData.MaxQty = 0;
                }
                this.$forceUpdate();
            }
        },
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/StockOutDet/Get`, `/StockOutDet/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/StockOutDet/Get`, { Id: Id });
            Data.StockOutId = this.$route.query.StockOutId;
            Data.WarehouseId = this.CurrentWarehouseId;
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {
            if (this.formData.Qty == 0) {
                this.$message.error("数量不能为0");
                return;
            }
            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/StockOutDet/CreateOrEdit`, this.formData);

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
            await this.$PostDelete(`/StockOutDet/Delete`, { Id: Id });
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
            await this.$PostDelete(`/StockOutDet/BatchDelete`, { Ids: ids });

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