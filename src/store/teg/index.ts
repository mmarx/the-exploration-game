import { Module } from 'vuex'
import { actions } from './actions'
import { mutations } from './mutations'
import { getters } from './getters'
import { TegState } from './types'
import { RootState } from '@/store/types'

export const state: TegState = {
  maxCounterexamples: 5,
  properties: [],
  counterexamples: {},
  implications: [],
  candidate: null,
  counterCandidates: null,
}

export const teg: Module<TegState, RootState> = {
  state,
  mutations,
  actions,
  getters,
}
