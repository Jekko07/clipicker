<template>
  <q-layout>
    <q-page-container>
      <router-view />
    </q-page-container>

  </q-layout>
</template>

<script>
import '@okta/okta-signin-widget/dist/css/okta-sign-in.min.css';
import '@okta/okta-signin-widget/dist/css/okta-theme.css';


export default {
  name: 'ClinicLayout',
  created() {
    this.authenticate();
  },
  methods: {
    async authenticate () {
      this.$router.push({
        path: '/clinic'
      })
    }
  },
}
</script>

<style lang="stylus" scoped>
</style>
