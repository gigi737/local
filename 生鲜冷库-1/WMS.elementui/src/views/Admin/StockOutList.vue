<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="单号" prop="No">
                        <el-input v-model.trim="searchForm.No" placeholder="请输入单号" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="备注" prop="Remark">
                        <el-input v-model.trim="searchForm.Remark" placeholder="请输入备注" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="货主">
                        <SigleSelect url="/Customer/List" columnName="OwnerName" :clearable="true" columnValue="Id"
                            v-model="searchForm.CustomerId" :where="{ WarehouseId: CurrentWarehouseId }">
                        </SigleSelect>
                    </el-form-item>

                    <el-form-item label="出库类型">
                        <SigleSelect url="/Select/StockOutTypeEnum" columnName="Name" :clearable="true"
                            columnValue="Code" v-model="searchForm.StockOutType">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="出库状态">
                        <SigleSelect url="/Select/StockOutStatusEnum" columnName="Name" :clearable="true"
                            columnValue="Code" v-model="searchForm.StockOutStatus">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="出库时间">
                        <el-date-picker v-model="searchForm.OutTimeRange" type="datetimerange"
                            start-placeholder="开始出库时间" end-placeholder="结束出库时间" :default-time="['00:00:00']"
                            value-format="yyyy-MM-dd HH:mm:ss">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="计划出库时间">
                        <el-date-picker v-model="searchForm.PlanOutTimeRange" type="datetimerange"
                            start-placeholder="开始计划出库时间" end-placeholder="结束计划出库时间" :default-time="['00:00:00']"
                            value-format="yyyy-MM-dd HH:mm:ss">
                        </el-date-picker>
                    </el-form-item>
                </el-form>
            </div>

        </el-card>


        <el-dialog :title="formData.Id ? '修改出库单' : '添加出库单'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody" style="max-height: 600px;overflow-y: scroll">
                    <el-col :span="24">
                        <el-form-item label="单号" prop="No">
                            <el-input type="text" v-model.trim="formData.No" placeholder="请输入单号"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="货主" prop="CustomerId">
                            <SigleSelect url="/Customer/List" columnName="OwnerName" columnValue="Id"
                                v-model="formData.CustomerId" :where="{ WarehouseId: CurrentWarehouseId }">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="出库类型" prop="StockOutType">
                            <SigleSelect url="/Select/StockOutTypeEnum" columnName="Name" columnValue="Code"
                                v-model="formData.StockOutType">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>


                    <el-col :span="24">
                        <el-form-item label="计划出库时间" prop="PlanOutTime">
                            <el-date-picker v-model="formData.PlanOutTime" align="right" type="datetime"
                                placeholder="选择时间" value-format="yyyy-MM-dd HH:mm:ss">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="备注" prop="Remark">
                            <el-input type="textarea" v-model.trim="formData.Remark" placeholder="请输入备注"
                                :clearable="true"></el-input>
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


        <PaginationTable ref="PaginationTableId" url="/StockOut/List" :column="dataColum" :where="where">
            <template v-slot:header>
                <el-button type="primary" size="mini" plain icon="el-icon-edit" @click="ShowEditModal()">新 增</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()">批 量 删
                    除</el-button>
            </template>
            <template v-slot:Operate="scope">
                <el-button class="margin-top-xs" type="primary" size="mini" v-if="scope.row.StockOutStatus != 5"
                    @click="ShowEditModal(scope.row.Id)">修
                    改</el-button>
                <el-button class="margin-top-xs" type="success" size="mini"
                    @click="ToStockOutDetail(scope.row)">出库明细</el-button>
                <el-button class="margin-top-xs" type="warning" size="mini" v-if="scope.row.StockOutStatus == 3"
                    @click="Complete(scope.row.Id)">出库完成</el-button>

                <el-button class="margin-top-xs" v-if="scope.row.StockOutStatus != 5" type="danger" size="mini"
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
    name: "StockOutList",
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
                    key: "No",
                    title: "单号",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "CustomerId",
                    hidden: true,
                },
                {
                    key: "WarehouseId",
                    hidden: true,
                },
                {
                    key: "WarehouseDto.Name",
                    title: "冷库名称",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "OutTime",
                    title: "出库时间",
                    width: "240px",
                    type: store.getters.ColumnType.DATETIME,
                },
                {
                    key: "PlanOutTime",
                    title: "计划出库时间",
                    width: "240px",
                    type: store.getters.ColumnType.DATETIME,
                },
                {
                    key: "Remark",
                    title: "备注",


                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "StockOutType",
                    hidden: true,
                }, {
                    key: "StockOutTypeFormat",
                    title: "出库类型",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "StockOutStatus",
                    hidden: true,
                }, {
                    key: "StockOutStatusFormat",
                    title: "出库状态",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    title: "操作",
                    width: "300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
                "No": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CustomerId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "WarehouseId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "StockOutType": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "StockOutStatus": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "OutTime": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "PlanOutTime": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Remark": [
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


        //出库
        async Complete(Id) {
            let { Data } = await this.$PostSigleUpdate(`/StockOut/Get`, `/StockOut/Complete`, Id, "你确定要出库吗?", {});

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },

        async ToStockOutDetail(row) {
            this.$router.push({
                path: "/Admin/StockOutDetList",
                query: {
                    StockOutId: row.Id,
                    StockOutType: row.StockOutType,
                    CustomerId: row.CustomerId,
                    StockOutStatus: row.StockOutStatus,
                }
            });
        },

        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/StockOut/Get`, { Id: Id });
            Data.StockOutStatus = 1;
            Data.WarehouseId = this.CurrentWarehouseId;
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/StockOut/CreateOrEdit`, this.formData);

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
            await this.$PostDelete(`/StockOut/Delete`, { Id: Id });
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
            await this.$PostDelete(`/StockOut/BatchDelete`, { Ids: ids });

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