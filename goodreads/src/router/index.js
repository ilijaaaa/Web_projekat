import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import ProfilView from '../views/ProfilView.vue'
import KnjigaView from '../views/KnjigaView.vue'
import KorisnikView from '../views/KorisnikView.vue'
import PretragaKnjigeView from '../views/PretragaKnjigeView.vue'
import ZanrView from '../views/ZanrView.vue'
import KnjigaZanrView from '../views/KnjigaZanrView.vue'
import ZahtevView from '../views/ZahtevView.vue'

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
    path: '/profil',
    name: 'profil',
    component: ProfilView,
    beforeEnter: (to, from, next) => {
      to.query = { sessionId: localStorage.getItem('korisnik') };
      next();
    }
  },
  {
    path: '/knjiga',
    name: 'knjiga',
    component: KnjigaView
  },
  {
    path: '/korisnik',
    name: 'korisnik',
    component: KorisnikView
  },
  {
    path: '/pretragaKnjige',
    name: 'pretragaKnjige',
    component: PretragaKnjigeView
  },
  {
    path: '/zanrovi',
    name: 'zanrovi',
    component: ZanrView
  },
  {
    path: '/knjigeZanr',
    name: 'knjigeZanr',
    component: KnjigaZanrView
  },
  {
    path: '/zahtev',
    name: 'zahtev',
    component: ZahtevView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router