<template>
  <div></div>
</template>

<script>
  export default {
    name: 'Homepage',
    data () {
      return {
        MAX_AUTHENTICATION_COUNT: 5,
        authenticateCount: 0
      }
    },
    async created() {
      await this.redirect();
    },
    methods: {
      async redirect() {
        const authenticated = this.$clipicker.isAuthenticated();
        if (authenticated) {
          this.$router.push({
            path: '/clinic/dashboard'
          });
        } else {
          this.$router.push({
            path: '/clinic/signin'
          });
        }
      },
    }
	}
</script>

<style scoped>

</style>
