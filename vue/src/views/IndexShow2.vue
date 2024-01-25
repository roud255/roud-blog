<link rel="stylesheet" href="src/assets/css/reg_login.css">
<template>
    <el-scrollbar height="100vh">
        <div class="all-page">
            <div class="page-header">
                    <img src="http://qny.roud.top/img/selfstyle.png" style="height: 60px" v-if="styleimgshow"/>
              <div class="mt-4" style="position: absolute; width: 30%; height: 30px; top:50%; left: 50%; transform: translate(-50%,-50%);">
                <el-input
                    v-model="input_search"
                    placeholder="想搜就搜~"
                    class="input-with-select"
                    @keyup.enter="inital"
                >
                  <template #prepend>
                    <el-select v-model="select_s" placeholder="选择" style="width: 80px">
                      <el-option label="标题" value="1" />
                      <el-option label="标签" value="2" />
                    </el-select>
                  </template>
                  <template #append>
                    <el-button @click="inital"><el-icon><Search /></el-icon></el-button>
                  </template>
                </el-input>
              </div>
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
            </div>

            <div class="infinite-list-wrapper" style="overflow:visible;" v-loading="inLoading">
              <ul>
                    <li v-for="(item,count) in t_data" :key="count" class="list-item" >
                        <div class="common-layout" style="width: 100%; height: 100%" @click="forward(item.id, item.self)">
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
                                            <span style="font-size: 12px; color: #AAAAAA; float: right"><el-icon><View /></el-icon>&nbsp;{{item.viewsnum}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<el-icon><ChatDotRound /></el-icon>&nbsp;{{item.commentsnum}}</span>
                                        </div>
                                    </el-main>
                                </el-container>
                            </el-container>
                            <span class="self-span" v-if="item.self">专属</span>
                        </div>
                    </li>
                </ul>
                <div class="bottom-box">
                  <el-pagination background layout="prev, pager, next" :total="total" v-model:currentPage="currentPage" v-model:page-size="psize" @current-change="handleCurrentChange"/>
                  <div class="copyrightidx bottom-box-i-c ">
                    <p>Copyright © 2024 roud.top All rights reserved.</p>
                    <p @click="goToTargetPage('https://beian.miit.gov.cn/')" class="p2">粤ICP备2024161340号</p>
                  </div>
                </div>
            </div>
        </div>
        <div>
          <SelfArticleValidate ref="sav"></SelfArticleValidate>
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
    import SelfArticleValidate from "@/components/SelfArticleValidate";

    export default {
      name: "IndexShow2",
      components: {SelfArticleValidate, AccountInformation},
      data(){
            return{
                loading : false,
                t_data : [],
                t_data_t : [],
                pages : 0,
                tagtype : "success",
                activeIndex : '1',
                input_search : '',
                styleimgshow : true,
                select_s: '1',
                total: 0,
                psize: 10,
                currentPage: 1,
                inLoading: false,
            }
        },
        methods:{
            goToTargetPage(url) {
              // 使用$router对象的push方法进行页面跳转
              // this.$router.push(url);
              window.open(url,'_blank');
            },
            forward(id, self){
                if(self){
                  // this.selfArticleValidate();
                  this.selfArticleValidateComplete(id)
                }else {
                  //打开新窗口
                  window.open('/article/show?id='+id.toString(),'_blank');
                }
                //直接在当前页面打开
                // this.$router.push('/article/show?id='+id.toString());
            },
            accountInformationShow(){
              this.$refs.aif.show_div();
            },
            selfArticleValidateComplete(id){
              this.$refs.sav.show_div2(id);
            },
            inital(){
                this.inLoading = true
                request.get("/aat/fp/public",{params:{
                        num : this.currentPage,
                        size : this.psize,
                        type : this.select_s,
                        search : this.input_search
                    }}).then(res=>{
                      if(res.code === 200){
                          this.t_data = res.data.records;
                          this.pages = res.data.pages;
                          this.total = res.data.total;
                          this.inLoading = false;
                      }
                });
            },
          handleCurrentChange(){
            this.inital()
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
        /*padding: 0;*/
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
        position: relative;
        padding-top: 80px;
        padding-bottom: 100px;
        height: 100%;
        text-align: center;
        min-height: 100vh;
    }
    .infinite-list-wrapper .list {
        padding: 0;
        margin: 0;
        list-style: none;
    }

    .infinite-list-wrapper .list-item {
         display: flex;
         position: relative;
         overflow: hidden;
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
    .infinite-list-wrapper .list-item .self-span{
      background: red;
      color: white;
      width: 50px;
      bottom: 0;
      right: 0;
      position: absolute;
      border-top-left-radius: 10px
    }
    .infinite-list-wrapper .list-item + .list-item {
        margin-top: 10px;
    }
    .infinite-list-wrapper .list-item:hover .roud-cover{
      transform: scale(1.05);
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
    .bottom-box{
      position: absolute;
      /*position: sticky;*/
      bottom: 0;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      width: 100%;
      margin: 0 auto;
    }
    .bottom-box-i-c{
      width: 100%;
      text-align: center;
      margin: 10px 0;
    }
    .bottom-box-i-c p{
      display: inline-block;
      margin: 0 10px;
    }
    .bottom-box-i-c .p2{
      cursor: pointer;
    }
    .bottom-box-i-c .p2:hover{
      color: #0095ec;
    }
    .copyrightidx{
      display: block;
      /*color: #0095ec;*/
      color: #757a77;
      font-size: 16px;
      font-weight: 100;
    }

</style>