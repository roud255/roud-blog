<template>
    <div class="reg reg-header">
        <h3 class="text">欢迎登录</h3>
        <p class="text">没有账号？<router-link to="/index/register">注册</router-link></p>
    </div>
    <el-form :model="login_form" label-width="80px" style="margin-top: 50px" :rules="rules" ref="login_form">
        <el-form-item label="邮　箱" size="large" prop="email">
            <el-input v-model="login_form.email" placeholder="请输入注册时使用的邮箱" />
        </el-form-item>
        <p class="emptyLine"></p>
        <el-form-item label="密　码" size="large" prop="password">
            <el-input
                    v-model="login_form.password"
                    type="password"
                    placeholder="请输入注册时的密码"
                    show-password
            />
        </el-form-item>
        <p class="emptyLine"></p>
        <el-form-item label="验证码" size="large" prop="vertifycode">
            <el-input v-model="login_form.vertifycode" placeholder="请输入图片验证码" style="width: 180px; margin-right: 10px;" @keydown.enter="onSubmit"/>
<!--            <img name="verifyCode" id="login-verifyCodeImg" src=>-->
            <el-image
                    :src="imageUrl"
                    style="width: 108px;height: 36px;vertical-align: middle;float:right;cursor: pointer;"
                    fit="cover"
                    @click="loadImage"
                    alt="验证码"
                    title="验证码"
            ></el-image>
        </el-form-item>
        <p class="emptyLine" style="height: 61px"></p>
        <el-form-item label-width="5%">
            <!--自定义提交按钮-->
            <el-button type="submitbtn" @click="onSubmit">登录</el-button>
        </el-form-item>
        <el-checkbox v-model="notice" label="" size="large" style="margin-left: 50px;">
            <p style="display: inline-block;font-size: 12px;">阅读并接受<a href="#" class="notice_a">《用户协议》</a>及<a href="#" class="notice_a">《隐私权保护声明》</a></p>
        </el-checkbox>
    </el-form>
</template>

<script>
    import {ElMessage} from "element-plus";
    import request from "../utils/request";
    import md5 from 'js-md5';
    export default {
        name: "Login",

        data(){
            return{
                imageUrl : '/api/login',
                notice: false,
                login_form : {
                },
                flagCode : "",
                rules: {
                    //邮箱校验规则
                    email: [
                        { required: true, message: "请输入邮箱", trigger: "blur" },
                        { pattern:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/g, message: "请输入正确的邮箱", trigger: "blur"}
                    ],
                    password:[
                        { required: true, message: "请输入密码", trigger: "blur" },
                        { pattern:/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,12}$/g, message: "密码必须包含大小写字母和数字的组合，不能使用特殊字符", trigger: "blur"}
                    ],
                    vertifycode:[
                        { required: true, message: "请输入验证码", trigger: "blur" },
                        { pattern:/^[A-Za-z0-9]{4}$/g, message: "验证码为4位字母、数字的组合（不区分大小写）", trigger: "blur"}
                    ],
                    notice: [
                        { required: true, message: "请勾选协议",trigger: "blur" },
                    ],
                },

            }
        },
        created() {
            if (localStorage.getItem('token')) {
                request.get("/manage/user/info", {params:{
                        token : localStorage.getItem('token'),
                    }}).then(res =>{
                    if(res.code!="80002"){
                        this.showWarningMessage("已经存在登录账户！");
                        this.$router.push("/manage/user");
                    }
                })
            }
        },
        methods:{
            showSuccessMessage(msg){
                ElMessage.success({
                    message: msg,
                });
            },
            showFailMessage(msg){
                ElMessage.error({
                    message: msg,
                });
            },
            showWarningMessage(msg){
                ElMessage.warning({
                    message: msg,
                });
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

                //当前时间
                var curTime = year + "-" + month + "-" + day
                    + "-" + hour;
                return curTime;
            },
             getRandCode(){
               const array = new Uint32Array(4);
               window.crypto.getRandomValues(array);
               let uuid = '';
               array.forEach((num) => {
                 uuid += num.toString(16).padStart(8, '0');
               });
               return uuid;
             },

            onSubmit(){
                this.login_form.flag = this.flagCode
                this.$refs['login_form'].validate(valid => {
                    if (valid) {
                        if(this.notice){
                            request.post("/login",this.login_form).then(res =>{
                                if(res.code!="200"){
                                    this.showWarningMessage(res.msg);
                                }else {
                                    localStorage.setItem('token',res.data.token);
                                    this.showSuccessMessage("登录成功！");
                                    this.$router.push("/manage/user");
                                }
                            });
                        }else {
                            this.showWarningMessage("请阅读并勾选下方协议");
                        }
                    } else {
                        this.showFailMessage("提交失败，请正确填写各项信息");
                    }
                });
            },
            loadImage(){
                let flag = ""+this.getRandCode()+Date.now()
                this.flagCode = flag
                this.imageUrl = "/api/login?flag="+flag;
            },
        }
    }
</script>
<style scoped>
    @import '../assets/css/reg_login.css';
</style>