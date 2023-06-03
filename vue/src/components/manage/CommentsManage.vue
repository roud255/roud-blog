<template>

  <div style="margin: 10px 0;">
    <el-input
        v-model="search"
        placeholder="输入关键字"
        style="width: 21.5%"
        clearable
        @keyup.enter="load"
    />
    <el-button type="primary" style="margin: 0 10px" @click="load">查询</el-button>
  </div>

  <div style="margin-bottom: 10px">
    <el-table :data="tableData" border height="67vh" style="width: 100%">
      <el-table-column type="index" label="序号" width="60" />
      <el-table-column prop="id" label="ID" width="180" />
      <el-table-column prop="content" label="内容" width="180" :show-overflow-tooltip="true"/>
      <el-table-column prop="from_name" label="作者" width="180"/>
      <el-table-column prop="op_time" label="操作时间" :show-overflow-tooltip="true"/>
      <el-table-column prop="parent_id" label="父评论ID" width="100" :show-overflow-tooltip="true"/>
      <el-table-column prop="article_id" label="来源文章" width="180" :show-overflow-tooltip="true"/>
      <el-table-column prop="to_name" label="被评论者名" width="100" :show-overflow-tooltip="true"/>
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <!--                    <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>-->
          <el-popconfirm title="确定删除吗?" @confirm="handleDel(scope.row)">
            <template #reference>
              <el-button link type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <div>
    <el-pagination
        v-model:currentPage="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 50, 100, 200]"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>

</template>

<script>
import request from "../../utils/request";
import {ElMessage } from 'element-plus'

export default {
  name: "CommentsManage",
  data(){
    return{
      form : {},
      // dialogVisible : false,
      search : "",
      currentPage : 1,
      pageSize : 10,
      total : 0,
      tableData : [],
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
    }
    ,
    load(){
      request.get("/manage/comment/findall", {params:{
          pageNum : this.currentPage,
          pageSize : this.pageSize,
          search : this.search
        }}).then(res =>{
        if(res.code != "200"){
          return;
        }
        this.total = res.data.total;
        this.tableData = res.data.records;
      })
    },
    //改变页数时加载一次数据
    handleSizeChange(){
      this.load()
    },
    //改变页码时加载一次数据
    handleCurrentChange(){
      this.load()
    },
    //
    // handleEdit(row){
    //     this.form = JSON.parse(JSON.stringify(row));
    //     this.dialogVisible = true;
    // },
    handleDel(row){
      let parse = JSON.parse(JSON.stringify(row));
      let id = parse.id;
      request.delete("/manage/comment/del/"+id).then(res =>{
        if(res.code != "200"){
          this.showFailMessage(res.msg);
          return;
        }
        this.showSuccessMessage(res.msg)
      });
      this.load();
    },

  },

}
</script>

<style scoped>

</style>