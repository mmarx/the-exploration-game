import { ActionTree } from 'vuex'
import { TegState } from './types'
import { RootState } from '@/store/types'
import { getNextExplorationStep } from '~/api/index'
import { Counterexamples, GameOverError } from '~/api/types'

async function step(state: TegState) {
  const result = await getNextExplorationStep(state.properties,
                                              state.counterexamples,
                                              state.implications,
                                              state.sessionId || '',
                                              state.maxCounterexamples)

  if (!result.newImplication || !result.newImplication.head) {
    throw new GameOverError()
  }

  return result
}

export const actions: ActionTree<TegState, RootState> = {
  async newGame({ commit, state }, configuration) {
    commit('newGame', configuration)

    const candidate = await step(state)
    commit('newCandidate', candidate)

    return candidate
  },
  async acceptImplication({ commit, state }) {
    commit('acceptImplication')
    const candidate = await step(state)
    commit('newCandidate', candidate)

    return candidate
  },
  async rejectImplication({ commit, state, getters, dispatch }, counterexamples: Counterexamples) {
    const counters: any = counterexamples
    const data: any = {}
    const props = getters.getProperties()

    for (const item of Object.keys(counters)) {
      data[item] = await dispatch('getEntityData', item) as any
      const properties = []

      for (const property of props) {
        if (property in data[item].claims && !(property in properties)) {
          properties.push(property)
        }
      }

      counters[item] = properties
    }

    commit('addCounterexamples', counters)
    commit('acceptCounterexamples')
    const candidate = await step(state)
    commit('newCandidate', candidate)

    return candidate
  },
  async acceptCounterexamples({ commit, state }) {
    commit('acceptCounterexamples')

    const candidate = await step(state)
    commit ('newCandidate', candidate)

    return candidate
  },
}
