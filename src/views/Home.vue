<template>
  <div class="home">
    <teg-game />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import TegGame from '~/components/TegGame.vue'

@Component({
  components: {
    'teg-game': TegGame,
  },
})
export default class Home extends Vue {}
</script>
