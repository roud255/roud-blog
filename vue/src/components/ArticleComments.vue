<template>
  <div>
    <div v-clickoutside="hideReplyBtn" @click="inputFocus" class="my-reply">
        <el-avatar class="header-img" :size="40" :src="headimg" v-if="headimg"></el-avatar>
        <el-avatar class="header-img" :size="40" :style="`background:dodgerblue;`" v-if="!headimg"> {{getHeadName(myName)}} </el-avatar>
      <div class="reply-info" >
        <div
            tabindex="0"
            contenteditable="true"
            id="replyInput"
            spellcheck="false"
            placeholder="输入评论..."
            class="reply-input"
            @focus="showReplyBtn"
            @input="onDivInput($event)"
        >
        </div>
      </div>
      <div class="reply-btn-box" v-show="btnShow">
        <el-button class="reply-btn" size="medium" @click="sendComment" type="primary">发表评论</el-button>
      </div>
    </div>
    <div v-for="(item,i) in comments" :key="i" class="author-title reply-father">
      <!--nn-->
      <el-popover
          :width="300"
          popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
          placement="right"
          trigger="click"
      >
        <template #reference>
          <el-avatar class="header-img" :size="40" :src="item.headimg" v-if="item.headimg"></el-avatar>
          <el-avatar class="header-img" :size="40" :style="`background:dodgerblue`" v-if="!item.headimg"> {{getHeadName(item.name)}} </el-avatar>
        </template>
        <template #default>
          <div
              class="demo-rich-conent"
              style="display: flex; gap: 16px; flex-direction: column"
          >
            <el-avatar class="header-img" :size="60" :src="item.headimg" v-if="item.headimg"></el-avatar>
            <el-avatar class="header-img" :size="60" :style="`background:dodgerblue`" v-if="!item.headimg"> {{getHeadName(item.name)}} </el-avatar>
            <div>
              <p
                  class="demo-rich-content__name"
                  style="margin: 0; font-weight: 500"
              >
                {{item.name}}<span style="display: inline-block; margin-left: 20px" v-if="item.sex==`男`"><el-icon color="blue"><Male /></el-icon></span>
                <span style="display: inline-block; margin-left: 20px" v-if="item.sex==`女`"><el-icon color="pink"><Female /></el-icon></span>
              </p>
              <p
                  class="demo-rich-content__mention"
                  style="margin: 0; font-size: 14px; color: var(--el-color-info)"
              >
                {{item.email}}
              </p>
            </div>

            <p class="demo-rich-content__desc" style="margin: 0">
              {{item.motto}}
            </p>
          </div>
        </template>
      </el-popover>


<!--      <el-avatar class="header-img" :size="40" :src="item.headimg" v-if="item.headimg"></el-avatar>-->
<!--      <el-avatar class="header-img" :size="40" :style="`background:dodgerblue`" v-if="!item.headimg"> {{getHeadName(item.name)}} </el-avatar>-->
<!--      -->
      <div class="author-info">
        <span class="author-name">{{item.name}}</span>
        <span class="author-time">{{item.time}}</span>
      </div>
      <div class="icon-btn">
        <span @click="showReplyInput(i,item.name,item.id,item.parent_id,0)"><el-icon><ChatDotSquare /></el-icon></span>
<!--        <span @click="showReplyInput(i,item.name,item.id)"><el-icon><Comment /></el-icon>{{item.commentNum}}</span>-->
<!--        <el-icon><Promotion /></el-icon>{{item.like}}-->
      </div>
      <div class="talk-box">
        <p>
          <span class="reply">{{item.comment}}</span>
        </p>
      </div>
      <div class="reply-box">
        <div v-for="(reply,j) in item.reply" :key="j" class="author-title">
