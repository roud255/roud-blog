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
            <el-table-column prop="nickname" label="昵称" width="180" />
            <el-table-column prop="phonenumber" label="邮箱" width="180"/>
            <el-table-column prop="password" label="密码"/>
            <el-table-column prop="registertime" label="注册时间" width="180" />
            <el-table-column prop="type" label="用户类型" width="100"/>
            <el-table-column prop="power" label="权限" width="100"/>
            <el-table-column fixed="right" label="操作" width="120">
                <template #default="scope">
                    <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>
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

        <el-dialog v-model="dialogVisible" title="新增用户" width="500px" :before-close="handleClose" center>
            <el-form :model="form" :rules="rules" ref="form" style="padding: 0 30px" class="demo-ruleForm">
                <el-form-item label="昵　　称" prop="nickname">
                    <el-input v-model="form.nickname" />
                </el-form-item>
                <el-form-item label="邮　　箱" prop="phonenumber">
                    <el-input v-model="form.phonenumber"/>
                </el-form-item>
                <el-form-item label="密　　码" prop="password">
                    <el-input v-model="form.password" />
                </el-form-item>
                <el-form-item label="用户类型" prop="type">
                    <el-radio-group v-model="form.type">
                        <el-radio label="0">管理员</el-radio>
                        <el-radio label="1">普通用户</el-radio>
                        <el-radio label="2">演示用户</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="权　　限" prop="power">
<!--                    <el-select v-model="form.power" placeholder="输入用户权限">-->
<!--                        <el-option label="管理" value="0" />-->
<!--                        <el-option label="用户" value="1" />-->
<!--                        <el-option label="演示" value="2" />-->
<!--                    </el-select>-->
                  <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange()">Check all</el-checkbox>
                  <el-checkbox-group v-model="checkedItems" @change="handleCheckedItemsChange()">
                    <el-checkbox v-for="item in items" :key="item" :label="item">{{item }}</el-checkbox>
                  </el-checkbox-group>
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
        name: "UserManage",
        data(){
            return{
                form : {},
                dialogVisible : false,
                search : "",
                currentPage : 1,
                pageSize : 10,
                total : 0,
                tableData : [],
                // 权限多选框
                checkAll : false,
                isIndeterminate : true,
                checkedItems : ['Shanghai', 'Beijing'],
                items : ['Shanghai', 'Beijing', 'Guangzhou', 'Shenzhen'],
                rules: {
                    //邮箱校验规则
                    phonenumber: [
                        { required: true, message: "请输入邮箱", trigger: "blur" },
                        { pattern:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/g, message: "请输入正确的邮箱", trigger: "blur"}
                    ],
                    nickname:[
                        { required: true, message: "请输入昵称", trigger: "blur" },
                        { pattern:/^\w{4,8}$/g, message: "昵称由4-8位字母、数字或下划线组成", trigger: "blur"}
                    ],
                    password:[
                        { required: true, message: "请输入密码", trigger: "blur" },
                        { pattern:/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,12}$/g, message: "密码必须包含大小写字母和数字的组合，不能使用特殊字符", trigger: "blur"}
                    ],
                    type:[
                        { required: true, message: "必填", trigger: 'change',},
                    ]
                    // power:[
                    //     { required: true, message: "必填", trigger: "change" },
                    // ]
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
            handleCheckAllChange(){
              if(this.checkAll){
                this.checkedItems = this.items
                this.checkAll = true
                this.isIndeterminate = false
              }else {
                this.checkAll = false
                this.checkedItems = []
                this.isIndeterminate = false
              }
            },
            handleCheckedItemsChange(){
              const checkedCount = this.checkedItems.length
              this.checkAll = checkedCount === this.items.length
              this.isIndeterminate = checkedCount > 0 && checkedCount < this.items.length
            },
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
                request.get("/manage/user/select", {params:{
                    pageNum : this.currentPage,
                    pageSize : this.pageSize,
                    search : this.search
                }}).then(res =>{
                    if(res.code != "1"){
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
            //1、获取当前时间子方法
            repair(i){
                if (i >= 0 && i <= 9) {
                    return "0" + i;
                } else {
                    return i;
                }
            },
            //2、获取当前方法，格式"yyyy-MM-dd HH:mm:ss"
            getCurrentTime() {
                var date = new Date();//当前时间
                var year = date.getFullYear();//返回指定日期的年份
                var month = this.repair(date.getMonth() + 1);//月
                var day = this.repair(date.getDate());//日
                var hour = this.repair(date.getHours());//时
                var minute = this.repair(date.getMinutes());//分
                var second = this.repair(date.getSeconds());//秒

                //当前时间
                var curTime = year + "-" + month + "-" + day
                    + " " + hour + ":" + minute + ":" + second;
                return curTime;
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
                            request.put("/manage/user/update", this.form).then(res =>{
                                if(res.code != "1"){
                                    this.showFailMessage(res.msg);
                                    return;
                                }
                                this.showSuccessMessage(res.msg)
                            });
                            this.dialogVisible = false;
                            this.form = {};
                            this.load();
                        }else{//新增
                            this.form["id"] = (new Date()).getTime();
                            this.form["registertime"] = this.getCurrentTime();
                            request.post("/manage/user/add", this.form).then(res =>{
                              if(res.code != "1"){
                                this.showFailMessage(res.msg);
                                return;
                              }
                              this.showSuccessMessage(res.msg)
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
                request.delete("/manage/user/del/"+id).then(res =>{
                    if(res.code != "1"){
                        this.showFailMessage(res.msg);
                        return;
                    }
                  this.showSuccessMessage(res.msg)
                });
                this.load();
            },

        },

    }
</script>

<style scoped>

</style>