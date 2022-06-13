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
                <el-main>
                    <Main/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    import Header from "../components/Header";
    import Aside from "../components/Aside";
    import Main from "../components/UserManage";

    export default {
        name:"Layout",
        components:{
            Header,
            Aside,
            Main
        }
    }
</script>

<style scoped>

</style>