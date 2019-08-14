import { EntityId } from '@/api/types'
export { EntityId } from '@/api/types'

export type Atom = EntityId
export interface Counterexamples {
  [key: string]: EntityId[],
}

export interface Implication {
  head: Atom[],
  body: Atom[],
}

export interface ExplorationResult {
  counterexamples: Counterexamples,
  newImplication: Implication,
}

export interface ExplorationRequest {
  properties: EntityId[],
  counterexamples: Counterexamples,
  implications: Implication[],
  wdbound: number,
}
