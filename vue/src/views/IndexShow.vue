<link rel="stylesheet" href="src/assets/css/reg_login.css">
<template>
    <el-scrollbar height="100vh">
        <div class="all-page">
            <div class="page-header">
                    <img src="http://roud.top/img/selfstyle.png" style="height: 60px" v-if="styleimgshow"/>
                    <el-input
                            v-model="input_search"
                            class="w-50 m-2"
                            placeholder="想搜就搜~"
                            prefix-icon="search"
                            clearable
                            @keyup.enter="inital"
                            style="position: absolute; width: 30%; height: 30px; top:50%; left: 50%; transform: translate(-50%,-50%);"
                    />
<!--                    <span class="go_manage_btn" @click="this.$router.push(`/manage/user`)">进入后台</span>-->
                <el-dropdown class="my_workplace">
                    <span class="el-dropdown-link">
                      更多
                      <el-icon class="el-icon--right">
                        <arrow-down />
                      </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click="this.$router.push(`/manage/user`)">进入后台</el-dropdown-item>
                            <el-dropdown-item @click="dev_ing_show">进入商城</el-dropdown-item>
                            <el-dropdown-item @click="dev_ing_show">网站说明</el-dropdown-item>
                            <el-dropdown-item @click="accountInformationShow">个人信息</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
<!--                <el-menu class="el-menu-demo my_workplace" mode="horizontal" @select="handleSelect">-->
<!--                    <el-submenu index="1">-->
<!--                        <template slot="title"></template>-->
<!--                        <el-menu-item index="1-1">网站说明</el-menu-item>-->
<!--                        <el-menu-item index="1-2">进入商城</el-menu-item>-->
<!--                        <el-menu-item index="1-3">进入后台</el-menu-item>-->
<!--                    </el-submenu>-->
<!--                </el-menu>-->
            </div>
            <!--Advertisement-->
<!--            <div class="roud_Advertisement" style="position: fixed; top: 200px; left: 60px; width: 200px; height: 400px;overflow: hidden; border-radius: 8px;" v-if="true">-->
<!--                <el-icon style="position: absolute; top: 0%; right: 0%;"><CircleClose /></el-icon>-->
<!--                <div style="position: absolute; top:10%; height: 90%; width: 100%;background: green"></div>-->
<!--            </div>-->

            <div class="infinite-list-wrapper" style="overflow:visible;">
                    <ul
                        v-infinite-scroll="load"
                        class="list"
                        :infinite-scroll-disabled="disabled"
                >
                    <li v-for="(item,count) in t_data" :key="count" class="list-item">
                        <div class="common-layout" style="width: 100%; height: 100%" @click="forward(item.id)">
                            <el-container style="width: 100%; height: 100%">
                                <el-aside  style="width: 210px; overflow: hidden;">
                                    <el-image :src="item.cover" class="roud-cover"/>
                                </el-aside>
                                <el-container>
                                    <el-header style="padding-top: 16px">
                                        <p class="roud-title">{{item.title}}</p>
                                        <p id="text_author_a_time" style="margin-top:5px; color: #5b5b5b; font-size:14px">{{item.author}}&nbsp;&nbsp;&nbsp;&nbsp;{{(item.publishtime)}}</p>
                                    </el-header>
                                    <el-main class="el-main-text">
                                        <p>{{item.description}}</p>
                                        <div style="height: 25px; text-align: left">
                                            <span style="font-size: 12px; color: #AAAAAA">&ensp;标签：</span>
                                            <el-tag
                                                    v-for="tag in item.tags"
                                                    :key="tag.tagname"
                                                    type=""
                                                    effect="dark">
                                                {{ tag.tagname }}
                                            </el-tag>
                                        </div>
                                    </el-main>
                                </el-container>
                            </el-container>
                        </div>
                    </li>
                </ul>
                <p v-if="loading" style="padding: 20px 0">加载中...</p>
                <p v-if="noMore" style="padding: 20px 0">我也是有底线的~</p>
            </div>
        </div>
        <div style="position: absolute;top: 50%;left: 50%;transform: translate(-50%,-50%)">
          <AccountInformation ref="aif"></AccountInformation>
        </div>
    </el-scrollbar>
</template>

