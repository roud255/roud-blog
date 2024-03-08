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

    <div style="margin-bottom: 10px" v-loading="inLoading">
        <el-table :data="tableData" border height="67vh" style="width: 100%">
            <el-table-column type="index" label="序号" width="60" />
            <el-table-column prop="id" label="ID" width="180" />
            <el-table-column prop="title" label="标题" width="180" :show-overflow-tooltip="true"/>
            <el-table-column prop="author" label="作者" width="80"/>
            <el-table-column prop="description" label="摘要" width="100" :show-overflow-tooltip="true"/>
            <el-table-column prop="cover" label="封面" width="100" :show-overflow-tooltip="true"/>
            <el-table-column prop="postbody" label="内容" :show-overflow-tooltip="true"/>
            <el-table-column prop="publishtime" label="发布时间" width="100" :show-overflow-tooltip="true"/>
            <el-table-column prop="self" label="是否专属（1是0否）" width="100" :show-overflow-tooltip="true " align="center"/>
            <el-table-column prop="validateCode" label="访问秘钥" width="100" :show-overflow-tooltip="true"/>
            <el-table-column prop="sort" label="是否置顶（1是2否）" width="100" :show-overflow-tooltip="true" align="center"/>
          <el-table-column fixed="right" label="操作" width="120">
                <template #default="scope">
                    <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-popconfirm title="确定删除吗?" @confirm="handleDel(scope.row)">
                        <template #reference>
                            <el-button link type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
    </div>

    <el-dialog v-model="dialogVisible" title="修改文章" width="800px" :before-close="handleClose" center>
      <el-form
          :model="form"
          :rules="rules"
          ref="form"
          label-width="auto"
          :label-position="labelPosition"
          class="demo-ruleForm"
      >
        <el-form-item label="标题"  prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="署名" prop="author">
          <el-input v-model="form.author" />
        </el-form-item>
        <el-form-item label="摘要" prop="description">
          <el-input v-model="form.description" />
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-input v-model="form.cover" />
        </el-form-item>
        <el-form-item label="时间">
          <el-date-picker v-model="form.publishtime" type="datetime" placeholder="选择日期和时间" disabled/>
        </el-form-item>

        <el-form-item label="是否专属">
          <el-radio-group v-model="form.self">
            <el-radio v-model="form.self" :label="1">是</el-radio>
            <el-radio v-model="form.self" :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="访问秘钥">
          <el-input v-model="form.validateCode"/>
        </el-form-item>
        <el-form-item label="是否置顶">
          <el-radio-group v-model="form.sort">
            <el-radio v-model="form.sort" :label="1">是</el-radio>
            <el-radio v-model="form.sort" :label="2">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="内容" style="margin: 20px 0">
          <v-md-editor v-model="form.postbody" height="400px"></v-md-editor>
        </el-form-item>

        <div style="text-align: -webkit-center">
          <el-form ref="ruleForm" style="width: max-content">
            <el-form-item style="margin: 20px 0">
              <el-button type="primary" @click="onSubmitUpdate">更新</el-button>
              <el-button @click="onCancel">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-form>
    </el-dialog>

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
        name: "UserManage",
        data(){
            return{
                form : {},
                // dialogVisible : false,
                search : "",
                currentPage : 1,
                pageSize : 10,
                total : 0,
                tableData : [],
                dialogVisible: false,
                inLoading : false,
              rules: {
                  //邮箱校验规则
                  title: [
                    { required: true, message: "必填", trigger: "blur" },
                  ],
                  author:[
                    { required: true, message: "必填", trigger: "blur" },
                  ],
                  description:[
                    { required: true, message: "必填", trigger: "blur" },
                  ],
                  cover:[
                    { required: true, message: "必填", trigger: 'change',},
                  ]
                }
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
              this.inLoading = true;
              request.get("/manage/article/fps", {params:{
                        pageNum : this.currentPage,
                        pageSize : this.pageSize,
                        search : this.search
                    }}).then(res =>{
                        if(res.code !== 200){
                            return;
                        }
                        this.total = res.data.total;
                        this.tableData = res.data.records;
                        this.inLoading = false;
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
                request.delete("/manage/article/del/"+id).then(res =>{
                    if(res.code !== 200){
                        this.showFailMessage(res.msg);
                        return;
                    }
                    this.showSuccessMessage(res.msg)
                });
                this.load();
            },
            handleEdit(row){
              this.form = JSON.parse(JSON.stringify(row));
              this.dialogVisible = true;
            },
            onCancel(){
              this.dialogVisible=false;
            },
            onSubmitUpdate(){
              this.$refs['form'].validate(valid => {
                if (valid) {
                  if(this.form.id){//更新
                    request.put("/manage/article/update", this.form).then(res =>{
                      if(res.code !== 200){
                        this.showFailMessage(res.msg);
                        return;
                      }
                      this.showSuccessMessage(res.msg)
                    });
                    this.dialogVisible = false;
                    this.form = {};
                    this.load();
                  }
                } else {
                  this.showFailMessage("提交失败");
                }
              });

            },

        },

    }
</script>

<!--scope不生效-->
<style lang="css">
    .el-popper {
        font-size: 12px;
        max-width: 500px;
    }
</style>