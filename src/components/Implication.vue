<template>
<p><atoms :atoms="implication.body" />
  -&gt;
  <atoms :atoms="implication.head" /></p>
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
