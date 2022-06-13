<template>
    <div>
        <el-menu
                default-active="1-1"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose"
                style="min-height: calc(100vh - 60px); width: 200px"
        >
            <el-sub-menu index="1">
                <template #title>
                    <el-icon><Stamp /></el-icon>
                    <span>系统管理</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="1-1">用户管理</el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>

            <el-sub-menu index="2">
                <template #title>
                    <el-icon><setting /></el-icon>
                    <span>系统设置</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="2-1">权限管理</el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>
        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "Aside"
    }
</script>
<style>

</style>