<script>
    import { computed, ref } from 'vue';
    import request from "../utils/request";
    import { ElNotification as notify } from 'element-plus'
    import AccountInformation from "@/components/AccountInformation";

    export default {
      name: "Index",
      components: {AccountInformation},
      data(){
            return{
                loading : false,
                t_data : [],
                t_data_t : [],
                total:5,
                count : 5,
                i : 2,
                pages : 0,
                tagtype : "success",
                activeIndex : '1',
                input_search : '',
                styleimgshow : true,
            }
        },
        methods:{
            forward(id){
                //打开新窗口
                window.open('/article/show?id='+id.toString(),'_blank');
                //直接在
                // this.$router.push('/article/show?id='+id.toString());
            },
            accountInformationShow(){
              this.$refs.aif.show_div();
            },
            inital(){
                request.get("/aat/fp",{params:{
                        num : 1,
                        size : 5,
                        search : this.input_search
                    }}).then(res=>{
                        this.count = 5;
                        this.i = 2;
                        this.t_data = res.data.records;
                        this.total = res.data.total;
                        this.pages = res.data.pages;
                });
            }
            ,
            load(){
                this.loading = true;
                request.get("/aat/fp",{params:{
                        num : this.i,
                        size : 5,
                        search : this.input_search
                    }}).then(res=>{
                    this.t_data_t = this.t_data.concat(res.data.records);
                });
                if(this.i<this.pages){
                    this.i = this.i+1;
                }
                setTimeout(() => {
                    this.t_data = this.t_data_t;
                    this.count = this.t_data.length;
                    this.loading = false;
                }, 1000);
            },
            _isMobile() {
                let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
                return flag;
            },
            handleResize (event) {
                this.fullWidth = document.documentElement.clientWidth;
                if (this.fullWidth < 810) {
                    this.styleimgshow = false;
                } else {
                    this.styleimgshow = true;
                }
            },
            dev_ing_show(){
                notify('该功能尚在开发中，敬请期待!');
            }
        },
        computed:{
            noMore:function () {
                return this.count >= this.total;
            },
            disabled:function () {
                return this.loading || this.noMore
            }
        },
        created() {
            if(this._isMobile()){
                this.$router.push("/errorcomputermodel");
                return;
            }
            this.fullWidth = document.documentElement.clientWidth;
            if (this.fullWidth < 810) {
                this.styleimgshow = false;
            } else {
                this.styleimgshow = true;
            }
            this.inital();
            window.addEventListener('resize', this.handleResize)
        },beforeDestroy: function () {
            window.removeEventListener('resize', this.handleResize)
        },
    }
</script>

<style scoped>
    *{
        padding: 0;
        margin: 0;
    }
    .page-header{
        list-style-type: none;
        overflow: hidden;
        background-color: #ffffff;
        position: fixed;
        top: 0;
        height: 60px;
        width: 100%;
        min-width: 800px;
        padding: 0 30px;
        z-index: 999;
    }
    .all-page{
        width: 100%;
        min-width: 800px;
        height: 100%;
        min-height: 100vh;
        background: #f4f4f4;
    }
    .infinite-list-wrapper {
        padding-top: 80px;
        height: 100%;
        text-align: center;
    }
    .infinite-list-wrapper .list {
        padding: 0;
        margin: 0;
        list-style: none;
    }

    .infinite-list-wrapper .list-item {
         display: flex;
         align-items: center;
         width: 50%;
         min-width: 600px;
         height: 160px;
         border-radius: 10px;
         margin: 0 auto;
         background: #ffffff;;
         color: black;
         cursor: pointer;
     }
    .infinite-list-wrapper .list-item + .list-item {
        margin-top: 10px;
    }
    .el-main-text{
        padding-top: 0;
        width: 100%;
        height: 60%;
        border: 1px solid transparent;
        text-overflow: ellipsis;
    }
    .el-main-text p{
        display: -webkit-box;
        height: 43px;
        color: #2d2d2d;
        line-height: 23px;
        font-size: 14px;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
        margin-bottom: 8px;
    }
    .list-item:hover {
        box-shadow: 0 16px 32px 0 rgba(48, 55, 66, 0.15);/* 盒子悬浮时阴影 */
    }
    .roud-cover{
        width: 180px;
        height: 120px;
        margin: 18px auto;
        transition: all .5s ease .1s;
    }
    .roud-title{
        font-size: 18px;
        font-weight: 500;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .infinite-list-wrapper .list-item:hover .roud-cover{
        transform: scale(1.05);
    }
    .go_manage_btn{
         position: absolute;
         color: #757a77;
         top:50%;
         right: 10%;
         transform: translate(-50%,-50%);
         cursor: pointer;
     }
    .go_manage_btn:hover{
        color: #0095ec;
    }
    .my_workplace{
        position: absolute;
        top:50%;
        right: 10%;
        transform: translate(-50%,-50%);
        cursor: pointer;
    }
    .my_workplace:hover{
        color: #0095ec;
    }

</style>