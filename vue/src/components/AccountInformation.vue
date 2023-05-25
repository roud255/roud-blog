<template>
  <div v-if="show_d" style="width: 50%;">
    <el-card style="width: 800px; margin-left: 10px; margin-top: 10px;">
      <el-icon style="margin-left: 97%;cursor: pointer" @click="show_div3"><CloseBold /></el-icon>
      <el-form label-width="80px" size="large">

        <el-upload
            class="avatar-uploader"
            :action="'/api/img/upload'"
            :headers="uploadHeaders"
            :show-file-list="false"
            :on-success="handleSuccess">
          <img v-if="form.file" :src="form.file" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>

        <el-form-item label="邮箱" style="margin-left: 200px; margin-top: -150px">
          <el-input v-model="form.email" disabled autocomplete="off" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="昵称" style="margin-left: 200px">
          <el-input v-model="form.nickname" disabled maxlength="10" autocomplete="off" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="密码" style="margin-left: 200px">
          <el-input v-model="form.password" disabled maxlength="16" autocomplete="off" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="性别" style="margin-left: 200px">
          <el-select v-model="form.sex" placeholder="请选择您的性别" style="width: 400px">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="个性签名" style="margin-left: 200px" >
          <el-input type="input" v-model="form.motto" maxlength="20" autocomplete="off" style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="save">保 存</el-button>
          <el-button type="primary" @click="show_div2">关闭</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AccountInformation",
  data() {
    return {
      show_d:false,
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      options: [{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女'
      }],
      value: '',
      head_img:"",
      uploadHeaders:{"token":localStorage.getItem('token')},
      loginflag:false,
      userid:""
    }
  },

  created() {
    this.load()
  },
  methods: {
    load() {
      if (localStorage.getItem('token')) {
        request.get("/manage/user/info", {params:{
            token : localStorage.getItem('token'),
          }}).then(res =>{
          if(res.code=="80002"){
            this.loginflag = false;
            return;
          }
          // this.userName = res.data.name;
          // this.avatar = this.userName.substring(0,2);
          var img = res.data.imgurl;
          if(img){
            this.head_img = "/api/img/show/"+img;
            this.form.file = this.head_img;
          }
          this.userid = res.data.id
          this.form.email = res.data.phone;
          this.form.nickname = res.data.name;
          this.form.motto = res.data.motto;
          this.form.password = "************";
          var sex = res.data.sex;
          if(sex==0){
            this.form.sex = "男";
          }else{
            this.form.sex = "女";
          }
          this.loginflag = true;
        })
      // const username = this.user.id
      // if (!username) {
      //   this.$message.error("当前无法获取用户信息!请登录!")
      //   return
      // }
      // this.request.get("/user/username/" + username).then(res => {
      //   // console.log(res)
      //   this.form = res.data
      // })
    }},
    show_div(){
      if(!this.loginflag){
          this.$message.error("当前无法获取用户信息!请登录!");
          return;
      }
      this.show_d = !this.show_d;
    },
    show_div2(){
      this.show_d = !this.show_d;
    },
    show_div3(){
      this.show_d = !this.show_d;
    },
    updateToken(){
      request.get("/login/updatetoken", {params:{
          token : localStorage.getItem('token'),
        }}).then(res =>{
        if(res.code=="200"){
          localStorage.setItem('token',res.data.token);
          this.load()
        }})
    },
    save() {
      request.post("/user/updateinfo",this.form).then(res =>{
        if(res.code=="200"){
          this.$message.success(res.msg);
          this.updateToken();
        }else {
          this.$message.error(res.msg);
        }
      });
    },
    // 头像上传
    handleSuccess(response){
      if(response.code=="200"){
        this.head_img = "/api/img/show/"+response.data;
        this.form.file = this.head_img;
        this.updateToken();
        this.$message.success("保存成功")
      }else {
        this.$message.error(response.msg)
      }
    }
  }
}
</script>

<style>
.avatar-uploader {
  text-align: left;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 160px;
  height: 160px;
  display: block;
}
</style>

