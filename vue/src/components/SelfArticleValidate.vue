<template>
  <div v-if="show_d" style="width: 100%; height: 100%">
    <el-dialog v-model="show_d" title="权限校验" center style="width:20%; min-width: 300px">
    <span>
      <el-form-item>
          <el-input v-model="selfArticleValidateVal" placeholder="请输入访问秘钥" clearable></el-input>
      </el-form-item>
      <p style="color: #f00;">提示: 该文章属于专属文章，需要输入访问秘钥验证通过方可浏览！</p>
    </span>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="submitValidate(this.articleId)">
          提交
        </el-button>
        <el-button @click="cancelValite">取消</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "SelfArticleValidate",
    data() {
    return {
        show_d: false,
        selfArticleValidateVal: "",
        articleId:"",
    }
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
    show_div(){
      this.show_d = !this.show_d;
    },
    show_div2(id){
      this.show_d = !this.show_d;
      this.articleId = id;
    },
    submitValidate(id){
      this.validate(id, this.selfArticleValidateVal)
      this.cancelValite();
    },
    cancelValite(){
      this.selfArticleValidateVal = "";
      this.show_div()
    },
    validate(id, validateCode){
      if(validateCode=="" || validateCode==null){
        this.showFailMessage("输入的验证码为空");
        return;
      }
      request.get("/aat/selfArticle/validate", {params:{
          articleId : id,
          validateCode : validateCode,
        }}).then(res =>{
        if(res.code=="200"){
          window.open('/article/show?id='+id.toString()+"&validateCode="+validateCode,'_blank');
          }else {
          this.showFailMessage(res.msg);
        }
        }
        )
    }
  }
}
</script>

<style scoped>

</style>