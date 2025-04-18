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
                    <el-form-item label="盘点人">
                        <SigleSelect url="/User/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.CheckUserId">
                        </SigleSelect>
                    </el-form-item>

                    <el-form-item label="货主">
                        <SigleSelect url="/Customer/List" columnName="OwnerName" :clearable="true" columnValue="Id"
                            v-model="searchForm.CustomerId" :where="{ WarehouseId: CurrentWarehouseId }">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="执行库存调整">
                        <el-select v-model="searchForm.IsExcuteInventoryChange" :clearable="true"
                            placeholder="请选择是否执行库存调整">
                            <el-option key="true" label="是" value="true">
                            </el-option>
                            <el-option key="false" label="否" value="false">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="盘点时间">
                        <el-date-picker v-model="searchForm.CheckTimeRange" type="datetimerange"
                            start-placeholder="开始盘点时间" end-placeholder="结束盘点时间" :default-time="['00:00:00']"
                            value-format="yyyy-MM-dd HH:mm:ss">
                        </el-date-picker>
                    </el-form-item>
                </el-form>
            </div>

        </el-card>


        <el-dialog :title="formData.Id ? '修改盘点单' : '添加盘点单'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
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
                        <el-form-item label="盘点时间" prop="CheckTimeRange">
                            <el-date-picker v-model="formData.CheckTimeRange" align="right" type="datetimerange"
                                range-separator="-" start-placeholder="开始盘点时间" end-placeholder="结束盘点时间" placeholder="选择"
                                value-format="yyyy-MM-dd HH:mm:ss">
                            </el-date-picker>
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


        <PaginationTable ref="PaginationTableId" url="/InventoryCheck/List" :column="dataColum" :where="where">
            <template v-slot:header>
                <el-button type="primary" size="mini" plain icon="el-icon-edit" @click="ShowEditModal()">新 增</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()">批 量 删
                    除</el-button>
            </template>
            <template v-slot:Operate="scope">
                <el-button class="margin-top-xs" type="primary" v-if="scope.row.InventoryCheckStatus != 3" size="mini"
                    @click="ShowEditModal(scope.row.Id)">修
                    改</el-button>
                <el-button class="margin-top-xs" type="success" size="mini" @click="ToInventoryCheckDetList(scope.row)">
                    盘点明细</el-button>
                <el-button class="margin-top-xs" v-if="scope.row.InventoryCheckStatus == 2" type="warning" size="mini"
                    @click="Completed(scope.row.Id)">盘点完成
                </el-button>
                <el-button class="margin-top-xs"
                    v-if="scope.row.InventoryCheckStatus == 3 && scope.row.IsExcuteInventoryChange != true"
                    type="warning" size="mini" @click="CompletedStockChange(scope.row.Id)">完成库存调整
                </el-button>
                <el-button class="margin-top-xs" type="danger" v-if="scope.row.InventoryCheckStatus != 3" size="mini"
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
    name: "InventoryCheckList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", 'CurrentWarehouseId'
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
                    key: "CheckUserId",
                    hidden: true,
                },
                {
                    key: "CheckUserDto.Name",
                    title: "盘点人",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
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
                    key: "CustomerId",
                    hidden: true,
                },
                {
                    key: "CustomerDto.OwnerName",
                    title: "货主",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "InventoryCheckStatus",
                    hidden: true,
                },

                {
                    key: "InventoryCheckStatusFormat",
                    title: "盘点状态",


                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "IsExcuteInventoryChange",
                    title: "是否执行库存调整",


                    type: store.getters.ColumnType.JUDGMENTTAG,
                },
                {
                    key: "CheckTime",
                    title: "盘点时间",

                    type: store.getters.ColumnType.SHORTTEXT,
                    template: function (item, index) {
                        return `${item.BeginCheckTime}至${item.EndCheckTime}`
                    }
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
                "CheckUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "WarehouseId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CustomerId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CheckTimeRange": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "IsExcuteInventoryChange": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "InventoryCheckStatus": [
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
        async ToInventoryCheckDetList(row) {
            this.$router.push({
                path: '/Admin/InventoryCheckDetList',
                query: {
                    InventoryCheckId: row.Id,
                    CustomerId: row.CustomerId,
                    InventoryCheckStatus: row.InventoryCheckStatus,
                }
            });
        },
        //盘点完成
        async Completed(Id) {
            let { Data } = await this.$PostSigleUpdate(`/InventoryCheck/Get`, `/InventoryCheck/Completed`, Id, '你确定盘点完成了吗?', {});

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        //完成库存调整
        async CompletedStockChange(Id) {
            let { Data } = await this.$PostSigleUpdate(`/InventoryCheck/Get`, `/InventoryCheck/CompletedStockChange`, Id, '你确定完成库存调整吗?', {});

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/InventoryCheck/Get`, { Id: Id });
            if (!Id) {
                Data.WarehouseId = this.CurrentWarehouseId;
                Data.CheckUserId = this.UserId;
                Data.InventoryCheckStatus = 1;
                Data.IsExcuteInventoryChange = false;
            }
            if (Data.BeginCheckTime && Data.EndCheckTime) {
                Data.CheckTimeRange = [Data.BeginCheckTime, Data.EndCheckTime];
            }
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    this.formData.BeginCheckTime = this.formData.CheckTimeRange[0];
                    this.formData.EndCheckTime = this.formData.CheckTimeRange[1];
                    var { Success } = await this.$Post(`/InventoryCheck/CreateOrEdit`, this.formData);

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
            await this.$PostDelete(`/InventoryCheck/Delete`, { Id: Id });
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
            await this.$PostDelete(`/InventoryCheck/BatchDelete`, { Ids: ids });

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