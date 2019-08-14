<template>
  <div class="hello">
    <p>
      <span>{{ result }}</span>
    </p>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import { ExplorationResult } from '~/api/types'
import { getNextExplorationStep } from '~/api/index'

@Component
export default class HelloWorld extends Vue {
  @Prop() private msg!: string
  private result: string | null = null

  private created() {
    getNextExplorationStep(['P1', 'P3', 'P4', 'P10', 'P20', 'P30'],
                           { Q1234: ['P4', 'P3', 'P10'],
                             Q1339: ['P4', 'P30', 'P10'],
                           },
                           [{ head: ['P10'], body: ['P3', 'P4'] },
                            { head: ['P30'], body: ['P4', 'P1'] }])
      .then((result: ExplorationResult) => {
        this.result = result as any
      })
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
