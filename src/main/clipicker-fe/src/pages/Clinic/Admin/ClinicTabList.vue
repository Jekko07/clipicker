<template>
  <div>
    <div>
      <router-view />
    </div>

    <div style="height: 100px" />
    <q-tabs class="fixed-bottom" align="justify">
      <q-route-tab
        slot="title"
        align="justify"
        icon="local_hospital"
        to="/clinic/dashboard/adminClinics"
        name="active-clinics"
        replace
        label="Active"
        exact/>
      <q-route-tab
        slot="title"
        align="justify"
        icon="local_hospital"
        to="/clinic/dashboard/adminClinics/inactive"
        name="inactive-clinics"
        @click="markViewPendingClinics"
        replace
        label=""
        exact>
        Pending
        <div v-if="hasNewInactiveClinics" style="right: -10px; top: -50px; position: relative">
          <q-chip dense color="red">!</q-chip>
        </div>

      </q-route-tab>
    </q-tabs>
  </div>
</template>

<script>
  export default {
    name: "ClinicTabList",
    computed: {
      hasNewInactiveClinics() {
        return this.$store.getters['user/get'].notifyPendingClinic;
      }
    },
    methods: {
      markViewPendingClinics() {
        this.$store.dispatch('user/notifyPendingClinic', false);
      }
    }
  }
</script>

<style scoped>

</style>
