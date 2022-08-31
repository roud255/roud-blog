import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import '@/assets/css/global.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//mdeditor
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';


//中文包
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import Prism from 'prismjs';
//mdeditor
VueMarkdownEditor.use(vuepressTheme, {
    Prism,
});

const app = createApp(App)
// 图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(store).use(VueMarkdownEditor).use(router).use(ElementPlus, {locale: zhCn,}).mount('#app')
