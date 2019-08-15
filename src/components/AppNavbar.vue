<template>
  <b-navbar toggleable="lg" type="light" fill>
    <b-navbar-brand to="/">
      <span><sqid-image file="Wikidata-logo-with-magnifying-glass-icon.svg" width="100" />
        The Exploration Game</span>
    </b-navbar-brand>
    <b-navbar-toggle target="mainNavbar" />
    <b-collapse is-nav id="mainNavbar">
      <b-navbar-nav id="mainNavbar" class="justify-content-end flex-grow-1">
        <app-navbar-navlinks :navlinks="navlinks" />
        <!-- <app-navbar-login /> -->
      </b-navbar-nav>
    </b-collapse>
</b-navbar>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { AppNavbarNavlinks, Navlink } from '@/components/AppNavbarNavlinks.vue'

@Component({
    components: {
        'app-navbar-navlinks': AppNavbarNavlinks,
    }})
export default class AppNavbar extends Vue {
    private navlinks: Navlink[] = [
      { target: {name: 'home'}, message: 'pageTitle.start', exact: true },
      { target: {name: 'about'}, message: 'pageTitle.about' },
    ]
}
</script>
