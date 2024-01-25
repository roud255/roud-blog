<template>
    <el-scrollbar height="100vh">
        <div style="position : relative; height:100%;min-width: 800px; margin: 0 auto; text-align: center" class="sa—m">
            <div class="show-header">
                <h1 class="show-title">{{title}}</h1>
                <p class="show-tags">
                    <el-tag
                            v-for="tag in tags"
                            :key="tag.tagname"
                            type="warning"
                            effect="dark">
                        {{ tag.tagname }}
                    </el-tag>
                </p>
                <p class="show-publish">
                    {{author}}&ensp;&ensp;{{publishtime}}
                </p>
            </div>
            <div class="show-main">
                <v-md-editor :model-value="data" mode="preview"></v-md-editor>
            </div>
            <div class="comments-box" style="text-align: left; padding: 0 22%; padding-bottom: 50px">
              <el-divider>
<!--                <el-icon><star-filled /></el-icon>-->
                以下为评论区
              </el-divider>
              <article-comments></article-comments>
            </div>
            <p class="copyright">Copyright © 2022 roud.top. All rights reserved</p>
        </div>
        <el-backtop :right="100" :bottom="100" target=".el-scrollbar__wrap">
          <div
              style="
           {
            height: 100%;
            width: 100%;
            background-color: #f2f5f6;
            box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
            text-align: center;
            line-height: 40px;
            color: #1989fa;
          }
        "
          >
            TOP
          </div>
        </el-backtop>
    </el-scrollbar>
</template>

<script>
    import request from "../utils/request";
    import ArticleComments from "@/components/ArticleComments";

    export default {
      name : "ArticleShow",
      components:{
        ArticleComments
      },
      data() {
            return {
                data: '',
                title : '',
                tags : [],
                author : '',
                publishtime : ''
            };
        },
        methods:{
            getPostBody(){
                let url = window.location.href;
                let param_str = url.split('?')[1];
                let params = new URLSearchParams('?'+param_str);
                let get_id = params.get('id');
                let get_validateCode = params.get('validateCode');
                request.get("/aat/getArticleById",{params:{
                        id : get_id,
                        validateCode: get_validateCode
                    }}).then(res=>{
                        if(res.code != "200"){
                            this.$router.push("/notfound");
                            return;
                        }
                        let t_data = res.data;
                        this.data = t_data.postbody;
                        this.title = t_data.title;
                        this.tags = t_data.tags;
                        this.author = t_data.author;
                        this.publishtime = t_data.publishtime;
                })
            }
        },
        created() {
            this.getPostBody();
        }
    };
</script>

<style scoped>
    .show-header{
        position: relative;
        height: 40vh;
        width: 100%;
        background: url("http://qny.roud.top/img/20220904235709.png") center center / cover no-repeat rgb(34, 34, 34);
        text-align: center;
    }
    .show-title{
        position: absolute;
        color: #fff;
        top : 40%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
    .show-publish{
        position: absolute;
        width: 100%;
        color: #fff;
        top : 60%;
         }
    .show-tags{
        position: absolute;
        width: 100%;
        color: #fff;
        top : 70%;
    }
    .show-main{
        width: 60%;
        text-align: left;
        margin: 0 auto;
    }
    .copyright{
        position: absolute;
        bottom: 10px;
        left: 50%;
        transform: translateX(-50%);
        color: #0d0000;
        font-size: 12px;
        font-weight: 100;
    }
</style>