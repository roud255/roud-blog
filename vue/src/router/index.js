import { createRouter, createWebHistory } from 'vue-router'
import Manage from "@/views/Manage";
import RegisterAndLogin from "@/views/RegisterAndLogin";
import Index from "@/components/Index";
import Editor from "@/views/Article";

const routes = [
    {
      path: '/',
      name: 'Manage',
      component: Manage,
      children:[
          {
              path: 'user',
              name: 'UserManage',
              component: ()=>import("@/components/UserManage")
          }
      ]
    },

    {
        path: '/index',
        name: 'RegisterAndLogin',
        component: RegisterAndLogin,
        children: [
            {
                path: 'register',
                name: 'Register',
                component: ()=>import("@/components/Register")
            },
            {
                path: 'login',
                name: 'Login',
                component: ()=>import("@/components/Login")
            },
        ]
    },
    {
        path: '/test',
        name: 'Index',
        component: Index,
        // children: [
        //     {
        //         path: 'register',
        //         name: 'Register',
        //         component: ()=>import("@/components/Register")
        //     },
        //     {
        //         path: 'login',
        //         name: 'Login',
        //         component: ()=>import("@/components/Login")
        //     },
        // ]
    },
    {
        path: '/article',
        name: 'Article',
        component: Editor,
        children: [
            {
                path: 'edit',
                name: 'edit',
                component: ()=>import("@/components/ArticleEditor")
            },
            {
                path: 'show',
                name: 'show',
                component: ()=>import("@/components/ArticleShow")
            }
        ]
    },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
