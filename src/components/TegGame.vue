<template>
  <sqid-bars>
    <template v-slot:mainbar>
      <div>
        <h1>The Exploration Game</h1>

        <sqid-collapsible-card id="candidate" header="Current Candidate">
          <div v-if="!won">
            <div>
              <p v-if="getCandidateImplication">
                <implication :implication="getCandidateImplication" />
              </p>
            </div>

            <div v-if="candidateCounterexamples.length">
              <p>There are items that do not satisfy this
                implication. Are they valid counterexamples?
                <ul>
                  <li v-for="([itemId, properties], idx) of candidateCounterexamples"
                      :key="idx">
                    <counterexample :item="itemId" :properties="properties" />
                  </li>
                </ul>
              </p>
            </div>

            <p>
              <b-button variant="success"
                        @click="onAccept">
                <font-awesome-icon icon="check" />accept</b-button>
              <b-button variant="danger"
                        @click="onReject">
                <font-awesome-icon icon="times" />reject</b-button>
            </p>
          </div>
          <div v-else>
            <p>Congratulations, you have <i>won</i> The Exploration Game!</p>
          </div>
        </sqid-collapsible-card>

        <sqid-collapsible-card id="state" header="Game State">
          <b-tabs card>
            <b-tab title="Implications">
              <p>
                <ol>
                  <li v-for="(implication, idx) of getImplications" :key="idx">
                    <implication :implication="implication" />
                  </li>
                </ol>
              </p>

            </b-tab>
            <b-tab active title="Properties">
              <p>
                <ol>
                  <li v-for="(propertyId, idx) of getProperties" :key="idx">
                    <entity-link :entityId="propertyId" />
                  </li>
                </ol>
              </p>
            </b-tab>
            <b-tab title="Counterexamples">
              <p>
                <ol>
                  <li v-for="(itemId, idx) of Object.keys(getCounterexamples)" :key="idx">
                    <counterexample :item="itemId" :properties="getCounterexamples[itemId]" />
                  </li>
                </ol>
              </p>
            </b-tab>
          </b-tabs>
        </sqid-collapsible-card>
      </div>
    </template>
    <template v-slot:sidebar>
      <h2>How to play</h2>
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

  private get candidateCounterexamples() {
    const candidates = this.getCandidateCounterexamples || {}

    return Object.entries(candidates)
  }

  private get won() {
    const candidate = this.getCandidateImplication

    return candidate && !candidate.head.length && !candidate.body.length
  }

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

<style lang="less" scoped>
button {
  margin-left: .5em;
  margin-right: .5em;
}
</style>
