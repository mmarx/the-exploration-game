import { Atom, Implication, EntityId, Counterexamples,
         ExplorationResult, ExplorationRequest } from '~/api/types'

import { http } from '@/http'

const backendEndpoint = '/teg/api/'

async function performApiRequest(action: string,
                                 request: ExplorationRequest): Promise<ExplorationResult> {
  const response = await http.get(`${backendEndpoint}${action}`, {
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
  const response = await performApiRequest('explore', {
    properties,
    counterexamples,
    implications,
    sessionId,
    maxCounterexamples,
  })

  return response
}

export async function getNumberOfCounterexamples(implication: Implication) {
  const response = await performApiRequest('howmany', {
    implication,
    sessionId,
  } as any)

  return (response as any).numberOfCounterexamples as number || 0
}
