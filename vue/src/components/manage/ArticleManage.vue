<template>

    <div style="margin: 10px 0;">
        <el-input
                v-model="search"
                placeholder="输入关键字"
                style="width: 21.5%"
                clearable
                @keyup.enter="load"
        />
        <el-button type="primary" style="margin: 0 10px" @click="load">查询</el-button>
    </div>

    <div style="margin-bottom: 10px">
        <el-table :data="tableData" border height="67vh" style="width: 100%">
            <el-table-column type="index" label="序号" width="60" />
            <el-table-column prop="id" label="ID" width="180" />
            <el-table-column prop="title" label="标题" width="180" :show-overflow-tooltip="true"/>
            <el-table-column prop="author" label="作者" width="180"/>
            <el-table-column prop="description" label="摘要" :show-overflow-tooltip="true"/>
            <el-table-column prop="cover" label="封面" width="100" :show-overflow-tooltip="true"/>
            <el-table-column prop="postbody" label="内容" width="180" :show-overflow-tooltip="true"/>
            <el-table-column prop="publishtime" label="发布时间" width="100" :show-overflow-tooltip="true"/>
            <el-table-column fixed="right" label="操作" width="120">
                <template #default="scope">
<!--                    <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>-->
                    <el-popconfirm title="确定删除吗?" @confirm="handleDel(scope.row)">
                        <template #reference>
                            <el-button link type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
    </div>

    <div>
        <el-pagination
                v-model:currentPage="currentPage"
                v-model:page-size="pageSize"
                :page-sizes="[5, 10, 50, 100, 200]"
                :small="small"
                :disabled="disabled"
                :background="background"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
        />
    </div>

</template>

<script>
    import request from "../../utils/request";
    import {ElMessage } from 'element-plus'

    export default {
        name: "UserManage",
        data(){
            return{
                form : {},
                // dialogVisible : false,
                search : "",
                currentPage : 1,
                pageSize : 10,
                total : 0,
                tableData : [],
            }
        },
        // 自定义指令
        directives : {
            //自动给表格加序号
            indexMethod(index) {
                return index + this.pageSize * (this.currentPage - 1) + 1;
            },
        },
        //创建时加载一次数据
        created() {
            this.load()
        },
        methods : {
            showSuccessMessage(msg){
                ElMessage.success({
                    message: msg,
                });
            },
            showFailMessage(msg){
                ElMessage.error({
                    message: msg,
                });
            }
            ,
            load(){
                request.get("/manage/article/fps", {params:{
                        pageNum : this.currentPage,
                        pageSize : this.pageSize,
                        search : this.search
                    }}).then(res =>{
                        if(res.code=="80002"){
                            return;
                        }
                        this.total = res.data.total;
                        this.tableData = res.data.records;
                })
            },
            //改变页数时加载一次数据
            handleSizeChange(){
                this.load()
            },
            //改变页码时加载一次数据
            handleCurrentChange(){
                this.load()
            },
            //
            // handleEdit(row){
            //     this.form = JSON.parse(JSON.stringify(row));
            //     this.dialogVisible = true;
            // },
            handleDel(row){
                let parse = JSON.parse(JSON.stringify(row));
                let id = parse.id;
                request.delete("/manage/article/del/"+id).then(res =>{
                    this.showSuccessMessage(res.msg)
                });
                this.load();
            },

        },

    }
</script>

<!--scope不生效-->
<style lang="css">
    .el-popper {
        font-size: 12px;
        max-width: 500px;
    }
</style>