<!--          <el-avatar class="header-img" :size="40" :src="reply.headimg" v-if="reply.headimg"></el-avatar>-->
<!--          <el-avatar class="header-img" :size="40" :style="`background:dodgerblue`" v-if="!reply.headimg"> {{getHeadName(reply.from)}} </el-avatar>-->

          <el-popover
              :width="300"
              popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
              placement="right"
              trigger="click"
          >
            <template #reference>
              <el-avatar class="header-img" :size="40" :src="reply.headimg" v-if="reply.headimg"></el-avatar>
              <el-avatar class="header-img" :size="40" :style="`background:dodgerblue`" v-if="!reply.headimg"> {{getHeadName(reply.from)}} </el-avatar>
            </template>
            <template #default>
              <div
                  class="demo-rich-conent"
                  style="display: flex; gap: 16px; flex-direction: column"
              >
                <el-avatar class="header-img" :size="40" :src="reply.headimg" v-if="reply.headimg"></el-avatar>
                <el-avatar class="header-img" :size="40" :style="`background:dodgerblue`" v-if="!reply.headimg"> {{getHeadName(reply.from)}} </el-avatar>
                <div>
                  <p
                      class="demo-rich-content__name"
                      style="margin: 0; font-weight: 500"
                  >
                    {{reply.from}}<span style="display: inline-block; margin-left: 20px" v-if="reply.sex==`男`"><el-icon color="blue"><Male /></el-icon></span>
                    <span style="display: inline-block; margin-left: 20px" v-if="reply.sex==`女`"><el-icon color="pink"><Female /></el-icon></span>
                  </p>
                  <p
                      class="demo-rich-content__mention"
                      style="margin: 0; font-size: 14px; color: var(--el-color-info)"
                  >
                    {{reply.email}}
                  </p>
                </div>

                <p class="demo-rich-content__desc" style="margin: 0">
                  {{reply.motto}}
                </p>
              </div>
            </template>
          </el-popover>

          <div class="author-info">
            <span class="author-name">{{reply.from}}</span>
            <span class="author-time">{{reply.time}}</span>
          </div>
          <div class="icon-btn">
            <span @click="showReplyInput(i,reply.from,reply.id,reply.parent_id,1)"><el-icon><ChatDotSquare /></el-icon></span>
<!--            <span @click="showReplyInput(i,reply.from,reply.id)"><el-icon><Comment /></el-icon>{{reply.commentNum}}</span>-->
<!--            <el-icon><Promotion /></el-icon>{{reply.like}}-->
          </div>
          <div class="talk-box">
            <p>
              <span>回复 <span style="color: #9B9B9B">{{reply.to}}</span>:</span>
              <span class="reply">{{reply.comment}}</span>
            </p>
          </div>
          <div class="reply-box">

          </div>
        </div>
      </div>
      <div  v-show="_inputShow(i)" class="my-reply my-comment-reply">
        <el-avatar class="header-img" :size="40" :style="`background:dodgerblue;`" v-if="!headimg"> {{getHeadName(myName)}} </el-avatar>
        <el-avatar class="header-img" :size="40" :src="headimg" v-if="headimg"></el-avatar>
        <div class="reply-info" >
          <div tabindex="0" contenteditable="true" spellcheck="false" placeholder="输入评论..."   @input="onDivInput($event)"  class="reply-input reply-comment-input"></div>
        </div>
        <div class=" reply-btn-box">
          <el-button class="reply-btn" size="medium" @click="sendCommentReply(i,j)" type="primary">发表评论</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ElMessage} from "element-plus";

