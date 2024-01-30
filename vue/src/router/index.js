import { createRouter, createWebHistory } from 'vue-router'
import Manage from "@/views/Manage";
import Index from "@/views/Index";
import IndexShow2 from "@/views/IndexShow2";
import Editor from "@/views/Article";
import NotFound from "@/views/NotFound";
import ForBidIPManage from "@/components/manage/ForBidIPManage";
import ErrorComputerModel from "@/views/ErrorComputerModel";
import AccountInformation from "@/components/AccountInformation";
import CommentsManage from "@/components/manage/CommentsManage";
import ForBidIPHistory from "@/components/manage/ForBidIPHistory";

const routes = [
    {
      path: '/manage',
      name: 'Manage',
      component: Manage,
      children:[
          {
              path: 'user',
              name: 'UserManage',
              component: ()=>import("@/components/manage/UserManage")
          },
          {
              path: 'article',
              name: 'ArticleManage',
              component: ()=>import("@/components/manage/ArticleManage")
          },
          {
              path: 'edit',
              name: 'ArticleEdit',
              component: ()=>import("@/components/manage/ArticleEditor")
          },
          {
              path: 'tags',
              name: 'TagManage',
              component: ()=>import("@/components/manage/TagManage")
          },
          {
              path: 'forbidip',
              name: 'ForbidIP',
              component: ForBidIPManage
          },
          {
              path: 'forbidipHistory',
              name: 'ForBidIPHistory',
              component: ForBidIPHistory
          },
          {
              path: 'comment',
              name: 'comment',
              component: CommentsManage
          }

      ]
    },

    {
        path: '/index',
        name: 'Index',
        component: Index,
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
        path: '/show',
        name: 'IndexShow2',
        component: IndexShow2,
    },
    {
        path: '/article',
        name: 'Article',
        component: Editor,
        children: [
            {
                path: 'show',
                name: 'show',
                component: ()=>import("@/components/ArticleShow")

            }
        ]
    },

    /*
    测试
     */
    {
        path: '/test',
        name: 'test',
        component: AccountInformation
    },
    {
        path: '/',
        name: 'default',
        component: IndexShow2,
    },
    {
        path: '/errorcomputermodel',
        name: 'errorcomputermodel',
        component: ErrorComputerModel,
    },
    {
        name: 'NotFound',
        path: '/:pathMatch(.*)*',
        component: NotFound
    },
]
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
