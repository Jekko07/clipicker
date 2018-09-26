<template>
  <div>
    <div>
      <div class="q-pb-md">
        <q-btn
          round
          color="primary"
          @click="addSymptom"
          class="fixed"
          icon="add"
          style="right: 18px; bottom: 18px; z-index: 1"
        />
      </div>
      <div>
        <q-search v-model="search.value"
                  :placeholder="search.label"
                  @blur="searchSymptoms"
                  clearable
                  no-icon
        />
      </div>
      <q-list inset-separator class="q-mt-md" v-if="symptoms.length > 0">
        <q-item v-for="symp in symptoms" :key="symp.id">
          <q-item-side color="red-14">
            <i class="fas fa-user-md" color="red-14"></i>
          </q-item-side>
          <q-item-main :label="symp.name"/>
          <q-item-side right>
            <q-btn flat round dense icon="more_vert">
              <q-popover>
                <q-list link>
                  <q-item v-close-overlay @click.native="editSymptom(symp.id)">
                    <q-item-side icon="edit" />
                    <q-item-main label="Edit" />
                  </q-item>
                  <q-item v-close-overlay @click.native="deleteSymptom(symp.id)">
                    <q-item-side icon="delete" />
                    <q-item-main label="Delete" />
                  </q-item>
                </q-list>
              </q-popover>
            </q-btn>
          </q-item-side>
        </q-item>
      </q-list>
      <div class="q-my-md q-body-1" v-else>
        <span v-if="search.value.length > 0">No record found!</span>
        <span v-else>No Symptoms yet. Please add!</span>
      </div>
    </div>

  </div>
</template>

<script>
  import api from '../../../api/clinic';

  export default {
    name: "SymptomList",
    data() {
      return {
        symptoms: [],
        search: {
          label: 'Search for symptom',
          value: '',
        }
      }
    },

    created() {
      this.listSymptoms();
    },

    methods: {
      searchSymptoms() {
        this.listSymptoms({
          name: this.search.value
        })
      },
      listSymptoms(filter) {
        api.symptom.listSymptoms(filter)
          .then(response => {
            const data = response.data;
            if (data.success) {
              this.symptoms = data.data;
            } else {
              this.$clipicker.error(data.message);
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      editSymptom(id) {
        this.prepareRoute();
        this.$router.push({
          path: '/clinic/dashboard/adminSymptoms/edit' +
          '/id/' + id
        })
      },

      deleteSymptom(id) {
        this.$q.dialog({
          title: 'Confirm Symptom Deletion',
          message: 'Are you sure you want to delete?',
          ok: 'Ok',
          cancel: 'Cancel'
        }).then(() => {
          api.symptom.deleteSymptom(id)
            .then(response => {
              const data = response.data;
              if (data.success) {
                this.$clipicker.info(data.message);
                this.listSymptoms();
              } else {
                this.$clipicker.error(data.message);
              }
            })
            .catch(error => {
              console.log(`Error ${error}`);
            })
        }).catch(() => {
          // do nothing
        })
      },

      addSymptom() {
        this.prepareRoute();
        this.$router.push({
          path: '/clinic/dashboard/adminSymptoms/add'
        })
      },

      prepareRoute(noCallback) {
        if (!noCallback) {
          this.$clipicker.setCallback(() => {
            this.$router.push('/clinic/dashboard/adminSymptoms');
            this.listSymptoms();
          })
        }
      },

    }

  }
</script>

<style scoped>

</style>
