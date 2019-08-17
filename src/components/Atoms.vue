<template>
  <span>
    <p v-if="!compact">
      <ol v-if="atoms.length">
        <li v-for="(atom, idx) of atoms" :key="idx">
          {{ atom }}: <entity-link :entityId="atom" />
        </li>
      </ol>
      <span v-else>
        <b-tooltip title="this implication applies to every item">[empty]</b-tooltip>
      </span>
    </p>
    <span v-else>
      <ul class="comma-separated" v-if="atoms.length">
        <li v-for="(atom, idx) of atoms" :key="idx">
          <entity-link :entityId="atom" />
        </li>
      </ul>
      <span v-else>
        <span v-b-tooltip title="this implication applies to every item">[empty]</span>
      </span>
    </span>
  </span>
</template>


<script lang="ts">
import { Component, Prop, Watch, Vue } from 'vue-property-decorator'
import { Action } from 'vuex-class'
import { EntityId } from '~/api/types'

@Component
export default class Atoms extends Vue {
  @Prop({ required: true }) private atoms!: EntityId[]
  @Prop({ default: false, type: Boolean }) private compact!: boolean
  @Action private requestLabels!: ({}: { entityIds: EntityId[] }) => void

  private created() {
    this.onAtomsChanged()
  }

  @Watch('atoms')
  private onAtomsChanged() {
    this.requestLabels({ entityIds: this.atoms })
  }
}
</script>
