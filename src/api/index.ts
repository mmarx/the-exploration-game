import { Atom, Implication, EntityId, Counterexamples,
         ExplorationResult, ExplorationRequest } from '~/api/types'

import { http } from '@/http'
import { sparqlRequest } from '@/api'
import { SparqlBinding } from '@/api/types'
import { sparqlEndpoint } from '@/api/endpoints'

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

export async function sparqlQuery(query: string): Promise<SparqlBinding[]> {
  const response = await sparqlRequest(sparqlEndpoint,
                                       `#TOOL:TEG, https://tools.wmflabs.org/teg/
${query}`)
  return response.results.bindings
}

export type CounterExampleQueryType = AskType | LimitedCounterExampleQueryType

export interface AskType {
  type: 'ask'
}

export interface LimitedCounterExampleQueryType {
  type: 'count' | 'retrieve'
  limit?: number
}

interface CountResult {
  entities: { datatype: string,
              type: 'literal',
              value: number,
            },
}

export function counterExampleQueryForImplication(implication: Implication,
                                                  queryType: CounterExampleQueryType) {
  let verb
  let limit

  switch (queryType.type) {
    case 'ask':
      verb = 'ASK'
      break

    case 'count':
      verb = 'SELECT (COUNT(DISTINCT ?entity) AS ?entities) WHERE { SELECT ?entity '
      limit = ((queryType.limit !== null)
               ? `LIMIT ${queryType.limit} }`
               : '')
      break

    case 'retrieve':
      verb = 'SELECT DISTINCT ?entity'
      limit = ((queryType.limit !== null)
               ? `LIMIT ${queryType.limit}`
               : '')
      break
  }

  const premise = patternForPremise(implication.body)
  const conclusion = patternForConclusion(implication.head)

  return `${verb} WHERE {
  ${premise}
  ${conclusion}
} ${limit}`
}

export function supportQueryForImplication(implication: Implication, limit: number) {
  const atoms = implication.body.concat(implication.head)
  const rule = patternForPremise(atoms)

  return `SELECT (COUNT(DISTINCT ?entity) AS ?entities) WHERE {
  ${rule}
} LIMIT ${limit}`
}

function patternForPremise(body: Atom[]) {
  if (!body.length) {
    return `?entity ?someProperty [] .
[] wikibase:directClaim ?someProperty`
  }

  const clauses = body.map((property) => `wdt:${property} []`).join(';\n    ')

  return `?entity
${clauses} .`
}

function patternForConclusion(head: Atom[]) {
  return head.map((property) => `{ FILTER NOT EXISTS { ?entity wdt:${property} [] . } }`).join(' UNION\n  ')
}

export async function getNumberOfCounterexamples(implication: Implication,
                                                 _: string) {
  const response = await sparqlQuery(counterExampleQueryForImplication(implication,
                                                                       { type: 'retrieve', limit: 1001 }))
  return response.length
}

export async function getSupport(implication: Implication) {
  const response = await sparqlQuery(supportQueryForImplication(implication, 1001))

  return Number.parseInt(response[0].entities.value, 10)
}
