import { createWebHistory, createRouter } from "vue-router";
import Home from "./components/Home.vue";
import Login from "./components/Login.vue";
import UserList from "./components/UserList.vue"
import Register from "./components/Register.vue"; 
import PostList from "./components/PostList.vue";

const Profile = () => import("./components/Profile.vue")
const BoardAdm = () => import("./components/BoardAdm.vue")
const BoardStudent = () => import("./components/BoardStudent.vue")
const BoardTeacher = () => import("./components/BoardTeacher.vue")

const routes = [
  { 
    path: '/', 
    redirect: '/login' 
  },
  {
    path: "/home",
    name: "home",
    component: Home
  },
  {
    path: "/login",
    component: Login
  },
  {
    path: "/post-list",
    component: PostList
  },
  {
    path: "/user-list",
    component: UserList
  },
  {
    path: "/register",
    component: Register,
  },
  {
    path: "/profile",
    name: "profile", 
    component: Profile,
  },
  {
    path: "/adm",
    name: "adm", 
    component: BoardAdm,
  },
  {
    path: "/teacher",
    name: "teacher", 
    component: BoardTeacher,
  },
  {
    path: "/student",
    name: "student", 
    component: BoardStudent,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/register'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem('user');

  if (authRequired && !loggedIn) {
    next('/login');
  } else {    
    next();
  }
});

export default router;