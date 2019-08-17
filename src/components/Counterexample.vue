<template>
<p><entity-link :entityId="item" />: <atoms :atoms="properties" compact/></p>
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
  @Prop({ required: true }) private item!: EntityId
  @Prop({ required: true }) private properties!: EntityId[]
  @Action private requestLabels!: (args: { entityIds: EntityId[] }) => void

  private created() {
    this.onChanged()
  }

  @Watch('item')
  @Watch('properties')
  private onChanged() {
    const entityIds = [this.item].concat(this.properties)
    this.requestLabels({ entityIds })
  }
}

</script>
