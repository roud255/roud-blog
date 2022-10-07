<template>
    <div class="common-layout">
        <el-container>
            <el-header style="padding: 0">
                <Header/>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <Aside/>
                </el-aside>
                <el-main style="height: calc(100vh - 60px);">
                        <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    import Header from "../components/manage/Header";
    import Aside from "../components/manage/Aside";
    import Main from "../components/manage/UserManage";

    export default {
        name:"Manage",
        components:{
            Header,
            Aside,
            Main
        }
    }
</script>

<style scoped>
    .common-layout{
        min-width: 800px;
    }
</style>