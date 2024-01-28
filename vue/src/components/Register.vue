<template>
    <div class="reg reg-header">
        <h3 class="text">欢迎注册</h3>
        <p class="text">已有账号？<router-link to="/index/login">登录</router-link></p>
    </div>
    <el-form :model="reg_form" label-width="80px" style="margin-top: 30px" :rules="rules" ref="reg_form">
        <el-form-item label="用户名" size="large" prop="nickname">
            <el-input v-model="reg_form.nickname" placeholder="请设置登录用户名" />
        </el-form-item>
        <p class="emptyLine"></p>
        <el-form-item label="邮　箱" size="large" prop="email">
            <el-input v-model="reg_form.email" placeholder="可用于登录和找回密码" />
        </el-form-item>
        <p class="emptyLine"></p>
        <el-form-item label="密　码" size="large" prop="password">
            <el-input
                    v-model="reg_form.password"
                    type="password"
                    placeholder="请设置登录密码"
                    show-password
            />
        </el-form-item>
        <p class="emptyLine"></p>
        <el-form-item label="验证码" size="large" prop="vertifycode">
            <el-input v-model="reg_form.vertifycode" placeholder="请输入验证码" style="width: 180px; margin-right: 10px;"  @keydown.enter="onSubmit"/>
            <el-button :disabled="checkCodeBtn.disabled" @click="getCode">{{ checkCodeBtn.text }}</el-button>
        </el-form-item>
        <p class="emptyLine"></p>
        <el-form-item label-width="5%">
            <el-button type="submitbtn" @click="onSubmit">注册</el-button>
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
        name: "Register",

        data(){
            return{
                notice : false,
                reg_form : {
                },
                rules: {
                    //邮箱校验规则
                    email: [
                        { required: true, message: "请输入邮箱", trigger: "blur" },
                        { pattern:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/g, message: "请输入正确的邮箱", trigger: "blur"}
                    ],
                    nickname:[
                        { required: true, message: "请输入昵称", trigger: "blur" },
                        { pattern:/^(\w|[\u4e00-\u9fa5]){4,8}$/g, message: "昵称由4-8位字母、数字、下划线或汉字组成", trigger: "blur"}
                    ],
                    password:[
                        { required: true, message: "请输入密码", trigger: "blur" },
                        { pattern:/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,12}$/g, message: "密码必须包含大小写字母和数字的组合，不能使用特殊字符", trigger: "blur"}
                    ],
                    vertifycode:[
                        { required: true, message: "请输入验证码", trigger: "blur" },
                        { pattern:/^\d{4}$/g, message: "验证码为4位数字的组合", trigger: "blur"}
                    ],
                },
                checkCodeBtn : {
                    text: '获取验证码',
                    loading: false,
                    disabled: false,
                    duration: 60,
                    timer: null},

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
            getUserEmailVertifyCode(){
                let ca = "roudblog.mail.";
                let userEmailVertifyCode = this.reg_form.email+ca+this.getCurrentTime();
                return userEmailVertifyCode;
            },
            getCode(){
                if(this.reg_form.email==""||this.reg_form.email==null){
                    this.showWarningMessage("请输入邮箱!");
                    return;
                }
                let regExp = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
                if (!regExp.test(this.reg_form.email)) {
                    this.showFailMessage("邮箱格式不正确");
                    return;
                }else{
                    if (this.checkCodeBtn.duration !== 10) {
                        this.checkCodeBtn.disabled = true;
                    }
                    //清除一次定时器，防止重复点击
                    clearInterval(this.checkCodeBtn.timer);
                    // 定义定时器
                    request.post("/reg/code",{
                        email: this.reg_form.email,
                        userVertifyCode: md5(this.getUserEmailVertifyCode())
                    }).then(res=>{
                        let resCode = res.code;
                        if(resCode=="200"){
                            this.showSuccessMessage(res.msg);
                        }else {
                            this.showFailMessage(res.msg);
                        }

                    });
                    this.checkCodeBtn.timer = setInterval(() => {
                        const tmp = this.checkCodeBtn.duration--;
                        this.checkCodeBtn.text = `${tmp}秒`;
                        if (tmp <= 0) {
                            // 清除掉定时器
                            clearInterval(this.checkCodeBtn.timer);
                            this.checkCodeBtn.duration = 60;
                            this.checkCodeBtn.text = '重新获取';
                            // 设置按钮可以单击
                            this.checkCodeBtn.disabled = false;
                        }
                    }, 1000)

                }

            },
            onSubmit(){
                this.$refs['reg_form'].validate(valid => {
                    if (valid) {
                        if(this.notice){
                            request.post("/reg/do",this.reg_form).then(res =>{
                                if(res.code=="200"){
                                    this.showSuccessMessage(res.msg);
                                    this.$router.push("/index/login");
                                }else{
                                    this.showFailMessage(res.msg);
                                }
                            });
                        }else {
                            this.showWarningMessage("请阅读并勾选下方协议");
                        }
                    } else {
                        this.showFailMessage("提交失败");
                    }
                });
            },

        }
    }
</script>

<style scoped>
    @import '../assets/css/reg_login.css';
</style>