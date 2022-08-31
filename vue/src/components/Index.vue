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
<!--                    <li v-for="i in count" :key="i" class="list-item">-->
<!--                        <div class="common-layout" style="width: 100%; height: 100%">-->
<!--                            <el-container style="width: 100%; height: 100%">-->
<!--                                <el-aside width="200px">-->
<!--                                    <el-image :src="src" style="width: 120px; height: 120px; margin: 13px auto"/>-->
<!--                                </el-aside>-->
<!--                                <el-container>-->
<!--                                    <el-header style="padding-top: 16px">-->
<!--                                        <h5>我是一个标题</h5>-->
<!--                                        <p id="text_author_a_time" style="margin-top:5px; color: #9B9B9B; font-size: 15px; font-weight: 300">{{author}}{{now_time}}</p>-->
<!--                                    </el-header>-->
<!--                                    <el-main class="el-main-text">-->
<!--                                        <p>我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容我是测试描述内容</p>-->
<!--                                    </el-main>-->
<!--                                </el-container>-->
<!--                            </el-container>-->
<!--                        </div>-->
<!--                    </li>-->
                    <li v-for="(item,count) in t_data" :key="count" class="list-item">
                        <div class="common-layout" style="width: 100%; height: 100%">
                            <el-container style="width: 100%; height: 100%">
                                <el-aside width="200px">
                                    <el-image :src="src" style="width: 120px; height: 120px; margin: 13px auto"/>
                                </el-aside>
                                <el-container>
                                    <el-header style="padding-top: 16px">
                                        <h5>{{item.title}}</h5>
                                        <p id="text_author_a_time" style="margin-top:5px; color: #9B9B9B; font-size: 15px; font-weight: 300">{{item.author}}{{item.time}}</p>
                                    </el-header>
                                    <el-main class="el-main-text">
                                        <p>{{item.description}}</p>
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
                loading : false,
                src :
                    'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
                t_data : [
                    {
                        title:'这是第一个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },
                    {
                        title:'这是第二个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },
                    {
                        title:'这是第三个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },
                    {
                        title:'这是第四个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },
                    {
                        title:'这是第五个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },
                ],
                t_data_t :[{
                    title:'这是第一个标题',
                    author: 'Tom',
                    time: '2022-7-14 14:00:00',
                    description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                },
                    {
                        title:'这是第二个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },
                    {
                        title:'这是第三个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },
                    {
                        title:'这是第四个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },
                    {
                        title:'这是第五个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },
                    {
                        title:'这是第六个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },
                    {
                        title:'这是第七个标题',
                        author: 'Tom',
                        time: '2022-7-14 14:00:00',
                        description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                    },],
                count : 5,
            }
        },
        methods:{
            load(){
                this.loading = true;
                setTimeout(() => {
                    this.t_data = this.t_data_t;
                    this.t_data_t = [
                        {
                            title:'这是第一个标题',
                            author: 'Tom',
                            time: '2022-7-14 14:00:00',
                            description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                        },
                        {
                            title:'这是第二个标题',
                            author: 'Tom',
                            time: '2022-7-14 14:00:00',
                            description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                        },
                        {
                            title:'这是第三个标题',
                            author: 'Tom',
                            time: '2022-7-14 14:00:00',
                            description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                        },
                        {
                            title:'这是第四个标题',
                            author: 'Tom',
                            time: '2022-7-14 14:00:00',
                            description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                        },
                        {
                            title:'这是第五个标题',
                            author: 'Tom',
                            time: '2022-7-14 14:00:00',
                            description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                        },
                        {
                            title:'这是第六个标题',
                            author: 'Tom',
                            time: '2022-7-14 14:00:00',
                            description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                        },
                        {
                            title:'这是第七个标题',
                            author: 'Tom',
                            time: '2022-7-14 14:00:00',
                            description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                        },
                        {
                            title:'这是第八个标题',
                            author: 'Tom',
                            time: '2022-7-14 14:00:00',
                            description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                        },
                        {
                            title:'这是第九个标题',
                            author: 'Tom',
                            time: '2022-7-14 14:00:00',
                            description: '测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容',
                        },
                        ];
                        this.count = this.t_data.length;
                        this.loading = false
                }, 1500)
            }
        },
        computed:{
            noMore:function () {
                return this.count >= this.t_data_t.length;
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
         height: 150px;
         border-radius: 10px;
         margin: 0 auto;
         background: #ffffff;;
         color: black;
         cursor: pointer;
     }

    .infinite-list-wrapper .list-item:hover {
        color: #3f89ec;
    }
    .infinite-list-wrapper .list-item:hover #text_author_a_time{
        color: #3f89ec !important;
    }
    .infinite-list-wrapper .list-item:hover .el-main-text p{
        color: #3f89ec;
    }

    /*#f4f4f4 #ffffff*/
    .infinite-list-wrapper .list-item + .list-item {
        margin-top: 10px;
    }
    .el-main-text{
        padding-top: 0;
        width: 100%;
        height: 50%;
        border: 1px solid transparent;
        text-overflow: ellipsis;
    }
    .el-main-text p{
        display: -webkit-box;
        height: 66px;
        color: #2d2d2d;
        line-height: 23px;
        font-size: 14px;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 3;
        overflow: hidden;
    }
    .list-item:hover {
        box-shadow: 0 16px 32px 0 rgba(48, 55, 66, 0.15);/* 盒子悬浮时阴影 */
    }
</style>