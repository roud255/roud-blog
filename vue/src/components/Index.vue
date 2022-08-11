<link rel="stylesheet" href="../assets/css/reg_login.css">
<template>
    <el-scrollbar height="100vh">
        <div class="all-page">
            <div class="page-header">
                <img src="../assets/img/selfstyle.png" style="height: 60px"/>
            </div>
            <div class="infinite-list-wrapper" style="overflow: auto">
                <ul
                        v-infinite-scroll="load"
                        class="list"
                        :infinite-scroll-disabled="disabled"
                >
                    <li v-for="i in count" :key="i" class="list-item">
                        <div class="common-layout" style="width: 100%; height: 100%">
                            <el-container style="width: 100%; height: 100%">
                                <el-aside width="200px">
                                    <el-image :src="src" style="width: 120px; height: 120px; margin: 13px auto"/>
                                </el-aside>
                                <el-container>
                                    <el-header style="padding-top: 16px">
                                        <h5>我是一个标题</h5>
                                        <p style="color: #9B9B9B; font-size: 15px; font-weight: 300">{{author}}{{now_time}}</p>
                                    </el-header>
                                    <el-main class="el-main-text">
                                        <p>问题：通过接口获取的pdf文件流，想要实现下载或预览，结果每次打开的都是空的pdf 解决方法：MD原来是我一直开了mock把接口拦截了，难怪我设置成什么样都没有用！！！把mock给注释掉！！！问题：通过接口获取的pdf文件流，想要实现下载或预览，结果每次打开的都是空的pdf 解决方法：MD原来是我一直开了mock把接口拦截了，难怪我设置成什么样都没有用！！！把mock给注释掉！！！问题：通过接口获取的pdf文件流，想要实现下载或预览，结果每次打开的都是空的pdf 解决方法：MD原来是我一直开了mock把接口拦截了，难怪我设置成什么样都没有用！！！把mock给注释掉！！！</p>
                                    </el-main>
                                </el-container>
                            </el-container>
                        </div>
                    </li>
                </ul>
                <p v-if="loading" style="margin: 20px 0">加载中...</p>
                <p v-if="noMore" style="margin: 20px 0">我也是有底线的~</p>
            </div>
        </div>
    </el-scrollbar>
</template>

<script>
    import { computed, ref } from 'vue';
    export default {
        name: "Index",
        data(){
            return{
                count : 10,
                loading : false,
                src :
                    'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
                author : "roud     ",
                now_time : "2022-7-14 14:00:00",
            }
        },
        methods:{
            load(){
                this.loading = true;
                setTimeout(() => {
                    this.count += 2;
                    this.loading = false
                }, 1500)
            }
        },
        computed:{
            noMore:function () {
                return this.count >= 20;
            },
            disabled:function () {
                return this.loading || this.noMore
            }
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
        background: #f4f4f4;
    }
    .infinite-list-wrapper {
        padding-top: 100px;
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
        height: 150px;
        border-radius: 10px;
        margin: 0 auto;
        background: #ffffff;;
        color: black;
    }
    /*#f4f4f4 #ffffff*/
    .infinite-list-wrapper .list-item + .list-item {
        margin-top: 10px;
    }
    .el-main-text{
        display: -webkit-box;
        padding-top: 0;
        width: 100%;
        height: 50%;
        border: 1px solid transparent;
        overflow: hidden;
        text-overflow: ellipsis;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
    }
    .el-main-text p{
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        -webkit-box-orient: vertical;
    }
    .list-item:hover {
        box-shadow: 0 16px 32px 0 rgba(48, 55, 66, 0.15);/* 盒子悬浮时阴影 */
    }
</style>