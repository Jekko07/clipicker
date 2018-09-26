<template>
  <div>
    <div>
      <div class="q-pb-md">
        <q-btn
          round
          color="primary"
          @click="addSpecialization"
          class="fixed"
          icon="add"
          style="right: 18px; bottom: 18px; z-index: 1"
        />
      </div>
      <div>
        <q-search v-model="search.value"
                  :placeholder="search.label"
                  @blur="searchSpecializations"
                  clearable
                  no-icon
        />
      </div>
      <q-list inset-separator class="q-mt-md" v-if="specializations.length > 0">
        <q-item v-for="spec in specializations" :key="spec.id">
          <q-item-side color="red-14">
            <i class="fas fa-user-md" color="red-14"></i>
          </q-item-side>
          <q-item-main :label="spec.name"/>
          <q-item-side right>
            <q-btn flat round dense icon="more_vert">
              <q-popover>
                <q-list link>
                  <q-item v-close-overlay @click.native="editSpecialization(spec.id)">
                    <q-item-side icon="edit" />
                    <q-item-main label="Edit" />
                  </q-item>
                  <q-item v-close-overlay @click.native="deleteSpecialization(spec.id)">
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
        <span v-else>No Specializations yet. Please add!</span>
      </div>
    </div>

  </div>
</template>
<script>
  import api from '../../../api/clinic';

  export default {
    name: "SpecializationList",
    data() {
      return {
        specializations: [],
        search: {
          label: 'Search for specialization',
          value: '',
        }
      }
    },

    created() {
      this.listSpecializations();
    },

    methods: {
      searchSpecializations() {
        this.listSpecializations({
          name: this.search.value
        })
      },
      listSpecializations(filter) {
        api.spec.listSpecializations(filter)
          .then(response => {
            const data = response.data;
            if (data.success) {
              this.specializations = data.data;
            } else {
              this.$clipicker.error(data.message);
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      editSpecialization(id) {
        this.prepareRoute();
        this.$router.push({
          path: '/clinic/dashboard/adminSpecializations/edit' +
          '/id/' + id
        })
      },

      deleteSpecialization(id) {
        this.$q.dialog({
          title: 'Confirm Specialization Deletion',
          message: 'Are you sure you want to delete?',
          ok: 'Ok',
          cancel: 'Cancel'
        }).then(() => {
          api.spec.deleteSpec(id)
            .then(response => {
              const data = response.data;
              if (data.success) {
                this.$clipicker.info(data.message);
                this.listSpecializations();
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

      addSpecialization() {
        this.prepareRoute();
        this.$router.push({
          path: '/clinic/dashboard/adminSpecializations/add'
        })
      },

      prepareRoute(noCallback) {
        if (!noCallback) {
          this.$clipicker.setCallback(() => {
            this.$router.push('/clinic/dashboard/adminSpecializations');
            this.listSpecializations();
          })
        }
      },

    }

  }
</script>
