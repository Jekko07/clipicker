<template>
  <div>
    <q-dialog
      v-model="showServicesFilter"
      prevent-close
    >
      <div slot="body">
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

        <div class="q-my-sm">
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
      </div>
      <div slot="buttons" slot-scope="props">
        <div style="float: left">
          <div class="button-wrapper q-my-sm">
            <q-btn label="Search" icon="search" color="primary" @click="search" />
            <q-btn label="Close" icon="close" color="primary" @click="close" class="q-ml-sm" />
          </div>
        </div>
        <div style="clear: both;"></div>
      </div>
    </q-dialog>

    <div v-show="false">
      <div v-show="isLoadClinicServices"></div>
    </div>
  </div>
</template>

<script>
  import api from '../../api/patient';

  export default {
    name: "ClinicServicesFilter",
    data() {
      return {
        specList: [],
        symptomList: [],
        selectedSpecs: [],
        specOptions: [],
        selectedSymptoms: [],
        symptomsOptions: [],

        showServiceFilterDialog: false,
        specListLoaded: false,
        symptomListLoaded: false,
      }
    },

    created() {
      this.loadSpecializationList();
      this.loadSymptomsList();
    },

    computed: {
      showServicesFilter: {
        get() {
          return this.$store.getters['services/get'].show;
        },
        set(newValue) {
          // do nothing;
        }
      },

      isLoadClinicServices() {
        if (this.specListLoaded && this.symptomListLoaded) {
          this.selectedSpecs = this.$store.getters['services/get'].filter.specIdList;
          this.selectedSymptoms = this.$store.getters['services/get'].filter.symptomIdList;
        }
        return this.specListLoaded && this.symptomListLoaded;
      }
    },

    methods: {
      loadSpecializationList() {
        api.guest.nearbyClinic.listSpecialization()
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
        api.guest.nearbyClinic.listSymptom()
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

      search() {
        this.$store.dispatch('services/set', {
          specIdList: this.selectedSpecs,
          symptomIdList: this.selectedSymptoms
        });
        this.$store.dispatch('services/show', false);
        this.$store.dispatch('services/querySearch');
      },

      close() {
        this.$store.dispatch('services/show', false);
      },
    }
  }
</script>

<style scoped>

</style>
