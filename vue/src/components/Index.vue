<link rel="stylesheet" href="../assets/css/reg_login.css">
<template>
    <el-scrollbar height="100vh">
        <div class="all-page">
            <div class="page-header">
                <img src="../assets/img/selfstyle.png" style="height: 60px"/>
            </div>
            <div class="infinite-list-wrapper" style="overflow:visible;">
                    <ul
                        v-infinite-scroll="load"
                        class="list"
                        :infinite-scroll-disabled="disabled"
                >
                    <li v-for="(item,count) in t_data" :key="count" class="list-item">
                        <div class="common-layout" style="width: 100%; height: 100%">
                            <el-container style="width: 100%; height: 100%">
                                <el-aside  style="width: 210px; overflow: hidden;">
                                    <el-image :src="item.cover" class="roud-cover"/>
                                </el-aside>
                                <el-container>
                                    <el-header style="padding-top: 16px">
                                        <h4 class="roud-title">{{item.title}}</h4>
                                        <p id="text_author_a_time" style="margin-top:5px; color: #AAA; font-size: 15px;">{{item.author}}&nbsp;&nbsp;&nbsp;&nbsp;{{(item.publishtime).split(" ")[0]}}</p>
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
    </el-scrollbar>
</template>

<script>
    import { computed, ref } from 'vue';
    import request from "../utils/request";
    export default {
        name: "Index",
        data(){
            return{
                loading : false,
                t_data : [],
                t_data_t : [],
                total:5,
                count : 5,
                i : 2,
                pages : 0,
                goload: false
            }
        },
        methods:{
            inital(){
                request.get("/aat/page",{params:{
                        num : 1,
                        size : 5
                    }}).then(res=>{
                        this.t_data = res.data.records;
                        this.total = res.data.total;
                        this.pages = res.data.pages;
                });
            }
            ,
            load(){
                this.loading = true;
                request.get("/aat/page",{params:{
                        num : this.i,
                        size : 5
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
                }, 2000);
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
            this.inital();
        }
    }
</script>

<style scoped>
    .page-header{
        list-style-type: none;
        overflow: hidden;
        background-color: #ffffff;
        position: fixed;
        top: 0;
        height: 60px;
        width: 100%;
        padding: 0 30px;
        z-index: 999;
    }
    .all-page{
        width: 100%;
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
         /*justify-content: center;*/
         width: 50%;
         min-width: 600px;
         height: 160px;
         border-radius: 10px;
         margin: 0 auto;
         background: #ffffff;;
         color: black;
         cursor: pointer;
     }

    /*.infinite-list-wrapper .list-item:hover {*/
    /*    color: #3f89ec;*/
    /*}*/
    /*.infinite-list-wrapper .list-item:hover #text_author_a_time{*/
    /*    color: #3f89ec !important;*/
    /*}*/
    /*.infinite-list-wrapper .list-item:hover .el-main-text p{*/
    /*    color: #3f89ec;*/
    /*}*/

    /*#f4f4f4 #ffffff*/
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
        font-weight: 600;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .infinite-list-wrapper .list-item:hover .roud-cover{
        transform: scale(1.05);
    }
</style>