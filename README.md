<h1 align="center">roud-blog</h1>
<p align="center">
<a href=""><img src="https://img.shields.io/badge/author-roud-violet"></a>
<a href=""><img src="https://img.shields.io/badge/version-1.0.1-orange.svg"></a>
<a href=""><img src="https://img.shields.io/badge/license-Apache--2.0-blue"></a>
<a href=""><img src="https://img.shields.io/badge/jdk-1.8-9cf.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Maven-3.6+-yellow.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Redis-5.0+-inactive.svg"></a>
<a href=""><img src="https://img.shields.io/badge/SpringBoot-2.3.RELEASE-blue.svg"></a>
<a href=""><img src="https://img.shields.io/badge/java--jwt-3.10-succes.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Element--plus-2.2.15-green.svg"></a>
<a href=""><img src="https://img.shields.io/badge/MybatisPlus-3.4-red.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Vue-3.0-green.svg"></a>
<a href=""><img src="https://img.shields.io/badge/v--md--editor-2.3-ff69b4.svg"></a>
</p>

### 项目简介(Project Introduction)
##### 1、简介
该项目为一个个人个性化博客项目，项目结构分为前台文章展示部分和后台管理部分，角色目前分为管理员和演示用户。</br>
##### 2、演示站
项目演示地址: </br><a href="http://8.134.71.114:8081/"><img src="https://img.shields.io/badge/roud--blog-1.0-greed.svg"></a> &nbsp; &nbsp; &nbsp;</br>

演示站后台演示账号&密码:</br>
<p><a href="http://8.134.71.114:8081/index/login"><img src="https://img.shields.io/badge/账号-demo@roud.top-greed.svg"></a>&nbsp;<a href="http://8.134.71.114:8081/index/login"><img src="https://img.shields.io/badge/密码-Demo123456-red.svg"></a></p></br>
项目前台展示:</br>
<p><img src="http://roud.top/img/20230506004212.png" alt="前台"></p></br>
文章展示:</br>
<p><img src="http://roud.top/img/20230506091055.png" alt="文章"></p></br>
登录注册页展示:</br>
<p><img src="http://roud.top/img/20230506090505.png" alt="登录注册"></p></br>
后台文章编辑展示:</br>
<p><img src="http://roud.top/img/20230506005056.png" alt="后台页"></p></br>

### 项目搭建(Project construction)
<p>项目准备：jdk、mysql、maven、redis、node.js等相关环境需要提前配置好</p>

>代码拉取: git clone https://gitee.com/roud/roud-blog.git

<p>拉取后的主体项目结构如下：</p>
<p><img src="http://roud.top/img/20230506092641.png"></p>
<p>拉取后的前端项目结构如下：</p>
<p><img src="http://roud.top/img/20230506094725.png"></p>

##### 推荐步骤
1. <p>将sqlfiles目录下的sql文件导入数据库</p>
1. <p>定位到./cms/pom.xml，导入maven依赖</p>
1. <p>定位到./cms/src/main/resources/application.yml，修改数据库、redis及项目端口配置</p>
1. <p>定位到./cms/src/main/resources/config/mail.setting，此处修改邮箱配置（用于注册时发送邮箱验证码）。默认使用网易163邮箱，user填入邮箱，pass填入授权码（授权码获取：登录网易免费邮网页版-设置-POP3/SMTP/IMAP-开启POP3/SMTP-新增授权码）</p>
1. <p>切换到./vue目录下，执行npm install命令安装vue项目所需依赖</p>
1. <p>启动redis服务</p>
1. <p>启动springboot项目服务</p>
1. <p>启动vue服务，启动完成自动跳转至主页</p>

##### 其他
1. <p>目前角色权限仅通过type进行划分，type类型分别为：</p>
 - <p>0-超级管理员（具有对用户和博客文章增删改查权限）</p>
 - <p>1-普通用户（评论权限和查看信息脱敏的后台管理系统的权限，注册的账号默认类型为普通用户）</p>
 - <p>2-演示用户（仅具备查看信息脱敏的后台管理系统的权限）</p>
 
2. <p>如何在自己的项目中将普通账号升级为超级管理员账号？</p>
 - <p>直接修改数据库，将用户type修改为0</p>

3. <p>如何修改头像和拓展信息？</p>
- <p>进入后台-登录-回到首页-个人信息-上传头像成功-修改拓展信息-点击保存（注意：修改头像和修改拓展信息一天仅限一次）</p>
4...

### 页面路由
##### 首页
>http://localhost:8081/index/show

##### 登录页
>http://localhost:8081/index/login

##### 注册页
>http://localhost:8081/index/reg

##### 后台管理用户管理页面
>http://localhost:8081/manage/user

##### 后台管理文章管理页面
>http://localhost:8081/manage/article

##### 博客编辑页
>http://localhost:8081/manage/edit

##### 适配错误页
>http://localhost:8081/errorcomputermodel


### 该项目目前在不断完善，计划完善功能：
1. 小黑屋（废弃，打算在nginx中配置）
2. 主页广告植入及后台广告发布
3. 角色权限细化
4. 商城页面（文章页可以展示商城商品及跳转至商城）
5. 个人资料页
6. 资源共享
...

### 迭代过程
<p>2023.5.26 开始记录项目迭代（堆屎山）的过程</p>
<p>本次更新内容：</p>
<p><strong>用户信息拓展和头像实现</strong></p>
<p>引入mongodb实现图片上传查看，在项目中主要用于账户头像的上传与查看，实现用户头像和拓展信息的修改、项目目录规范化、以及前端小bug的修复等等</p>
<p>…</p>

<p>后续计划开发功能：评论管理</p>

>该项目为个人项目，仅在有空时进行更新完善，目前功能十分不完善，只满足了主要功能，欢迎各位提出宝贵意见。联系QQ：2273459453