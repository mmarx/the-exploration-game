<template>
  <div id="footer">
    <hr />
    <div class="container-fluid">
      <div class="row justify-content-between">
        <div class="col-md-6">
          <p></p>
        </div>
        <div class="col-md-6">
          <p>{{ $t('footer.poweredBy') }}
            <a href="https://query.wikidata.org/">Wikidata SPARQL Query</a>,
            <a href="https://github.com/Wikidata/SQID">SQID</a> &amp;
            <a href="https://github.com/tomhanika/conexp-clj">conexp-clj</a>.
          </p>
          <i18n path="footer.sourceCode" tag="p">
            <a href="https://github.com/mmarx/the-exploration-game">Github</a>
          </i18n>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
#footer {
 text-align: center;
}
</style>

<script lang="ts">
import { Component, Watch, Vue } from 'vue-property-decorator'
import { Action, Getter, namespace } from 'vuex-class'

const statistics = namespace('statistics')

@Component
export default class AppFooter extends Vue {
  @Action private setCurrentTranslation!: any

  private created() {
    this.onRouteChanged()
  }

  @Watch('$route')
  private onRouteChanged() {
    this.setCurrentTranslation()
  }
}
</script>
