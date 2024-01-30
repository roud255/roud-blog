<template>
    <el-scrollbar>
        <div style="position: relative; height:100%; min-width:850px; padding: 30px 30px;">
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
                <el-form-item label="摘要">
                    <el-input v-model="form.description" />
                </el-form-item>
                <el-form-item label="封面">
                    <el-input v-model="form.cover" />
                </el-form-item>
                <el-form-item label="时间">
                    <el-date-picker v-model="form.publishtime" type="datetime" placeholder="选择日期和时间"/>
                </el-form-item>

              <el-form-item label="是否专属">
                <el-radio-group v-model="form.self">
                  <el-radio v-model="form.self" :label="1">是</el-radio>
                  <el-radio v-model="form.self" :label="0">否</el-radio>
                </el-radio-group>
              </el-form-item>

              <el-form-item label="访问秘钥">
                <el-input v-model="form.validateCode" />
              </el-form-item>

                <el-form-item label="内容" style="margin: 20px 0">
                    <v-md-editor v-model="form.postbody" :include-level="[1, 3]" :disabled-menus="[]" @upload-image="handleUploadImage" height="400px"></v-md-editor>
                </el-form-item>

              <!--添加标签-->
              <el-form-item label="标签" style="margin: 20px 0">
                <el-tag
                    v-for="tag in dynamicTags"
                    :key="tag"
                    class="mx-1"
                    closable
                    :disable-transitions="false"
                    @close="handleClose(tag)"
                    v-model="form.tags"
                >
                  {{ tag }}
                </el-tag>
                <el-input
                    class="input-new-tag"
                    v-if="inputVisible"
                    v-model="inputValue"
                    ref="saveTagInput"
                    size="small"
                    @keyup.enter.native="handleInputConfirm"
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
                            <el-button @click="onCancel">取消</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-form>
            <p class="copyright">Copyright © 2022 roud.top. All rights reserved</p>
        </div>
    </el-scrollbar>
</template>
<style>
    .el-tag + .el-tag {
        margin-left: 10px;
    }
    .button-new-tag {
        margin-left: 10px;
        height: 32px;
        line-height: 30px;
        padding-top: 0;
        padding-bottom: 0;
    }
    .input-new-tag {
        width: 90px;
        margin-left: 10px;
        vertical-align: bottom;
    }
    .copyright{
        position: absolute;
        bottom: 2%;
        left: 50%;
        transform: translateX(-50%);
        color: #63aeff;
        font-size: 12px;
        font-weight: 100;
    }
    .copyright:hover{
        color: #476bff;
    }
</style>
<script>
    import {ElMessage} from "element-plus";
    import request from "../../utils/request";
    export default {
        name:"ArticleEditor",
        data() {
            return {
                form: {
                  self:0
                },
                dynamicTags: [],
                inputVisible: false,
                inputValue: '',
                labelPosition:'top',
            };
        },
        methods:{
            showSuccessMessage(msg){
                ElMessage.success({
                    message: msg,
                });
            },
            showFailMessage(msg){
                ElMessage.error({
                    message: msg,
                });
            },
            showWarningMessage(msg){
                ElMessage.warning({
                    message: msg,
                });
            },
            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue) {
                    this.dynamicTags.push(inputValue);
                }
                this.inputVisible = false;
                this.inputValue = '';
            },
            showInput() {
                this.inputVisible = true;
                this.$nextTick(() => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            },
            handleClose(tag) {
                this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
            },
            loadTags(){
                request.get("/manage/tags/select").then(res=>{
                    if(res.code !== 200){
                        return;
                    }
                    let data = res.data;
                    let tags = [];
                    for(var i=0;i<data.length;i++){
                        tags[i] = data[i].tagname;
                    }
                    this.dynamicTags = tags;
                })
            }
            ,
            handleUploadImage(event, insertImage, files) {
              let imgform = {}
              imgform.file = files[0]
              request.post("/img/upload/editor", imgform, {headers:{'Content-Type': 'multipart/form-data'}}).then(res => {
                if(res.code=="200"){
                  insertImage({
                    url: "/api/img/show/"+res.data,
                    desc: ''
                  })
                }
              });
            },
            onSubmit(){
                this.form.tags = this.dynamicTags;
                if(
                    this.form.title==null||this.form.title.trim(" ")==""
                ){
                    this.showWarningMessage("请正确填入标题")
                }else if(this.form.author==null||this.form.author.trim(" ")==""){
                    this.showWarningMessage("请正确填入署名")
                }else if(this.form.description==null||this.form.description.trim(" ")==""){
                    this.showWarningMessage("请正确填入摘要")
                }else if(this.form.cover==null||this.form.cover.trim(" ")==""){
                    this.showWarningMessage("请正确填入封面链接")
                }else if(this.form.publishtime==null){
                    this.showWarningMessage("请正确选择时间")
                }else if(this.form.postbody==null||this.form.postbody.trim(" ")==""){
                    this.showWarningMessage("请正确填入内容")
                }else if(this.form.tags.length==0){
                    this.showWarningMessage("标签不能为空")
                }else if(this.form.tags.length>4){
                    this.showWarningMessage("标签数不能大于4个且不推荐标签过长！")
                }else if(this.form.self==1 && (this.form.validateCode==null||this.form.validateCode.trim(" ")=="")){
                  this.showWarningMessage("专属文章需要填入访问秘钥")
                }else{
                    request.post("/manage/article/add", this.form).then(res => {
                        if(res.code!="200"){
                            this.showFailMessage(res.msg);
                        }else {
                            this.showSuccessMessage(res.msg);
                            this.form={};
                        }
                    });
                }
            },
            onCancel(){
                this.form={};
            }
        },
        created() {
            this.loadTags();
        }
    };
</script>