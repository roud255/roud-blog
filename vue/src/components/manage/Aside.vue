<template>
    <div>
        <el-menu
                router
                :default-active="this.$router.path"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose"
                style="height: calc(100vh - 60px); width: 200px"
        >
            <el-sub-menu index="1">
                <template #title>
                    <el-icon><User /></el-icon>
                    <span>用户管理</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="user" >
                        用户列表
                    </el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>

            <el-sub-menu index="2">
                <template #title>
                    <el-icon><Document /></el-icon>
                    <span>文章管理</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="article">
                        文章列表
                    </el-menu-item>
                    <el-menu-item index="edit">
                        文章编辑
                    </el-menu-item>
                    <el-menu-item index="tags">
                          标签列表
                    </el-menu-item>
                    <el-menu-item index="comment">
                          评论列表
                    </el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>

            <el-sub-menu index="3">
                <template #title>
                    <el-icon><Location /></el-icon>
                    <span>黑名单管理</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="forbidip" disabled>
                        黑名单列表
                    </el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>

<!--            <el-sub-menu index="3">-->
<!--                <template #title>-->
<!--                    <el-icon><setting /></el-icon>-->
<!--                    <span>系统设置</span>-->
<!--                </template>-->
<!--                <el-menu-item-group>-->
<!--                    <el-menu-item index="2-1">权限管理</el-menu-item>-->
<!--                </el-menu-item-group>-->
<!--            </el-sub-menu>-->
        </el-menu>
    </div>
</template>

<script>

    export default {
        name: "Aside",
        data(){
            return{

            }
        },
    }
</script>
<style>

</style>

