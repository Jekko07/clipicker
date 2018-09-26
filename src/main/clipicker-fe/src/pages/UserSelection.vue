<template>
  <q-page id="user-selection-container">
    <div id="dialog-container">
      <q-dialog
        v-model="userDialog" color="black"
        prevent-close
      >

        <div slot="title">
          <span class="dialog-title">Select User type</span>
        </div>
        <div slot="body">
          <div class="no-scroll">
            <q-radio v-model="user.selectedType" :val="user.clinic" :label="user.clinic" class="q-px-sm" dark color="secondary" />
            <q-radio v-model="user.selectedType" :val="user.patient" :label="user.patient" class="q-px-sm" dark color="secondary"/>
          </div>

        </div>

        <div slot="buttons" slot-scope="props">
          <div style="float: left">
            <q-btn label="Submit" @click="onOk" color="secondary" dark inverted />
          </div>
          <div style="clear: both;"></div>
        </div>
      </q-dialog>

    </div>

  </q-page>
</template>

<script>
import appConfig from '../config/app.config'
import { Platform } from 'quasar'

export default {
  name: 'UserSelection',
  data() {
    return {
      user: {
        patient: 'patient',
        clinic: 'clinic',
        selectedType: 'clinic',
      },
      userDialog: false
    }
  },
  methods: {
    async onOk() {
      this.hide();
      if (this.user.selectedType === this.user.clinic) {
        this.$clipicker.setClinicType(true);
        if (Platform.is.cordova	) {
          window.location = appConfig.httpLocation + '/clinic';   // redirect to clinic web location
        } else {
          this.$router.push( '/clinic');
        }
      } else if (this.user.selectedType === this.user.patient) {
        this.$clipicker.setClinicType(false);
        this.$router.push('/patient/nearby-clinic');
      }
    },

    hide() {
      this.userDialog = false;
    }
  },
  async created() {
    if (this.$clipicker.isUserTypeSet()) {
      if (this.$clipicker.isClinicType()) {
        if (Platform.is.cordova	) {
          window.location = appConfig.httpLocation + '/clinic';   // redirect to clinic web location
        } else {
          this.$router.push( '/clinic');
        }
      } else {
        this.$router.push('/patient/nearby-clinic');
      }
    } else {
      this.userDialog = true;
    }
  }
}
</script>

<style lang="stylus" scoped>

@import '~src/css/app.styl';

  #user-selection-container
    white-text()
  .dialog-title
    font-size 16px !important

</style>
