<template>
<p><entity-link :entityId="item" />:
  <atoms :atoms="properties" compact/></p>
</template>

<script lang="ts">
import { Component, Prop, Watch, Vue } from 'vue-property-decorator'
import { Action, Getter } from 'vuex-class'
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
  @Getter private getProperties!: any
  @Action private requestLabels!: (args: { entityIds: EntityId[] }) => void
  @Action private getEntityData!: any

  private claims: any = null

  private created() {
    this.onChanged()
  }

  @Watch('item')
  @Watch('properties')
  private async onChanged() {
    const entityIds = [this.item].concat(this.properties)
    this.requestLabels({ entityIds })
    this.claims = await this.getEntityData(this.item)

    for (const property of this.getProperties) {
      if (property in this.claims.claims && !(property in this.properties)) {
        this.properties.push(property)
      }
    }
  }
}

</script>
