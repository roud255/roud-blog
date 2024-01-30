<template>
  <div style="margin: 10px 0;">
    <el-button type="primary" @click="load">刷新</el-button>
  </div>
  <div style="margin-bottom: 10px" v-loading="inLoading">
    <el-table :data="tableData" border height="67vh" style="width: 100%">
      <el-table-column type="index" label="序号" width="60" :show-overflow-tooltip="true"/>
      <el-table-column prop="time" label="时间" width="180" :show-overflow-tooltip="true"/>
      <el-table-column prop="type" label="类型" width="180" :show-overflow-tooltip="true"/>
      <el-table-column prop="ip" label="IP" width="180" :show-overflow-tooltip="true"/>
      <el-table-column prop="blockingTime" label="时长" :show-overflow-tooltip="true"/>
    </el-table>
  </div>
</template>

<script>
  import {ElMessage} from "element-plus";
  import request from "@/utils/request";

  export default {
    name: "ForBidIPHistory",
    data(){
      return {
        inLoading : false,
      }
    },
    // 自定义指令
    directives : {
      //自动给表格加序号
      indexMethod(index) {
        return index + this.pageSize * (this.currentPage - 1) + 1;
      },
    },
    //创建时加载一次数据
    created() {
      this.load()
    },
    methods : {
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
      load(){
        this.inLoading = true;
        request.get("/manage/ip/blockinglog", {}).then(res =>{
          if(res.code !== 200){
            this.showFailMessage(res.msg);
            return;
          }
          //this.showSuccessMessage(res.msg)
          this.tableData = res.data;
          this.inLoading = false;
        })
      },

    },
  }
</script>

<style scoped>

</style>