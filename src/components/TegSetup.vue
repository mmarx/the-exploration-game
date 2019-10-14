<template>
  <b-container fluid>
    <b-row>
      <b-col>
        <h2>Properties</h2>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <property-search-box
          @selected-property="onSelectedProperty($event.id)"
          />
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="3" />
      <b-col sm="9">
        <ol>
          <li v-for="(propertyId, idx) of properties" :key="idx">
            <entity-link :entityId="propertyId" />
            <b-button variant="danger"
                      @click="onRemove(propertyId)">
              <font-awesome-icon icon="times" />remove</b-button>
          </li>
        </ol>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <h2>Game</h2>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="3">
        <label for="counterexamples">Number of Counterexamples</label>
      </b-col>
      <b-col sm="9">
        <b-form-input v-model="maxCounterexamples"
                      placeholder="Number of counterexamples"
                      type="number" />
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="3" />
      <b-col sm="9">
        <b-button variant="success"
                  @click="onStartGame"
                  :disabled="properties.length === 0">
          <font-awesome-icon icon="check" />start the game!</b-button>
        <span v-if="properties.length === 0">add at least one property to start the game</span>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { EntityId } from '@/api/types'
import TegPropertySearchBox from '~/components/PropertySearchBox.vue'

@Component({
  components: {
    'property-search-box': TegPropertySearchBox,
  },
})
export default class TegSetup extends Vue {
  private properties: EntityId[] = []
  private maxCounterexamples: number = 5

  private onSelectedProperty(propertyId: EntityId) {
    if (!this.properties.includes(propertyId)) {
      this.properties.push(propertyId)
    }
  }

  private onRemove(propertyId: EntityId) {
    this.properties = this.properties.filter((elt) => elt !== propertyId)
  }

  private onStartGame() {
    const properties = this.properties
    const maxCounterexamples = this.maxCounterexamples

    this.$emit('start-game', { properties,
                               maxCounterexamples,
                             })

  }
}
</script>

<style lang="less" scoped>
button {
  margin-left: .5em;
  margin-right: .5em;
  margin-bottom: .25em;
}

svg {
  margin-right: 1em;
}

ol li {
  margin-top: .25em;
  margin-bottom: .25em;
}
</style>