const clickoutside = {
  // 初始化指令
  bind(el, binding, vnode) {
    function documentHandler(e) {
      // 这里判断点击的元素是否是本身，是本身，则返回
      if (el.contains(e.target)) {
        return false;
      }
      // 判断指令中是否绑定了函数
      if (binding.expression) {
        // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
        binding.value(e);
      }
    }
    // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
    el.vueClickOutside = documentHandler;
    document.addEventListener('click', documentHandler);
  },
  update() {},
  unbind(el, binding) {
    // 解除事件监听
    document.removeEventListener('click', el.vueClickOutside);
    delete el.vueClickOutside;
  },
};
export default {
  name: "ArticleComments",
  data(){
    return{
      btnShow: false,
      index:'0',
      replyComment:'',
      myName:'',
      myId:19870621,
      to:'',
      toId:-1,
      headimg:'',
      email :'',
      motto :'',
      sex :'',
      comments:[]
          // [
          // {
          //   name:'Lana Del Rey',
          //   id:19870621,
          //   headImg:'http://roud.top/img/ziya.jpg',
          //   comment:'我发布一张新专辑Norman Fucking Rockwell,大家快来听啊',
          //   time:'2019年9月16日 18:43',
          //   commentNum:2,
          //   like:15,
          //   inputShow:false,
          //   reply:[
          //     {
          //       from:'Taylor Swift',
          //       id:19891221,
          //       fromHeadImg:'http://roud.top/img/ziya.jpg',
          //       to:'Lana Del Rey',
          //       toId:19870621,
          //       comment:'我很喜欢你的新专辑！！',
          //       time:'2019年9月16日 18:43',
          //       commentNum:1,
          //       like:15,
          //       inputShow:false
          //     },
          //     {
          //       from:'Ariana Grande',
          //       id:1123,
          //       fromHeadImg:'http://roud.top/img/ziya.jpg',
          //       to:'Lana Del Rey',
          //       toId:19870621,
          //       comment:'别忘记宣传我们的合作单曲啊',
          //       time:'2019年9月16日 18:43',
          //       commentNum:0,
          //       like:5,
          //       inputShow:false
          //     }
          //   ]
          // },
          //     {
          //       name:'Taylor Swift',
          //       id:19891221,
          //       headImg:'http://roud.top/img/ziya.jpg',
          //       comment:'我发行了我的新专辑Lover',
          //       time:'2019年9月16日 18:43',
          //       commentNum:1,
          //       like:5,
          //       inputShow:false,
          //       reply:[
          //         {
          //           from:'Lana Del Rey',
          //           id:19870621,
          //           fromHeadImg:'http://roud.top/img/ziya.jpg',
          //           to:'Taylor Swift',
          //           toId:19891221,
          //           comment:'新专辑和speak now 一样棒！',
          //           time:'2019年9月16日 18:43',
          //           commentNum:25,
          //           like:5,
          //           inputShow:false
          //
          //         }
          //       ]
          //     },
          //     {
          //       name:'Norman Fucking Rockwell',
          //       id:20190830,
          //       headImg:'http://roud.top/img/ziya.jpg',
          //       comment:'Plz buy Norman Fucking Rockwell on everywhere',
          //       time:'2019年9月16日 18:43',
          //       commentNum:0,
          //       like:5,
          //       inputShow:false,
          //       reply:[]
          //     },
          // ]
    }
  },
  directives: {clickoutside},
  methods: {
    inputFocus(){
      request.get("/manage/user/info", {params:{
          token : localStorage.getItem('token'),
        }}).then(res =>{
        if(res.code!="200"){
          this.$router.push("/index/login");
        }else {
          var replyInput = document.getElementById('replyInput');
          replyInput.style.padding= "8px 8px"
          replyInput.style.border ="2px solid blue"
          replyInput.focus()
        }
      })
    },
    showReplyBtn(){
      this.btnShow = true
    },
    hideReplyBtn(){
      this.btnShow = false
      replyInput.style.padding= "10px"
      replyInput.style.border ="none"
    },
    showReplyInput(i,name,id,parent_id,flag){
      this.comments[this.index].inputShow = false
      this.index =i
      this.comments[i].inputShow = true
      this.to = name
      if(flag==0){
        this.toId = id
      }else {
        this.toId = parent_id
      }
    },
    _inputShow(i){
      return this.comments[i].inputShow
    },
    getArticleId(){
      let url = window.location.href;
      let param_str = url.split('?')[1];
      let params = new URLSearchParams('?'+param_str);
      let get_id = params.get('id');
      return get_id
    },
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
    getHeadName(name){
      var len = (name.split("")).length;
      if(len>5){
        return name.slice(0,5);
      }
      return name;
    },
    sendComment(){
      if(!this.replyComment){
        this.$message({
          showClose: true,
          type:'warning',
          message:'评论不能为空'
        })
      }else{
        let a ={}
        let input =  document.getElementById('replyInput')
        let timeNow = new Date().getTime();
        let time= this.dateStr(timeNow);
        a.name= this.myName
        a.comment =this.replyComment
        a.headimg = this.headimg
        a.email = this.email
        a.motto = this.motto
        a.sex = this.sex
        a.time = time
        a.commentNum = 0
        a.like = 0
        a.article_id = this.getArticleId()
        a.parent_id = this.toId
        request.post("/aac",a).then(res =>{
          if(res.code != "200"){
            this.showFailMessage(res.msg);
          }else {
            //后台成功才添加评论
            this.comments.push(a)
            this.showSuccessMessage("成功！");
          }
        });
        this.toId = -1
        this.replyComment = ''
        input.innerHTML = '';

      }
    },
    sendCommentReply(i,j){
      if(!this.replyComment){
        this.$message({
          showClose: true,
          type:'warning',
          message:'评论不能为空'
        })
      }else{
        let a ={}
        let timeNow = new Date().getTime();
        let time= this.dateStr(timeNow);
        a.from= this.myName
        a.to = this.to
        a.headimg = this.headimg
        a.email = this.email
        a.motto = this.motto
        a.sex = this.sex
        a.comment =this.replyComment
        a.time = time
        // a.commentNum = 0
        // a.like = 0
        a.article_id = this.getArticleId()
        a.parent_id = this.toId
        request.post("/aac",a).then(res =>{
          if(res.code != "200"){
            this.showFailMessage(res.msg);
          }else {
            this.comments[i].reply.push(a)
            this.showSuccessMessage("成功！");
          }
        });
        this.toId = -1
        this.replyComment = ''
        document.getElementsByClassName("reply-comment-input")[i].innerHTML = ""
      }
    },
    onDivInput: function(e) {
      this.replyComment = e.target.innerHTML;
    },
    dateStr(date){
      //获取js 时间戳
      var time=new Date().getTime();
      //去掉 js 时间戳后三位，与php 时间戳保持一致
      time=parseInt((time-date)/1000);
      //存储转换值
      var s;
      if(time<60*10){//十分钟内
        return '刚刚';
      }else if((time<60*60)&&(time>=60*10)){
        //超过十分钟少于1小时
        s = Math.floor(time/60);
        return  s+"分钟前";
      }else if((time<60*60*24)&&(time>=60*60)){
        //超过1小时少于24小时
        s = Math.floor(time/60/60);
        return  s+"小时前";
      }else if((time<60*60*24*30)&&(time>=60*60*24)){
        //超过1天少于30天内
        s = Math.floor(time/60/60/24);
        return s+"天前";
      }else{
        //超过30天ddd
        var date= new Date(parseInt(date));
        return date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate();
      }
    },
    initload(){
      request.get("/manage/user/info", {params:{
          token : localStorage.getItem('token'),
        }}).then(res =>{
        if(res.code=="200"){
          this.myName = res.data.name
          this.email = res.data.phone
          this.motto = res.data.motto
          this.sex = res.data.sex==0?"男":"女"
          if(res.data.imgurl){
            this.headimg = "/api/img/show/"+res.data.imgurl;
          }
        }else {
          this.myName = "登录后评论~"
        }})
      let get_id = this.getArticleId()
      request.get("/aac",{params:{
          id : get_id,
        }}).then(res =>{
        if(res.code != "200"){
          this.showFailMessage("初始化评论区失败！");
        }else {
          let data = res.data;
          let comment = [];
          for(var i=0;i<data.length;i++){
            let j = {};
            j.name=data[i].from_name;
            j.id = data[i].id;
            j.headimg = data[i].headimg;
            j.sex = data[i].sex;
            j.motto = data[i].motto;
            j.email = data[i].email;
            j.comment = data[i].content;
            j.time = data[i].op_time;
            j.id = data[i].id;
            j.parent_id = data[i].parent_id;
            j.inputShow = false;
            let reply = data[i].child_comments
            let r2 = []
            if(reply.length>0){
              for(var flag=0;flag<reply.length;flag++){
                let j2 = {}
                j2.id = reply[flag].id;
                j2.headimg = reply[flag].headimg;
                j2.email = reply[flag].email;
                j2.motto = reply[flag].motto;
                j2.sex = reply[flag].sex;
                j2.comment = reply[flag].content;
                j2.time = reply[flag].op_time;
                j2.id = reply[flag].id;
                j2.from=reply[flag].from_name;
                j2.to=reply[flag].to_name;
                j2.parent_id = reply[flag].parent_id;
                j2.inputShow = false;
                r2.push(j2)
              }
            }
            j.reply=r2
            comment.push(j)
          }
          this.comments = comment;
        }
      });
    }
  },
  created() {
    this.initload()
  }
}
</script>
<style lang="stylus" scoped>
.my-reply
  padding 10px
  background-color #fafbfc
  .header-img
    display inline-block
    vertical-align top
    line-height 40px
  .reply-info
    display inline-block
    margin-left 5px
    width 90%
    @media screen and (max-width:1200px) {
      width 80%
    }
    .reply-input
      min-height 20px
      line-height 22px
      padding 10px 10px
      color #ccc
      background-color #fff
      border-radius 5px
      &:empty:before
        content attr(placeholder)
      &:focus:before
        content none
      &:focus
        padding 8px 8px
        border 2px solid blue
        box-shadow none
        outline none
  .reply-btn-box
    height 25px
    margin 10px 0
    .reply-btn
      position relative
      float right
      margin-right 15px
.my-comment-reply
  margin-left 50px
  .reply-input
    width flex
.author-title:not(:last-child)
  border-bottom: 1px solid rgba(178,186,194,.3)
.author-title
  padding 10px
  .header-img
    display inline-block
    vertical-align top
    line-height 40px
  .author-info
    display inline-block
    margin-left 5px
    width 60%
    height 40px
    line-height 20px
    >span
      display block
      cursor pointer
      overflow hidden
      white-space nowrap
      text-overflow ellipsis
    .author-name
      color #000000
      font-size 18px
      font-weight 600
    .author-time
      color #5b5b5b
      font-size 14px
  .icon-btn
    width 30%
    padding 0 !important
    float right
    @media screen and (max-width : 1200px){
      width 20%
      padding 7px
    }
    >span
      float right
      cursor pointer
    .iconfont
      margin 0 5px
  .talk-box
    margin 0 50px
    >p
      margin 0
    .reply
      font-size 16px
      color #000
  .reply-box
    margin 10px 0 0 50px
    background-color #efefef
</style>