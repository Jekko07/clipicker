<template>
  <div>
    <div>
      <span class="caption">Services offered: </span>
    </div>
    <div class="q-my-md">
      <q-select
        multiple
        chips
        filter
        inverted
        float-label="Pick symptoms"
        v-model="selectedSpecs"
        :options="options"
      />
    </div>

    <div class="button-wrapper q-my-sm">
      <q-btn label="Save" color="primary" />
    </div>
  </div>
</template>

<script>
import api from '../../../api/clinic';
import { filter } from 'quasar';

  export default {
    name: "Specialization",
    data() {
      return {

        specList: [],
        selectedSpecs: [],
        selectedSpecTemp: [],

        filterSelect: '',
        options: []
      }
    },
    created() {
      this.loadSpecializationList();
    },
    methods: {
      loadSpecializationList() {
        api.spec.listClinicSpecializations()
          .then(response => {
            const data = response.data;
            if (data.success) {
              this.specList = data.data;
              this.specList.map(spec => {
                this.options.push({
                  label: spec.name,
                  value: spec.id
                })
              })
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
