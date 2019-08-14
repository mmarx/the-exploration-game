import { GetterTree } from 'vuex'
import { TegState } from './types'
import { RootState } from '@/store/types'

export const getters: GetterTree<TegState, RootState> = {
  getMaxCounterexamples(state) {
    return state.maxCounterexamples
  },
  getProperties(state) {
    return state.properties
  },
  getCounterexamples(state) {
    return state.counterexamples
  },
  getImplications(state) {
    return state.implications
  },
  getCandidateImplication(state) {
    return state.candidate
  },
  getCandidateCounterexamples(state) {
    return state.counterCandidates
  },
}
