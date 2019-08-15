<template>
  <sqid-bars>
    <template v-slot:mainbar>
      <div>
        <h1>The Exploration Game</h1>

        <div><h2>current candidate</h2>
          <p v-if="getCandidateImplication">
            <implication :implication="getCandidateImplication" />
          </p>
        </div>

        <div><h2>possible counterexamples</h2>
          <p>
            <ul v-if="getCandidateCounterexamples">
              <li v-for="(itemId, idx) of Object.keys(getCandidateCounterexamples)" :key="idx">
                <counterexample :item="itemId" :properties="getCandidateCounterexamples[itemId]" />
              </li>
            </ul>
          </p>
        </div>

        <div><h2>properties</h2>
          <p>
            <ul class="comma-separated">
              <li v-for="(propertyId, idx) of getProperties" :key="idx">
                <entity-link :entityId="propertyId" />
              </li>
            </ul>
          </p>
        </div>

        <div><h2>implications found so far</h2>
          <p>
            <ul class="comma-separated">
              <li v-for="(implication, idx) of getImplications" :key="idx">
                <implication :implication="implication" />
              </li>
            </ul>
          </p>
        </div>

        <div><h2>counterexamples added so for</h2>
          <p>
            <ul>
              <li v-for="(itemId, idx) of Object.keys(getCounterexamples)" :key="idx">
                <counterexample :item="itemId" :properties="getCounterexamples[itemId]" />
              </li>
            </ul>
          </p>
        </div>

        <b-button @click="onAccept">accept</b-button>
        <b-button @click="onReject">reject</b-button>
      </div>
    </template>
  </sqid-bars>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { Getter, Action, Mutation } from 'vuex-class'
import { EntityId, Counterexamples, Implication,
         ExplorationResult, GameConfiguration } from '~/api/types'
import ImplicationVue from '~/components/Implication.vue'
import CounterexampleVue from '~/components/Counterexample.vue'

@Component({
  components: {
    implication: ImplicationVue,
    counterexample: CounterexampleVue,
  },
})
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
    const counterexample: Counterexamples = { [item]: candidate.body }
    this.addCounterexamples(counterexample)
    this.acceptCounterexamples()
  }
}
</script>
