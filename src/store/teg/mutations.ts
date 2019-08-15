import Vue from 'vue'
import { MutationTree } from 'vuex'
import { TegState } from './types'
import { EntityId, Counterexamples,
         GameConfiguration, ExplorationResult } from '~/api/types'
import { entityValue } from '@/api/sparql'

export const mutations: MutationTree<TegState> = {
  newGame(state, configuration) {
    state.maxCounterexamples = configuration.maxCounterexamples
    state.properties = configuration.properties
    state.implications = []
    state.counterexamples = {}
    state.candidate = null
    state.counterCandidates = null
  },
  newCandidate(state, result) {
    state.candidate = result.newImplication || null
    state.counterCandidates = null

    if (result.counterexamples) {
      const properties = result.newImplication.body || null
      const counters = {}

      for (const item of result.counterexamples) {
        counters[entityValue(item)] = properties
      }

      state.counterCandidates = counters
    }
  },
  acceptImplication(state) {
    if (state.candidate) {
      state.implications.push(state.candidate!)
    }
    state.candidate = null
    state.counterCandidates = null
  },
  addCounterexamples(state, counterexamples) {
    if (state.counterCandidates) {
      Object.assign(state.counterCandidates, counterexamples)
    } else {
      state.counterCandidates = counterexamples
    }
  },
  acceptCounterexamples(state) {
    if (state.counterCandidates) {
      state.counterexamples = Object.assign(state.counterexamples,
                                            state.counterCandidates!)
    }
    state.candidate = null
    state.counterCandidates = null
  },
  rejectCounterexample(state, itemId: EntityId) {
    if (state.counterCandidates && itemId in state.counterCandidates) {
      Vue.delete(state.counterCandidates, itemId)
    }
  },
}
