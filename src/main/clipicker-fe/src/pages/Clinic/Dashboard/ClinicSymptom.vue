<template>
  <div>
    <!-- as a String -->
    <q-chips-input @add="symp => addSymptom(symp)" v-model="selectedSympTemp" placeholder="Select from list or add new one" stack-label="List of symptoms covered" @duplicate="duplicate">
      <q-autocomplete @search="search" @selected="selected" />
    </q-chips-input>
  </div>
</template>

<script>
  import api from '../../../api/clinic';
  import { filter } from 'quasar';

  export default {
    name: "Symptom",
    data() {
      return {
        sympList: [],
        selectedSymps: [],
        selectedSympTemp: []
      }
    },
    created() {
      api.symptom.listClinicSymptoms()
        .then(response => {
          const data = response.data;
          if (data.success) {
            this.sympList = data.data;
          }
        })
        .catch(error => {
          console.log(`Error ${error}`);
        })
    },
    watch: {
      selectedSympTemp(selectedSymps) {
        this.setNewSymps(selectedSymps);
      }
    },
    methods: {
      search (name, done) {
        setTimeout(() => {
          done(filter(name, {field: 'value', list: this.parseSymps()}))
        }, 100)
      },

      selected (item) {
        this.$q.notify(`Selected suggestion "${item.label}"`)
      },

      duplicate (label) {
        this.$q.notify(`"${label}" already in list`)
      },

      parseSymps () {
        return this.sympList.map(symp => {
          return {
            label: symp.name,
            value: symp.name,
            id: symp.id
          }
        })
      },

      setNewSymps(selectedSymps) {
        let newSymps = [];
        selectedSymps.map(selectedSymp => {
          const foundSymp = this.sympList.filter(symp => symp.name == selectedSymp);
          if (foundSymp.length > 0) {
            newSymps.push(foundSymp);
          } else {
            this.selectedSympTemp.pop();
          }
        })
        this.selectedSymps = newSymps;
      },


      addSymptom(sympName) {
        const hasFound = this.sympList.filter(symp => symp.name == sympName);
        if (hasFound.length == 0) {
          this.selectedSympTemp.pop();
        } else {
          setTimeout(() => {
            this.selectedSymps(this.sympList.filter(symp => symp.name == sympName));
          }, 1000);
          ;
        }
      }

    },

  }
</script>

<style scoped>

</style>
