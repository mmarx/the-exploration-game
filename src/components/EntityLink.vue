<template>
  <a  :href="destination"
      :title="tooltip"
      @click="$event.stopImmediatePropagation()">{{ label }}</a>
</template>

<script lang="ts">
import { Component, Prop, Watch, Vue } from 'vue-property-decorator'
import { Action } from 'vuex-class'
import { EntityId } from '@/store/entity/claims/types'
import { i18n } from '@/i18n'

@Component
export default class EntityLink extends Vue {
  @Prop({ required: true }) private entityId!: EntityId
  @Action private getLabel: any
  private label: string = this.entityId

  private get destination() {
    return `https://www.wikidata.org/entity/${this.entityId}`
  }

  private get tooltip() {
    return `${this.label} (${this.entityId})`
  }

  private get language() {
    return i18n.locale
  }

  private async updateLabel() {
    this.label = await this.getLabel({
      entityId: this.entityId,
      lang: this.language,
    })
  }

  private created() {
    this.updateLabel()
  }

  @Watch('entityId')
  @Watch('language')
  private onEntityIdChanged() {
    this.updateLabel()
  }
}
</script>
