<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App"/>

    <sqid-image file="Octopus salutii Merculiano.jpg" width="520" />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import HelloWorld from '~/components/HelloWorld.vue'
import SqidImage from '@/components/SqidImage.vue'

@Component({
  components: {
    HelloWorld,
    SqidImage,
  },
})
export default class Home extends Vue {}
</script>
