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

            <p>
              <b-button variant="success"
                        @click="onAccept">
                <font-awesome-icon icon="check" />accept this implication</b-button>
              <b-button variant="danger"
                        @click="onReject">
                <font-awesome-icon icon="times" />reject this implication</b-button>
            </p>

            <div v-if="candidateCounterexamples.length">
              <p>There are <span v-if="numCounterExamples">{{ numCounterExamples }} </span>items that do not satisfy this
                implication. <span v-if="numCounterExamples">Here are some of them. </span>Are they valid counterexamples?
                <ul>
                  <li v-for="([itemId, properties], idx) of candidateCounterexamples"
                      :key="idx">
                    <counterexample :item="itemId" :properties="properties" />
                  </li>
                </ul>
              </p>
            </div>
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
      <ol>
        <li>Select a set of properties to play with (currently, we
          only offer a pre-selected set here)</li>
        <li>The Exploration game will ask you questions
        about <i>implications</i>: Considering all items with
        statements for a given set of properties, do they all also
        have statements for the properties in the conclusion of the
          implication?</li>
        <li>For every such implication, you can choose
        to <i>accept</i> it, if it is a valid implication,
        or <i>reject</i> it, if is not.</li>
        <li>To guide you in your decision, you will
        see <i>counterexamples</i>, i.e., items that satisfy the
        premise of the implication, but not the
        conclusion. Counterexamples need not be valid, they might also
          be missing statements.</li>
        <li>Based on your choice, The Exploration Game computes the
        next possible implication that is consistent with your
        choices, until all minimal implications have been found.</li>
      </ol>
    </template>
  </sqid-bars>
</template>

<script lang="ts">
import { Component, Prop, Watch, Vue } from 'vue-property-decorator'
import { Getter, Action, Mutation } from 'vuex-class'
import { EntityId, Counterexamples, Implication,
         ExplorationResult, GameConfiguration } from '~/api/types'
import { getNumberOfCounterexamples } from '~/api/index'
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
  private numCounterExamples = 0
  private implication: Implication | null = null

  @Watch('implication')
  private async onCandidateChanged() {
    if (!this.implication) {
      this.numCounterExamples = 0
    } else {
      const num = await getNumberOfCounterexamples(this.implication)

      this.numCounterExamples = num
    }
  }

  private get candidateCounterexamples() {
    const candidates = this.getCandidateCounterexamples || {}

    return Object.entries(candidates)
  }

  private get won() {
    this.implication = this.getCandidateImplication

    return this.implication && !this.implication.head.length && !this.implication.body.length
  }

  private created() {
    this.newGame({
      properties: ['P27', 'P463', 'P101', 'P31', 'P106'],
      maxCounterexamples: 5,
    })
  }

  private onAccept() {
    this.acceptImplication()
  }

  private onReject() {
    this.implication = this.getCandidateImplication || { head: [], body: [] }
    const counters = this.candidateCounterexamples || []

    if (!counters.length) {
      ++this.counter
      const item = `artificial-counterexample-${this.counter}`
      const counterexample: Counterexamples = { [item]: this.implication.body }
      this.addCounterexamples(counterexample)
    }

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
