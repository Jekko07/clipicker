<template>
  <div>
    <div>
      <div v-for="clinic in clinics" :key="clinic.id" class="q-py-md">
        <q-jumbotron>
          <div class="q-display-1">{{ clinic.name }}</div>
          <div class="q-subheading">
            {{ clinic.address }}
          </div>
          <hr class="q-hr q-my-lg">
          <div class="q-body-1 q-mb-md"> <strong>Business hours: </strong> {{ clinic.busStartTime }} - {{ clinic.busEndTime }}</div>
          <div class="q-body-1 q-mb-md"> <strong>Contact number: </strong> {{ clinic.contactNumber }}</div>
        </q-jumbotron>
      </div>
    </div>

  </div>
</template>

<script>
import api from '../../api/patient'

  export default {
    name: "ClinicList",
    data() {
      return {
        clinics: [],
        search: {
          label: 'Search for clinic',
          value: '',
        },
        currentUser: {
          email: ''
        }
      }
    },

    created() {
      this.listClinics();
    },

    methods: {
      listClinics(filter) {
        const vm = this;
        api.guest.nearbyClinic.list(filter)
          .then(response => {
            const data = response.data;
            if (data.success) {
              this.clinics = data.data;
              this.clinics.map(clinic => {
                clinic.busStartTime = this.$clipicker.buildBusTime(clinic.busStartTime, true);
                clinic.busEndTime = this.$clipicker.buildBusTime(clinic.busEndTime, true);
              })
            } else {
              this.$clipicker.error(data.message);
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },



    }

  }
</script>

<style scoped>

</style>
