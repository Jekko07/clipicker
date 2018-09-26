<template>
  <div>
    <div>
      <div class="q-pb-md">
        <q-btn
          round
          color="primary"
          @click="addClinic"
          class="fixed"
          icon="add"
          style="right: 18px; bottom: 80px; z-index: 1"
        />
      </div>
      <div>
        <q-search v-model="search.value"
                  :placeholder="search.label"
                  @blur="searchClinics"
                  clearable
                  no-icon
                   />
      </div>
      <q-list inset-separator class="q-mt-md" v-if="clinics.length > 0">
        <q-item v-for="clinic in clinics" :key="clinic.id">
          <q-item-side icon="local_hospital" :color="clinic.email == currentUser.email ? 'primary' : 'red-14'"/>
          <q-item-main>
            <q-item-tile label>{{ clinic.name }}</q-item-tile>
            <q-item-tile sublabel>{{ clinic.address }}</q-item-tile>
            <q-item-tile label v-if="clinic.email == currentUser.email">
              <span class="uppercase text-weight-thin text-right" style="color: #8b3e1d">current user</span>
            </q-item-tile>
          </q-item-main>
          <q-item-side right>
            <q-btn flat round dense icon="more_vert">
              <q-popover>
                <q-list link>
                  <q-item v-close-overlay @click.native="editClinic(clinic.email)">
                    <q-item-side icon="edit" />
                    <q-item-main label="Edit" />
                  </q-item>
                  <q-item v-close-overlay @click.native="deleteClinic(clinic.email)">
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
        <span v-else>No Clinics yet. Please add!</span>
      </div>
    </div>

  </div>
</template>

<script>
import api from '../../../api/clinic';

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

    computed: {
      isAdmin() {
        return this.$store.getters['user/get'].isAdmin;
      }
    },

    methods: {
      searchClinics() {
        this.listClinics({
          name: this.search.value
        })
      },

      listClinics(filter) {
        const vm = this;
        api.clinic.list(filter)
          .then(response => {
            const data = response.data;
            if (data.success) {
              this.currentUser.email = vm.$store.getters['user/get'].details.email;
              this.clinics = data.data;
            } else {
              this.$clipicker.error(data.message);
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      editClinic(email) {
        this.prepareRoute();
        this.$router.push({
          path: '/clinic/dashboard/adminClinics/edit' +
            '/email/' + email +
            '/own/0',
        })
      },

      deleteClinic(email) {
        this.$q.dialog({
          title: 'Confirm Clinic Deletion',
          message: 'Are you sure you want to delete?',
          ok: 'Ok',
          cancel: 'Cancel'
        }).then(() => {
          api.clinic.delete(email)
            .then(response => {
              const data = response.data;
              if (data.success) {
                this.$clipicker.info(data.message);
                this.listClinics();
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

      addClinic() {
        this.prepareRoute();
        const generatedEmail = 'admin_' + new Date().getTime() + '@clipicker.me';
        this.$router.push({
          path: '/clinic/dashboard/adminClinics/add' +
            '/email/' + generatedEmail +
            '/own/0'
        })
      },

      prepareRoute(noCallback) {
        if (!noCallback) {
          this.$clipicker.setCallback(() => {
            this.$router.push('/clinic/dashboard/adminClinics');
            this.listClinics();
          })
        }
      },

    }

  }
</script>

<style scoped>

</style>
