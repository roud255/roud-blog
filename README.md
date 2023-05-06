<h1 style="text-align: center">roud-blog</h1>
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
1. <p>将sql文件导入数据库</p>
1. <p>定位到./cms/pom.xml，导入maven依赖</p>
1. <p>定位到./cms/src/main/resources/application.yml，修改数据库、redis及项目端口配置</p>
1. <p>定位到./cms/src/main/resources/config/mail.setting，此处修改邮箱配置（用于注册时发送邮箱验证码），当前注册功能默认关闭；如需开启，请在./cms/src/main/java/top/roud/cms/controller/RegisterController.java中取消代码注释</p>
1. <p>切换到./vue目录下，执行npm install命令安装vue项目所需依赖</p>
1. <p>启动redis服务</p>
1. <p>启动springboot项目服务</p>
1. <p>启动vue服务，启动完成自动跳转至主页</p>



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
3. 文章评论（目前在做）
4. 商城页面（文章页可以展示商城商品及跳转至商城）
5. 个人资料页
6. 角色功能完善。目前只有管理员和演示用户，管理员可以对用户、文章等进行增删改查；演示用户仅可查看后台（用户密码等敏感信息无法查看），无任何操作权限
...

>该项目为个人项目，仅在有空时进行更新完善，目前功能十分不完善，只满足了主要功能，欢迎各位提出宝贵意见。联系QQ：2273459453