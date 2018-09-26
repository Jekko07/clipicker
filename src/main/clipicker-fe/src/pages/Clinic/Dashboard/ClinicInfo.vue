<template>
  <q-page>
    <clinic :email="email" :own="own" />
  </q-page>
</template>

<script>
  import Clinic from '../../../components/Clinic/Clinic'

  export default {
    name: "ClinicInfo",
    components: {
      'clinic': Clinic
    },
    data() {
      return {
        email: '',
        username: '',
        own: 1
      }
    },
    created() {
      this.loadClinicInfo();
    },
    computed: {
      
    },
    methods: {
      loadClinicInfo() {
        const user = this.$store.getters['user/get'].details;
        this.email = user.email;
        this.username = user.name;
      }
    }
  }
</script>

<style scoped>

</style>
