import { Atom, Implication, EntityId, Counterexamples,
         ExplorationResult, ExplorationRequest } from '~/api/types'

import { http } from '@/http'

const backendEndpoint = 'http://127.0.0.1:4223/explore'

function performApiRequest(request: ExplorationRequest): ExplorationResult {
  return http.post(endpoint, {
    params: request,
  })
}

export async function getNextExplorationStep(
  properties: EntityId[],
  counterexamples: Counterexamples,
  implications: Implication[],
  maxCounterexamples: number = 5): ExplorationResult {
  const response = await performApiRequest({
    properties:
    counterexamples,
    implications,
    wdbound: maxCounterexamples,
  })
}
