<template>
    <div style="height: 60px;line-height: 60px;border-bottom: 1px solid #ccc; display: flex">
        <div style="width: 200px; padding: 0 50px; color: cornflowerblue; font-weight: bold">后台管理系统</div>
        <div style="flex: 1"></div>
        <div style="width: 160px">
            <span style="display: inline-block;height: 40px; line-height:40px; margin: 10px 10px">
                <el-avatar :src="head_img" v-if="avatar_show"/>
                <el-avatar v-if="!avatar_show"> {{avatar}} </el-avatar>
            </span>
            <el-dropdown>
                <span class="el-dropdown-link" style="height: 60px; line-height: 60px; text-overflow: ellipsis">
                  {{userName}}<el-icon class="el-icon--right"><arrow-down /></el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
<!--                    <el-dropdown-item>个人信息</el-dropdown-item>-->
                        <el-dropdown-item @click="$router.push(`/`)">回到首页</el-dropdown-item>
                        <el-dropdown-item @click="logout">退出系统</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>
<script>
    import {ElMessage } from 'element-plus'
    import request from "../../utils/request";
    export default {
        name:"Header",
        data(){
            return{
                userName : "default",
                avatar : "",
                avatar_show: false,
                head_img : ""
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
            }
            ,
            load(){
                if (localStorage.getItem('token')) {
                    request.get("/manage/user/info", {params:{
                            token : localStorage.getItem('token'),
                        }}).then(res =>{
                        if(res.code=="80002"){
                            this.showFailMessage(res.msg);
                            // let t = 10;
                            // this.timer = setInterval(()=>{
                            //     if(t > 0){
                            //         t--;
                            //     }else{
                            //         clearInterval(this.timer);
                            //         this.timer = null;
                            //         this.$router.push("/index/login");
                            //     }
                            // },100);
                            this.$router.push("/index/login");
                            return;
                        }
                        if(res.data.name!=null && (res.data.name).length>5){
                          this.userName = (res.data.name).substring(0,4)+"...";
                        }else {
                          this.userName = res.data.name;
                        }
                        this.avatar = this.userName.substring(0,2);
                        var img = res.data.imgurl;
                        if(img){
                          this.avatar_show = true;
                          this.head_img = "/api/img/show/"+img;
                        }else {
                          this.avatar_show = false;
                        }
                    })
                }else{
                    this.showFailMessage("请先登录！");
                    let t = 10;
                    this.timer = setInterval(()=>{
                        if(t > 0){
                            t--;
                        }else{
                            clearInterval(this.timer);
                            this.timer = null;
                            this.$router.push("/index/login");
                        }
                    },100);
                    this.$router.push("/index/login");
                }
            },
            logout() {
              request.get("/login/quit", {params:{
                  token : localStorage.getItem('token'),
                }}).then(res =>{
                    if(res.code=="200"){
                      localStorage.clear();
                      this.$router.push("/index/login"); //跳回登录地址
                    }else {
                      this.showFailMessage(res.msg);
                    }
                })
            },
            _isMobile() {
                let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
                return flag;
            }
        },
        created() {
            if(this._isMobile()){
                this.$router.push("/errorcomputermodel");
                return;
            }
            this.load();
        }
    }
</script>
<style scoped>
    .el-avatar {
        --el-avatar-bg-color: #409eff;
    }
</style>