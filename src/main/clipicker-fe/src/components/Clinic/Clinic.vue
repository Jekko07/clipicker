<template>
    <div>
      <div v-show="load">
        <div class="form-container">
          <div class="q-mb-sm q-body-1" v-show="editable" >
            <span> Please fill up required fields:</span>
          </div>

          <q-btn label="Edit" icon="edit" color="primary" class="q-mb-sm" v-show="!newAccount && !editable" @click="edit"/>
          <q-input :stack-label="label.name" v-model="clinic.name" :disable="!editable" />
          <q-input :stack-label="label.address" v-model="clinic.address" type="textarea" rows="4" :max-height="100" :disable="!editable" />
          <q-input :stack-label="label.contactNumber" v-model="clinic.contactNumber" :disable="!editable" />
          <!-- Only Time -->
          <q-datetime :stack-label="label.bus_start_time" v-model="bus_start_time" type="time" :format="label.timeFormat" :disable="!editable"/>
          <q-datetime :stack-label="label.bus_end_time" v-model="bus_end_time" type="time" :format="label.timeFormat" :disable="!editable"/>


          <div class="q-my-sm">
            <div class="q-mb-sm">
              <span class="q-caption text-weight-light q-if-label">Map location *</span>
            </div>
            <q-btn round icon="location_on" color="red-10" @click="setMap" :disable="!editable" />
          </div>

          <div class="q-pt-lg">
            <q-btn label="Save" color="primary" class="q-mr-sm" @click="saveOrUpdate" :disable="!editable"/>
            <q-btn label="Cancel" color="primary" @click="cancel" :disable="(newAccount || !editable) && clinic.isOwn == 1" />
            <br><br><br>
          </div>
        </div>

        <!-- fullscreen map here -->
        <!-- start -->
        <div class="clinic-set-map-container" v-show="clipickerMapParam.load">
          <clipicker-map :options="clipickerMapParam" />
        </div>
        <!-- end -->
      </div>

      <div style="height: 100px" />
      <div v-if="!load">
        <router-view />
      </div>

      <q-tabs align="justify" v-model="admin.routeClinicInfo" class="fixed-bottom" v-if="isAdmin && editable && !newAccount">
        <q-tab
          slot="title"
          icon="info"
          replace
          label="Information"
          @click="load = true"

        />
        <q-route-tab
          slot="title"
          icon="fas fa-user-md"
          :to="admin.routeClinicServices"
          replace
          label="Services"
          @click="load = false"
          exact>

        </q-route-tab>
      </q-tabs>

    </div>
</template>
<script>
import api from '../../api/clinic';
import { required } from 'vuelidate/lib/validators';
import ClipickerMap from '../../components/Map/ClipickerMap'
import { date } from 'quasar';

export default {
    props: [
        'email',
        'own',
    ],
    components: {
      'clipickerMap': ClipickerMap
    },
    data() {
        return {
            load: false,
            editable: false,
            newAccount: false,
            dateFormatter: date,
            label: {
              name: 'Clinic name *',
              address: 'Address *',
              contactNumber: 'Contact number *',
              bus_start_time: 'Business Start Time *',
              bus_end_time: 'Business End Time *',
              timeFormat: 'hh:mm A'
            },
            bus_start_time: null,
            bus_end_time: null,
            clinic: {
                email: this.$props.email,
                isOwn: this.$props.own,
                username: '',
                name: '',
                address: '',
                lat: null,
                lng: null,
                contactNumber: '',
                bus_start_time: null,
                bus_end_time: null
            },
            clipickerMapParam: {
              mode: 'single',
              closeMapFn: this.closeMap,
              load: false,
              clinic: {}
            },
        }
    },

    created() {
      this.loadClinic();
    },

    computed: {
      isAdmin() {
        return this.$store.getters['user/get'].isAdmin;
      },
      admin() {
        return {
          routeClinicInfo: `/clinic/dashboard/adminClinics/edit/email/${this.$props.email}/own/${this.$props.own}`,
          routeClinicServices: `/clinic/dashboard/adminClinics/edit/email/${this.$props.email}/own/${this.$props.own}/services`
        }
      },
    },


    methods: {
      loadClinic() {
        api.clinic.loadClinic(this.clinic.email)
          .then(response => {
            this.load = true;
            const data = response.data;
            // clinic exist already
            if (data.success) {
              this.editable = false;
              this.newAccount = false;
              this.clinic = data.data;
              this.clinic.isOwn = this.$props.own;

              this.bus_start_time = this.$clipicker.buildBusTime(this.clinic.busStartTime);
              this.bus_end_time = this.$clipicker.buildBusTime(this.clinic.busEndTime);
              this.$store.dispatch('user/setActivationStatus', this.clinic.status);

            } else {
              this.editable = true;
              this.newAccount = true;
              this.clinic.username = this.$store.getters['user/get'].details.name;    // get the name from the login user which was stored from vuex store
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      saveOrUpdate() {
        this.clinic.bus_start_time = this.dateFormatter.formatDate(this.bus_start_time, 'HH:mm ') ? this.dateFormatter.formatDate(this.bus_start_time, 'HH:mm ').trim() : '';
        this.clinic.bus_end_time = this.dateFormatter.formatDate(this.bus_end_time, 'HH:mm ') ? this.dateFormatter.formatDate(this.bus_end_time, 'HH:mm ').trim() : '';
        this.$v.clinic.$touch();
        if (this.$v.clinic.$error) {
          this.$clipicker.error('Please fill up required fields.')
        } else {
          // save data here;
          api.clinic.saveOrUpdate(this.clinic)
            .then(response => {
              const data = response.data;
              if (data.success) {
                this.$store.dispatch('user/setRegistrationStatus', true);
                this.$store.dispatch('user/setActivationStatus', false);
                this.$clipicker.info(data.message);
                this.newAccount = false;
                this.editable = false;
                this.$clipicker.invokedCallback();
              }
            })

        }
      },

      edit() {
        this.editable  = true;
      },

      cancel() {
        this.editable = false;
        this.$clipicker.invokedCallback();
      },

      setMap() {
        this.clipickerMapParam.clinic = this.clinic;
        this.clipickerMapParam.load = true;
      },

      closeMap() {
        this.$delete(this.clipickerMapParam, 'clinic');
        this.clipickerMapParam.load = false;
      }

    },

    validations: {
      clinic: {
        email: { required },
        username: { required },
        name: { required },
        address: { required },
        lat: { required },
        lng: { required },
        contactNumber: { required },
        isOwn: { required },
        bus_start_time: { required },
        bus_end_time: { required }
      }
    }
}
</script>
<style lang="stylus" scoped>
  .form-container
    .q-input
      margin-bottom 8px
</style>


