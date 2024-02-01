const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
});
// 跨域配置
module.exports = {
  productionSourceMap: true,
  devServer: {
    open: true,
    host: 'localhost',
    port: 8081,
    proxy: {                 //设置代理
      '/api': {              //设置拦截器 斜杠+拦截器名字
        target: 'http://127.0.0.1:8989',     //代理的目标地址
        changeOrigin: true,              //是否设置同源
        pathRewrite: {                   //路径重写
          '^/api': ''                     //选择忽略拦截器里面的内容
        }
      }
    }
  }
};
