import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import KorisnikView from '../views/KorisnikView.vue'
import KnjigaView from '../views/KnjigaView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/korisnik',
    name: 'korisnik',
    component: KorisnikView,
    beforeEnter: (to, from, next) => {
      //to.query = { id: parseInt(localStorage.getItem('korisnik')) };
      next();
    },
  },
  {
    path: '/knjiga',
    name: 'knjiga',
    component: KnjigaView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router