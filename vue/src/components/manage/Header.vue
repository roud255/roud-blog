<template>
    <div style="height: 60px;line-height: 60px;border-bottom: 1px solid #ccc; display: flex">
        <div style="width: 200px; padding: 0 50px; color: cornflowerblue; font-weight: bold">后台管理系统</div>
        <div style="flex: 1"></div>
        <div style="width: 100px">
            <el-dropdown>
                <span class="el-dropdown-link" style="height: 60px; line-height: 60px">
                  张三<el-icon class="el-icon--right"><arrow-down /></el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item>个人信息</el-dropdown-item>
                        <el-dropdown-item>退出系统</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>
<script>
    export default {
        name:"Header"
    }
</script>