<template>
  <span>
    <ul>
      <li v-for="(atom, idx) of atoms" :key="idx">
        <entity-link :entityId="atom" />
      </li>
    </ul>
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
