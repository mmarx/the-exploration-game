import Vue from 'vue'
import Vuex, { StoreOptions } from 'vuex'
import { RootState } from '@/store/types'
import { i18n } from '@/store/i18n/index'
import { login } from '@/store/login/index'
import { entity } from '@/store/entity/index'
import { statistics } from '@/store/statistics/index'
import { teg } from './teg/index'

Vue.use(Vuex)


const store: StoreOptions<RootState> = {
  strict: process.env.NODE_ENV !== 'production',
  modules: {
    i18n,
    login,
    entity,
    statistics,
    teg,
  },
}

export default new Vuex.Store<RootState>(store)
