<template>
    <div style="position: relative;min-height:100vh;min-width: 758px;overflow: hidden;">
        <div class="bg-container">
        </div>
<!--        <el-page-header @back="goBack" style="color:#ffffff; font-size: 16px !important; font-weight: 200 !important; margin-left: 5%" title="返回首页">-->
<!--          <template #content>-->
<!--            <span style="font-size: 16px; color: #ffffff; font-weight: 200"> 登录&注册 </span>-->
<!--          </template>-->
<!--        </el-page-header>-->
        <span style="display: inline-block;position: absolute; top:5%;left: 5%; height:16px;line-height:16px;vertical-align:middle; cursor: pointer" @click="goBack">
          <el-icon color="white" style="float: left"><ArrowLeftBold /></el-icon>
          <span style="display: inline-block; color: white; font-size: 16px; float: left">返回</span>
        </span>

        <div style="position: absolute;
                    width: 480px;
                    height: 632px;
                    top: 50%;
                    left: 50%;
                    transform: translate(-50%, -50%);
                    background-color: rgba(255,255,255,.9);
                    border-radius: 16px;
                    overflow: hidden;
                    padding: 50px 50px;">
            <router-view></router-view>
        </div>
        <p class="copyright">Copyright © 2022 roud.top. All rights reserved</p>
    </div>

</template>

<script>

    export default {
        name: "Index",
        components:{

        },
        data(){

        },
        methods:{
            goBack(){
                this.$router.push("/")
            }
        }
    }

</script>

<style scoped>
    @import '../assets/css/reg_login.css';
</style>