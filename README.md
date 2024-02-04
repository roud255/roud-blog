<h1 align="center">roud-blog</h1>
<p align="center">
<a href=""><img src="https://img.shields.io/badge/Author-roud-violet"></a>
<a href=""><img src="https://img.shields.io/badge/Version-1.0.1-orange.svg"></a>
<a href=""><img src="https://img.shields.io/badge/License-Apache--2.0-blue"></a>
<a href=""><img src="https://img.shields.io/badge/Jdk-1.8-9cf.svg"></a>
<a href=""><img src="https://img.shields.io/badge/SpringBoot-2.3.RELEASE-blue.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Vue-2.0+-green.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Maven-3.6+-yellow.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Redis-inactive.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Java--jwt-succes.svg"></a>
<a href=""><img src="https://img.shields.io/badge/ElementUI--plus-green.svg"></a>
<a href=""><img src="https://img.shields.io/badge/MybatisPlus-red.svg"></a>
<a href=""><img src="https://img.shields.io/badge/V--MD--Editor-ff69b4.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Mogodb-green.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Actuator-FFCC33.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Druid-FFCC33.svg"></a>
</p>

### 后端项目目录：
##### 1、cms（初态后端项目，部分接口已经不兼容前端）
#### 2、roud-blog-cms（推荐使用，重构后的项目，性能优化和代码规范化处理）

### 项目简介(Project Introduction)
##### 1、简介
该项目为一个个人个性化博客项目，项目结构分为前台文章展示部分和后台管理部分，角色目前分为管理员和演示用户。</br>

##### 2、演示站
项目演示地址: </br><a href="http://blog.roud.top/"><img src="https://img.shields.io/badge/roud--blog-1.0-greed.svg"></a> &nbsp; &nbsp; &nbsp;</br>
<a href="http://blog.roud.top/">点我或者点击上面的图标访问演示站</a></br>

演示站后台演示账号&密码:</br>

<p>账号：<strong>demo@roud.top</strong></p>
<p>密码：<strong>Demo123456</strong></p>

项目接口及其作用：
- 请以开发环境启动项目后打开swaggerui查看
- 地址：http://localhost:8989/swagger-ui.html
- swaggerui配置（开发环境）账号：roud  密码：12345671（可自行在配置文件中修改）

### 项目搭建(Project construction)
<p>项目准备：jdk、mysql、maven、redis、mogodb、node.js等相关环境需要提前配置好</p>

>代码拉取: git clone https://gitee.com/roud/roud-blog.git

<p>拉取后的主体项目结构如下：</p>

> 后端项目请使用重构后的roud-blog-cms,图片示例的旧的项目结构，不用管
<p><img src="http://qny.roud.top/img/20230506092641.png"></p>
<p>拉取后的前端项目结构如下：</p>
<p><img src="http://qny.roud.top/img/20230506094725.png"></p>

##### 推荐步骤
1. <p>将sqlfiles目录下的sql文件导入数据库（sql文件中预设一个超级管理员账号数据，用于管理后台管理系统，可自行修改。账号：admin@roud.top 密码：123456Aa）</p>
1. <p>定位到./roud-blog-cms/pom.xml，导入maven依赖</p>
1. <p>定位到./roud-blog-cms/src/main/resources/application.yml，修改数据库、redis、mogodb及项目端口配置</p>
1. <p>定位到./roud-blog-cms/src/main/resources/config/mail.setting，此处修改邮箱配置（用于注册时发送邮箱验证码）。默认使用网易163邮箱，user填入邮箱，pass填入授权码（授权码获取：登录网易免费邮网页版-设置-POP3/SMTP/IMAP-开启POP3/SMTP-新增授权码）</p>
1. <p>切换到./vue目录下，执行npm install命令安装vue项目所需依赖</p>
1. <p>启动redis服务</p>
1. <p>启动springboot项目服务</p>
1. <p>启动vue服务，启动完成自动跳转至主页</p>
1. <p>使用管理员账号密码登录后台（（sql文件中预设一个超级管理员账号数据，用于管理后台管理系统，可自行修改。账号：admin@roud.top 密码：123456Aa））</p>

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




### 该项目目前在不断完善，计划完善功能：
1. 小黑屋（~~废弃，打算在nginx中配置~~）2024.1.31通过Java IO流+nginx+redis实现
2. 主页广告植入及后台广告发布
3. 角色权限细化
4. 商城页面（文章页可以展示商城商品及跳转至商城）
5. 个人资料(已实现)
6. 资源共享
...

### 迭代过程
<p>2023.5.26 开始记录项目迭代（堆屎山）的过程</p>
<p>本次更新内容：</p>
<p><strong>用户信息拓展和头像实现</strong></p>
<p>引入mongodb实现图片上传查看，在项目中主要用于账户头像的上传与查看，实现用户头像和拓展信息的修改、项目目录规范化、以及前端小bug的修复等等</p>
<p>…</p>

<p>2023.6.18</p>
<p>本次更新内容：</p>
<p>修复文章新增功能、新增文章编辑粘贴图片上传功能、文章编辑功能完善、新增文章浏览回到顶部功能以及修复若干bug</p>
<p>…</p>

<p>2023.6.24</p>
<p>本次更新内容：</p>
<p>引入kafka+elk</p>
<p>…</p>

<p>2023.11.07</p>
<p>本次更新内容：</p>
<p>新增专属文章，需要输入访问秘钥方可进行浏览</p>
<p>…</p>

<p>2023.12.02</p>
<p>本次更新内容：</p>
<p>单点登录完善，优化登录成功后返回给前端的token样式</p>
<p>…</p>

<p>2024.1.28</p>
<p>本次更新内容：</p>
<p>（重大更新）项目重构（主要是后端）。主要对代码进行了规范化修改、优化部分页面ui、完善后台管理、引入swagger和优化了项目缓存</p>
<p>…</p>

> 该项目为个人项目，仅在有空时进行更新完善，目前功能十分不完善，只满足了主要功能，欢迎各位提出宝贵意见。联系QQ：2273459453