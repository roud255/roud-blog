<template>
  <div style="margin: 10px 0;">
    <el-button type="primary" @click="add">新增</el-button>
    <el-button type="primary" @click="load">刷新</el-button>
  </div>

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

  <div style="margin-bottom: 10px" v-loading="inLoading">
    <el-table :data="tableData" border height="67vh" style="width: 100%">
<!--      <el-table-column type="index" label="序号" width="60" />-->
      <el-table-column prop="id" label="ID" width="180" />
      <el-table-column prop="name" label="名称" width="180" />
      <el-table-column prop="desc" label="描述" width="180"/>
      <el-table-column prop="addtime" label="添加时间" width="180"/>
      <el-table-column prop="allArticleId" label="涉及文章"/>
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定移除吗?" @confirm="handleDel(scope.row)">
            <template #reference>
              <el-button link type="danger">移除</el-button>
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

    <el-dialog v-model="dialogVisible" title="新增标签" width="500px" :before-close="handleClose" center>
      <el-form :model="form" :rules="rules" ref="form" style="padding: 0 30px" class="demo-ruleForm">
        <el-form-item label="I　P" prop="ip">
          <el-input v-model="form.ip" />
        </el-form-item>
        <el-form-item label="原因" prop="reason">
          <el-input v-model="form.reason"/>
        </el-form-item>
        <el-form-item label="时间" prop="time">
          <el-date-picker v-model="form.time" type="datetime" placeholder="选择日期和时间"/>
        </el-form-item>
      </el-form>

      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="cancelsubmit">取消</el-button>
                    <el-button type="primary" @click="save">确认</el-button>
                </span>
      </template>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: "TagManage"
}
</script>

<style scoped>

</style>