import { EntityId, Atom, Counterexamples, Implication } from '~/api/types'

export interface TegState {
  maxCounterexamples: number,
  properties: EntityId[],
  counterexamples: Counterexamples,
  implications: Implication[],
  candidate: Implication | null,
  counterCandidates: Counterexamples | null,
}
