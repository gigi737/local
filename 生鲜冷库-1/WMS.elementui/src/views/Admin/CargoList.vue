<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="商品名称" prop="Name">
                        <el-input v-model.trim="searchForm.Name" placeholder="请输入商品名称" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="条码" prop="SKU">
                        <el-input v-model.trim="searchForm.SKU" placeholder="请输入条码" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="品牌" prop="Brand">
                        <el-input v-model.trim="searchForm.Brand" placeholder="请输入品牌" :clearable="true"></el-input>
                    </el-form-item>

                    <el-form-item label="货主">
                        <SigleSelect url="/Customer/List" columnName="OwnerName" :clearable="true" columnValue="Id"
                            v-model="searchForm.CustomerId" :where="{ WarehouseId: CurrentWarehouseId }">
                        </SigleSelect>
                    </el-form-item>
                    <el-form-item label="商品分类">
                        <SigleSelect url="/CargoType/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.CargoTypeId" :where="{ WarehouseId: CurrentWarehouseId }">
                        </SigleSelect>
                    </el-form-item>
                </el-form>
            </div>

        </el-card>


        <el-dialog :title="formData.Id ? '修改商品' : '添加商品'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody" style="max-height: 600px;overflow-y: scroll">
                    <el-col :span="24">
                        <el-form-item label="商品名称" prop="Name">
                            <el-input type="text" v-model.trim="formData.Name" placeholder="请输入商品名称"
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
                        <el-form-item label="商品分类" prop="CargoTypeId">
                            <SigleSelect url="/CargoType/List" columnName="Name" columnValue="Id"
                                v-model="formData.CargoTypeId" :where="{ WarehouseId: CurrentWarehouseId }">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="条码" prop="SKU">
                            <el-input type="text" v-model.trim="formData.SKU" placeholder="请输入条码"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="品牌" prop="Brand">
                            <el-input type="text" v-model.trim="formData.Brand" placeholder="请输入品牌"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="规格型号" prop="Specification">
                            <el-input type="text" v-model.trim="formData.Specification" placeholder="请输入规格型号"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="单位" prop="Unit">
                            <el-input type="text" v-model.trim="formData.Unit" placeholder="请输入单位"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="最低库存预警" prop="MinStockAlert">
                            <el-input type="text" v-model.trim="formData.MinStockAlert" placeholder="请输入最低库存预警"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="最高库存限制" prop="MaxStockLimit">
                            <el-input type="text" v-model.trim="formData.MaxStockLimit" placeholder="请输入最高库存限制"
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


        <PaginationTable ref="PaginationTableId" url="/Cargo/List" :column="dataColum" :where="where">
            <template v-slot:header>
                <el-button type="primary" size="mini" plain icon="el-icon-edit" @click="ShowEditModal()">新 增</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()">批 量 删
                    除</el-button>
            </template>
            <template v-slot:Operate="scope">
                <el-button class="margin-top-xs" type="primary" size="mini" @click="ShowEditModal(scope.row.Id)">修
                    改</el-button>
                <el-button class="margin-top-xs" type="danger" size="mini" @click="ShowDeleteModal(scope.row.Id)">删
                    除</el-button>
            </template>
        </PaginationTable>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex';
export default {
    name: "CargoList",
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
                    key: "CargoTypeDto.Name",
                    title: "分类名称",


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
                    width: "160px",
                    title: "货主名称",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Name",
                    title: "商品名称",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "SKU",
                    title: "条码",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Brand",
                    title: "品牌",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Specification",
                    title: "规格型号",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Unit",
                    title: "单位",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "MinStockAlert",
                    title: "最低库存预警",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "MaxStockLimit",
                    title: "最高库存限制",
                    width: "160px",

                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "CargoTypeId",
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
                "Name": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "WarehouseId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CustomerId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "CargoTypeId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "SKU": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Brand": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Specification": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Unit": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "MinStockAlert": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "MaxStockLimit": [
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
            let { Data } = await this.$PostSigleUpdate(`/Cargo/Get`, `/Cargo/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/Cargo/Get`, { Id: Id });
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
                    var { Success } = await this.$Post(`/Cargo/CreateOrEdit`, this.formData);

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
            await this.$PostDelete(`/Cargo/Delete`, { Id: Id });
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
            await this.$PostDelete(`/Cargo/BatchDelete`, { Ids: ids });

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