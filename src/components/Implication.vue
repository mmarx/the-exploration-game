<template>
  <div>
    <p v-if="!compact">
      <span>Is it true that every
      item</span> <span v-if="implication.body.length"> with statements for
        <atoms :atoms="implication.body" /></span>
      <span> also has statements for all of </span>
      <atoms :atoms="implication.head" /><span>?</span>
    </p>
    <p>
      <atoms :atoms="implication.body" />
      <font-awesome-icon icon="arrow-right" />
      <atoms :atoms="implication.head" />
    </p>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Watch, Vue } from 'vue-property-decorator'
import { Action } from 'vuex-class'
import { EntityId } from '~/api/types'
import { Implication as ImplicationType } from '~/api/types'
import Atoms from '~/components/Atoms.vue'

@Component({
  components: {
    Atoms,
  },
})
export default class Implication extends Vue {
  @Prop({ required: true }) private implication!: ImplicationType
  @Prop({ default: false, type: Boolean }) private compact!: boolean
  @Action private requestLabels!: (args: { entityIds: EntityId[] }) => void

  private created() {
    this.onImplicationChanged()
  }

  @Watch('implication')
  private onImplicationChanged() {
    const entityIds = this.implication.body.concat(this.implication.head)
    this.requestLabels({ entityIds })
  }
}

</script>

<style lang="less" scoped>
svg {
  margin-left: .5em;
  margin-right: .5em;
}
</style>
