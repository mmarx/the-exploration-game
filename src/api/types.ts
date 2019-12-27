import { EntityId } from '@/store/entity/claims/types'
export { EntityId } from '@/store/entity/claims/types'

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
  maxCounterexamples: number,
  sessionId: string,
}

export interface GameConfiguration {
  properties: EntityId[],
  maxCounterexamples: number,
}

export interface Preset {
  description: string,
  properties: EntityId[],
}

export class GameOverError extends Error {
  constructor() {
    super('Game over, Player one. Be vigilant.')
  }
}
