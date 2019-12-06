<template>
  <sqid-bars>
    <template v-slot:mainbar>
      <div>
        <h1>The Exploration Game</h1>

        <sqid-collapsible-card id="candidate" header="Current Candidate" v-if="playing">
          <div v-if="!won">
            <div>
              <p v-if="getCandidateImplication">
                <implication :implication="getCandidateImplication" />
              </p>
            </div>

            <p>
              <b-button variant="success"
                        @click="onAccept">
                <font-awesome-icon icon="check" />{{ $t('game.acceptText') }}</b-button>
              <b-button variant="danger"
                        @click="onReject">
                <font-awesome-icon icon="times" />{{ $t('game.rejectText') }}</b-button>
            </p>
            <div v-if="candidateCounterexamples.length">
              <i18n tag="p" path="game.counterExamplesText">
                <span v-if="numCounterExamples <= 1000" place="numOfCounterExamples">{{numCounterExamples}}</span>
                <span v-else-if="numCounterExamples === 1001" place="numOfCounterExamples">{{ $t('game.tooManyCounterexamples') }}</span>
               <span v-if="numCounterExamples" place="someOfThem">{{ $t('game.someOfThem') }}</span>
              </i18n>
              <p>
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
            <i18n tag="p" path="game.congrats">
              <i place="won">{{ $t('game.won') }}</i>
            </i18n>
          </div>
        </sqid-collapsible-card>

        <sqid-collapsible-card id="setup" header="Game Setup" v-if="!playing || won">
          <teg-setup
            @start-game="onStartGame($event)" />
        </sqid-collapsible-card>

        <sqid-collapsible-card id="state" header="Game State" v-if="playing">
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
        <i18n tag="li" path="gameInstructions.stepOne" />
        <i18n tag="li" path="gameInstructions.stepTwo">
          <i place="implications">implications</i>
        </i18n>
        <i18n tag="li" path="gameInstructions.stepThree">
          <i place="accept">accept</i>
          <i place="reject">reject</i>
        </i18n>
        <i18n tag="li" path="gameInstructions.stepFour">
          <i place="counterexamples">counterexamples</i>
        </i18n>
        <i18n tag="li" path="gameInstructions.stepFive" />
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
import TegSetup from '~/components/TegSetup.vue'

@Component({
  components: {
    'implication': ImplicationVue,
    'counterexample': CounterexampleVue,
    'teg-setup': TegSetup,
  },
})
export default class TegGame extends Vue {
  @Getter private getMaxCounterexamples!: number
  @Getter private getProperties!: EntityId[]
  @Getter private getCounterexamples!: Counterexamples
  @Getter private getImplications!: Implication[]
  @Getter private getCandidateImplication!: Implication | null
  @Getter private getCandidateCounterexamples!: Counterexamples | null
  @Getter private getSessionId!: string | null
  @Action private newGame!: (configuration: GameConfiguration) => Promise<ExplorationResult>
  @Action private acceptImplication!: () => Promise<ExplorationResult>
  @Action private acceptCounterexamples!: () => Promise<ExplorationResult>
  @Mutation private rejectCounterexample!: (itemId: EntityId) => void
  @Mutation private addCounterexamples!: (counterexamples: Counterexamples) => void

  private counter: number = 0
  private numCounterExamples = 0
  private implication: Implication | null = null
  private playing = false

  @Watch('implication')
  private async onCandidateChanged() {
    if (!this.implication) {
      this.numCounterExamples = 0
    } else {
      const num = await getNumberOfCounterexamples(this.implication,
                                                   this.getSessionId || '')

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

  private onStartGame(event: any) {
    this.newGame(event)
    this.playing = true
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
