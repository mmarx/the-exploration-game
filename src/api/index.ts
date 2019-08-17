import { Atom, Implication, EntityId, Counterexamples,
         ExplorationResult, ExplorationRequest } from '~/api/types'

import { http } from '@/http'

const backendEndpoint = '/teg/api/explore'

async function performApiRequest(request: ExplorationRequest): Promise<ExplorationResult> {
  const response = await http.get(backendEndpoint, {
    params: request,
  })

  return response.data
}

export async function getNextExplorationStep(
  properties: EntityId[],
  counterexamples: Counterexamples,
  implications: Implication[],
  sessionId: string,
  maxCounterexamples: number = 5): Promise<ExplorationResult> {
  const response = await performApiRequest({
    properties,
    counterexamples,
    implications,
    sessionId,
    maxCounterexamples,
  })

  return response
}
