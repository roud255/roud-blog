#  <center> roud-blog
<p align="center">
<a href=""><img src="https://img.shields.io/badge/author-roud-violet"></a>
<a href=""><img src="https://img.shields.io/badge/license-Apache--2.0-blue"></a>
<a href=""><img src="https://img.shields.io/badge/jdk-1.8-9cf.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Maven-3.6+-yellow.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Redis-5.0+-inactive.svg"></a>
<a href=""><img src="https://img.shields.io/badge/SpringBoot-2.3.RELEASE-blue.svg"></a>
<a href=""><img src="https://img.shields.io/badge/Vue-3.0-green.svg"></a>
<a href=""><img src="https://img.shields.io/badge/ElementUI-plus-green.svg"></a>
<a href=""><img src="https://img.shields.io/badge/MybatisPlus-3.4-red.svg"></a>
</p>

### 项目简介(Project Introduction)
##### 1、简介
该项目为一个个人个性化博客项目,项目结构分为前台文章展示部分和后台管理部分。</br>
##### 2、演示站
项目演示地址: </br><a href="http://8.134.71.114:8081/"><img src="https://img.shields.io/badge/roud--blog-1.0-greed.svg"></a> &nbsp; &nbsp; &nbsp;</br>
演示站后台演示账号&密码:</br>
<img src="https://img.shields.io/badge/账号-demo@roud.top-greed.svg">&nbsp;<img src="https://img.shields.io/badge/密码-Demo123456-red.svg">
</br>
项目前台展示:</br>
![前台](http://roud.top/img/20230506004212.png)
</br>
文章展示:</br>
![文章](http://roud.top/img/20230506004439.png)
</br>
登录注册页展示:</br>
![文章](http://roud.top/img/20230506004819.png)
</br>
后台文章编辑展示:</br>
![文章](http://roud.top/img/20230506005056.png)
### 项目搭建(Project construction)
>代码拉取: git clone https://gitee.com/roud/roud-blog.git



### 涉及技术
Springboot
Vue3
Element-plus
Redis
MybatisPlus

### 项目配置
1、邮箱
在src...resources下有config目录，目录下有mail.setting配置文件，注意将邮箱配置成自己的，以免报错

### 页面路由

> 注意将端口号改成自己配置的端口号，一般默认 8080

#首页
>http://localhost:8081/index/show

#登录页
>http://localhost:8081/index/login

#注册页
>http://localhost:8081/index/reg

#后台管理用户管理页面
>http://localhost:8081/manage/user

#后台管理文章管理页面
>http://localhost:8081/manage/article

#博客编辑页
>http://localhost:8081/manage/edit

# roud—blog

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
