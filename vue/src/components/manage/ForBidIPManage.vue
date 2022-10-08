<template>
    <div style="margin: 10px 0;">
        <el-button type="primary" @click="add">新增</el-button>
        <el-button type="primary" @click="load">刷新</el-button>
    </div>

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
            <el-table-column prop="ip" label="IP" width="180" />
            <el-table-column prop="reason" label="原因" width="180px"/>
            <el-table-column prop="time" label="时间"/>
            <el-table-column fixed="right" label="操作" width="120">
                <template #default="scope">
                    <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-popconfirm title="确定移除吗?" @confirm="handleDel(scope.row)">
                        <template #reference>
                            <el-button link type="danger">移除</el-button>
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

        <el-dialog v-model="dialogVisible" title="新增小黑屋用户" width="500px" :before-close="handleClose" center>
            <el-form :model="form" :rules="rules" ref="form" style="padding: 0 30px" class="demo-ruleForm">
                <el-form-item label="I　P" prop="ip">
                    <el-input v-model="form.ip" />
                </el-form-item>
                <el-form-item label="原因" prop="reason">
                    <el-input v-model="form.reason"/>
                </el-form-item>
                <el-form-item label="时间" prop="time">
                    <el-date-picker v-model="form.time" type="datetime" placeholder="选择日期和时间"/>
                </el-form-item>
            </el-form>

            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="cancelsubmit">取消</el-button>
                    <el-button type="primary" @click="save">确认</el-button>
                </span>
            </template>
        </el-dialog>
    </div>

</template>

<script>
    import request from "../../utils/request";
    import {ElMessage } from 'element-plus'

    export default {
        name: "ForBidIPManage",
        data(){
            return{
                form : {},
                dialogVisible : false,
                search : "",
                currentPage : 1,
                pageSize : 10,
                total : 0,
                tableData : [],
                rules: {
                    //ip校验规则
                    ip: [
                        { required: true, message: "请输入ip地址", trigger: "blur" },
                        { pattern:/^((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/g, message: "请输入正确格式的ip", trigger: "blur"}
                    ],
                    reason:[
                        { required: true, message: "请输入拉黑原因", trigger: "blur" },
                    ],
                    time:[
                        { required: true, message: "请选择时间", trigger: "blur" },
                    ]
                }
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
                request.get("/manage/ip/select", {params:{
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
            //3、新增user
            add(){
                this.dialogVisible = true;
                this.form = {};
            },
            //3、保存user
            save(){
                this.$refs['form'].validate(valid => {
                    if (valid) {
                        if(this.form.id){//更新
                            request.put("/manage/ip/update", this.form).then(res =>{
                                this.showSuccessMessage(res.msg)
                            });
                            this.dialogVisible = false;
                            this.form = {};
                            this.load();
                        }else{//新增
                            this.form["id"] = (new Date()).getTime();
                            request.post("/manage/ip/add", this.form).then(res =>{
                                if(res.code=="1"){
                                    this.showSuccessMessage(res.msg);
                                }else{
                                    this.showFailMessage(res.msg);
                                }
                            });
                            this.dialogVisible = false;
                            this.form = {};
                            this.load();
                        }
                    } else {
                        this.showFailMessage("提交失败");
                    }
                });

            },
            cancelsubmit(){
                this.dialogVisible = false;
            },
            handleEdit(row){
                this.form = JSON.parse(JSON.stringify(row));
                this.dialogVisible = true;
            },
            handleDel(row){
                let parse = JSON.parse(JSON.stringify(row));
                let id = parse.id;
                request.delete("/manage/ip/del/"+id).then(res =>{
                    this.showSuccessMessage(res.msg)
                });
                this.load();
            },

        },

    }
</script>

<style scoped>

</style>