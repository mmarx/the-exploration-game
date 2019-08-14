<template>
  <div>
    <h1>headline</h1>

    <p>{{ getCandidateImplication }}</p>
    <p>{{ getCandidateCounterexamples }}</p>

    <b-button @click="onAccept">accept</b-button>
    <b-button @click="onReject">reject</b-button>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { Getter, Action, Mutation } from 'vuex-class'
import { EntityId, Counterexamples, Implication,
         ExplorationResult, GameConfiguration } from '~/api/types'

@Component
export default class TegGame extends Vue {
  @Getter private getMaxCounterexamples!: number
  @Getter private getProperties!: EntityId[]
  @Getter private getCounterexamples!: Counterexamples
  @Getter private getImplications!: Implication[]
  @Getter private getCandidateImplication!: Implication | null
  @Getter private getCandidateCounterexamples!: Counterexamples | null
  @Action private newGame!: (configuration: GameConfiguration) => Promise<ExplorationResult>
  @Action private acceptImplication!: () => Promise<ExplorationResult>
  @Action private acceptCounterexamples!: () => Promise<ExplorationResult>
  @Mutation private rejectCounterexample!: (itemId: EntityId) => void
  @Mutation private addCounterexamples!: (counterexamples: Counterexamples) => void

  private counter: number = 0

  private created() {
    this.newGame({
      properties: ['P161', 'P364', 'P495'],
      maxCounterexamples: 5,
    })
  }

  private onAccept() {
    this.acceptImplication()
  }

  private onReject() {
    const candidate = this.getCandidateImplication || { head: [], body: [] }
    ++this.counter
    const item = `counter-${this.counter}`
    const counterexample: Counterexamples = { item: candidate.body }
    this.addCounterexamples(counterexample)
    this.acceptCounterexamples()
  }
}
</script>
