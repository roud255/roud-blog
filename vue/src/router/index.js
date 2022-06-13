import { createRouter, createWebHistory } from 'vue-router'
import Layout from "@/views/Layout";
import RegisterAndLogin from "@/views/RegisterAndLogin";

const routes = [
    {
      path: '/',
      name: 'Layout',
      component: Layout
    },
    {
        path: '/login',
        name: 'RegisterAndLogin',
        component: RegisterAndLogin
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
