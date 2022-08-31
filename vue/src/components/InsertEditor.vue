<template>
    <el-scrollbar height="100vh">
        <div style="height:100%;padding: 30px 30px;">
            <h1 style="margin:20px 0;font-size: 32px;font-weight: 100">Roud后台编辑系统</h1>
            <el-form
                ref="form"
                label-width="auto"
                :label-position="labelPosition"
            >
                <el-form-item label="标题">
                    <el-input v-model="form.title" />
                </el-form-item>
                <el-form-item label="署名">
                    <el-input v-model="form.author" />
                </el-form-item>
                <el-form-item label="时间">
                    <el-date-picker
                            v-model="form.publishtime"
                            type="datetime"
                            placeholder="选择日期和时间"
                    />
                </el-form-item>

                <v-md-editor v-model="text" height="400px"></v-md-editor>

                <div style="text-align: -webkit-center">
                    <el-form ref="ruleForm" style="width: max-content">
                        <el-form-item style="margin: 20px 0">
                            <el-button type="primary" @click="onSubmit">提交</el-button>
                            <el-button>取消</el-button>
                        </el-form-item>
                    </el-form>
                </div>

            </el-form>

        </div>
    </el-scrollbar>
</template>

<script>
    export default {
        name:"InsertEditor",
        data() {
            return {
                text: '',
                form: {},
                labelPosition:'top',
            };
        },
    };
</script>