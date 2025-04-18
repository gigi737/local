<template>
    <div class="app-container">
        <el-card class="box-card">            
            <div slot="header" class="clearfix">
          		   <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="用户">
                        <SigleSelect url="/User/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.UserId">
                        </SigleSelect>
                    </el-form-item>                
                    <el-form-item label="冷库">
                        <SigleSelect url="/Warehouse/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.WarehouseId">
                        </SigleSelect>
                    </el-form-item>                
                </el-form>
            </div>

        </el-card>


        <el-dialog  :title="formData.Id ? '修改冷库用户' : '添加冷库用户'"   :visible.sync="editorShow" width="50%" :lock-scroll="true" height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                 <el-row :gutter="10" class="EditFromBody"   >               
                    <el-col :span="24">
                        <el-form-item label="用户" prop="UserId">
                          <SigleSelect url="/User/List" columnName="Name" columnValue="Id"  v-model="formData.UserId" >
                          </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="冷库" prop="WarehouseId">
                          <SigleSelect url="/Warehouse/List" columnName="Name" columnValue="Id"  v-model="formData.WarehouseId" >
                          </SigleSelect>
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


        <PaginationTable ref="PaginationTableId" url="/WarehouseRelativeUser/List" :column="dataColum" :where="where">
            <template v-slot:header>
                <el-button type="primary" size="mini"  plain icon="el-icon-edit" @click="ShowEditModal()">新 增</el-button>
                <el-button type="danger"  size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()">批 量 删 除</el-button>
 				 </template>
            <template v-slot:Operate="scope">
                <el-button class="margin-top-xs" type="primary" size="mini"  @click="ShowEditModal(scope.row.Id)">修 改</el-button>
                <el-button class="margin-top-xs" type="danger" size="mini" @click="ShowDeleteModal(scope.row.Id)">删 除</el-button>
            </template>
        </PaginationTable>
    </div>
</template>
    
<script>
import store from '@/store';
import { mapGetters } from 'vuex'
import moment from 'moment'
export default {
    name: "WarehouseRelativeUserList",
 	 computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {
		   
        return {
            where:{}, 
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                },
               {
                    key: "UserId",
                    hidden: true, 
                },
               {
                    key: "UserDto.Name",
                    title: "名称",
							  
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
                    title: "操作",
                  	  width:"300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
              "UserId":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
              "WarehouseId":[
                { required: true, message: '该项为必填项', trigger: 'blur' },
              ],           
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,

        };
    },
    created() {

    },
    methods: {
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/WarehouseRelativeUser/Get`, `/WarehouseRelativeUser/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/WarehouseRelativeUser/Get`, { Id: Id });
            this.formData = Data;
            
            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {
         
            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/WarehouseRelativeUser/CreateOrEdit`, this.formData);
                  
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
            await this.$PostDelete(`/WarehouseRelativeUser/Delete`, { Id: Id });
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
            await this.$PostDelete(`/WarehouseRelativeUser/BatchDelete`, { Ids: ids });
       		
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
    