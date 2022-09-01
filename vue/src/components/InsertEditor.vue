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

              <!--添加标签-->
              <el-form-item label="标签" style="margin: 20px 0">
                <el-tag
                    v-for="tag in dynamicTags"
                    :key="tag"
                    class="mx-1"
                    closable
                    :disable-transitions="false"
                    @close="handleClose(tag)"
                >
                  {{ tag }}
                </el-tag>
                <el-input
                    v-if="inputVisible"
                    ref="InputRef"
                    v-model="inputValue"
                    class="ml-1 w-20"
                    size="small"
                    @keyup.enter="handleInputConfirm"
                    @blur="handleInputConfirm"
                />
                <el-button v-else class="button-new-tag ml-1" size="small" @click="showInput">
                  + New Tag
                </el-button>
              </el-form-item>


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
                dynamicTags: ['入门', '基础', '进阶'],
                inputVisible: false,
                inputValue: '',
                labelPosition:'top',
            };
        },
        methods:{
          handleInputConfirm(){
            if (this.inputValue.value) {
              this.dynamicTags.value.push(inputValue.value)
            }
            this.inputVisible.value = false
            this.inputValue.value = ''
          },
          handleClose(tag: string)=>{
            this.dynamicTags.value.splice(this.dynamicTags.value.indexOf(tag), 1)
          }
        }
    };
</script>