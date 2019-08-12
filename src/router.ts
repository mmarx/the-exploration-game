import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Progress from '@/progress'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue'),
    },
  ],
})

router.beforeResolve((_to, _from, next) => {
  Progress.start()
  next()
})

router.beforeEach((to, _from, next) => {
  const segments = to.matched.slice().reverse()
  const title = segments.find((segment) => segment.meta && segment.meta.title)

  if (title) {
    document.title = title.meta.title
  }

  next()
})

router.afterEach((_to, _from) => Progress.done())

export default router
