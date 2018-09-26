<template>
  <div class="q-pa-md">
    <div>
      <span class="caption">Services offered: </span>
    </div>
    <div class="q-my-md">
      <q-select
        multiple
        chips
        filter
        inverted
        float-label="Pick specializations"
        v-model="selectedSpecs"
        :options="specOptions"
      />
    </div>

    <div class="q-my-md">
      <q-select
        multiple
        chips
        filter
        inverted
        float-label="Pick symptoms"
        v-model="selectedSymptoms"
        :options="symptomsOptions"
      />
    </div>

    <div class="button-wrapper q-my-sm">
      <q-btn label="Save" color="primary" @click="saveClinicServices" />
    </div>

    <div v-show="false">
      <div v-show="isLoadClinicServices"></div>
    </div>
  </div>
</template>

<script>
import api from '../../../api/clinic';


  export default {
    name: "ClinicServices",
    data() {
      return {
        specList: [],
        symptomList: [],
        specListLoaded: false,
        symptomListLoaded: false,

        selectedSpecs: [],
        selectedSymptoms: [],

        specOptions: [],
        symptomsOptions: [],
        clinicEmail: '',
      }
    },
    created() {
      if (this.$route.params.email) {
        this.clinicEmail = this.$route.params.email
      } else {
        this.clinicEmail = this.$store.getters['user/get'].details.email;
      }

      this.loadSpecializationList();
      this.loadSymptomsList();

    },

    computed: {
      isLoadClinicServices() {
        if (this.specListLoaded && this.symptomListLoaded) {
          this.loadClinicServices();
        }
        return this.specListLoaded && this.symptomListLoaded;
      }
    },

    methods: {
      loadSpecializationList() {
        api.spec.listClinicSpecializations()
          .then(response => {
            const data = response.data;
            if (data.success) {
              this.specList = data.data;
              this.specListLoaded = true;
              this.specList.map(spec => {
                this.specOptions.push({
                  label: spec.name,
                  value: spec.id
                })
              })
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      loadSymptomsList() {
        api.symptom.listClinicSymptoms()
          .then(response => {
            const data = response.data;
            if (data.success) {
              this.symptomList = data.data;
              this.symptomListLoaded = true;
              this.symptomList.map(symptom => {
                this.symptomsOptions.push({
                  label: symptom.name,
                  value: symptom.id
                })
              })
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      saveClinicServices() {
        api.clinic.saveClinicServices({
          specIdList: this.selectedSpecs,
          symptomIdList: this.selectedSymptoms,
          clinicEmail: this.clinicEmail
        })
          .then(response => {
            const data = response.data;
            if (data.success) {
              this.$clipicker.info(data.message);
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      loadClinicServices() {
        api.clinic.loadClinicServices({
          clinicEmail: this.clinicEmail
        })
          .then(response => {
            const data = response.data;
            if (data.success) {
              const clinicServiceOffer = data.data;
              this.selectedSpecs = clinicServiceOffer.specIdList;
              this.selectedSymptoms = clinicServiceOffer.symptomIdList;
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      }

    },
  }
</script>

<style scoped>

</style>

