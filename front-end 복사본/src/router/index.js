import { createRouter, createWebHistory } from "vue-router";
// import { useSessionStore } from "@/stores/session";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("@/views/HomeView.vue"),
    },
    {
      path: "/problem/:id",
      name: "problem-view",
      component: () => import("@/views/ProblemView.vue"),
    },
    {
      path: "/daily/:id",
      name: "daily-view",
      component: () => import("@/views/DailyView.vue"),
    },
    {
      path:'/sign',
      name:'sign',
      component:()=>import('@/views/SignView.vue')
    },
    {
      path:'/password',
      name:'password',
      component:()=>import('@/views/PasswordView.vue')
    },
    {
      path:'/univ',
      name:'univ',
      component:()=>import('@/views/Univ/UnivView.vue')
    },
    {
      path:'/groupboard',
      name:'groupboard',
      component:()=>import('@/views/Group/GroupBoardView.vue')
    },
    {
      path:'/groupfind',
      name:'groupfind',
      component:()=>import('@/views/Group/GroupFindView.vue')
    },
    {
      path:'/groupcreate',
      name:'groupcreate',
      component:()=>import('@/views/Group/GroupCreateView.vue')
    },
    {
      path:'/user/:id',
      name:'user',
      component:()=>import('@/views/User/UserView.vue')
    },
    {
      path:'/create',
      name:'create',
      component:()=>import('@/views/Univ/CreateView.vue')
    },
    {
      path:'/group/:id',
      name:'group',
      component:()=>import('@/views/Group/GroupView.vue')
    },
    {
      path:'/groupAddProb/:id',
      name:'groupAddProb',
      component:()=>import('@/views/Group/GroupAddProbView.vue')
    },
    {
      path:'/test',
      name:'test',
      component:()=>import('@/views/Group/GroupView.vue')
    },
  ],
});

// router.beforeEach((to, from, next) => {
//   const sessionStore = useSessionStore();
//   sessionStore.session()
//   const isAuthenticated = sessionStore.state.isLoggedIn;

//   if(!isAuthenticated && to.name !== 'home'){
//     next({name: 'home'});
//   } else {
//     next();
//   }
// })
export default router;
