<template>
  <q-page class="nearby-clinic" padding>
    <!-- fullscreen map here -->
    <!-- start -->
    <div class="clinic-set-map-container" v-show="clipickerMapParam.load">
      <clipicker-map :options="clipickerMapParam" />
    </div>
    <!-- end -->
    <div v-show="false">
      <div v-show="querySearch"/>
    </div>

  </q-page>
</template>

<script>

  import api from '../../api/patient';
  import ClipickerMap from '../../components/Map/ClipickerMap'

  export default {
    name: 'NearbyClinic',
    components: {
      ClipickerMap
    },
    data() {
      return {
        clinics: [],
        clipickerMapParam: {
          client: 'nearby-clinic',
          mode: 'interactive',
          closeMapFn: this.closeMap,
          load: false,
          clinics: []
        },
        selectedSymptoms: [],
        selectedSpecializations: [],
        minimizedModal: false
      }
    },

    created() {
      this.clipickerMapParam.clinics = [];
      this.clipickerMapParam.load = true;
    },



    methods: {
      listClinics(filters) {
        return api.guest.nearbyClinic.list(filters);
      },

      showNearbyClinics() {
        const filters = {
          symptomIdList: this.$store.getters['services/get'].filter.specIdList.join(','),
          specIdList: this.$store.getters['services/get'].filter.symptomIdList.join(',')
        };

        this.listClinics(filters)
          .then(response => {
            const data = response.data;
            if (data.success) {
              this.clinics = data.data;

              this.clinics.map(clinic => {
                clinic.bus_start_time = this.$clipicker.buildBusTime(clinic.busStartTime, true);
                clinic.bus_end_time = this.$clipicker.buildBusTime(clinic.busEndTime, true);
              })

              this.clipickerMapParam.clinics = this.clinics;
              this.clipickerMapParam.load = true;
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      closeMap() {
        this.clipickerMapParam.load = false;
        this.$delete(this.clipickerMapParam, 'clinics');
        this.$router.push({
          path: '/patient/all-clinic'
        })
      },
    },

    computed: {
      querySearch() {
        if (this.$store.getters['services/get'].querySearch > 0) {
          this.showNearbyClinics();
        }
        return this.$store.getters['services/get'].querySearch > 0;
      }
    }

  }
</script>

<style lang="stylus">
  .nearby-clinic
    .q-btn
      margin-top 8px !important
</style>
