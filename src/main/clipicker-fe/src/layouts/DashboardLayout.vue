<template>
  <q-layout view="lHh Lpr lFf"> <!-- Be sure to play with the Layout demo on docs -->

    <!-- (Optional) The Header -->
    <q-layout-header v-model="headerState">
      <q-toolbar>
        <q-btn
          flat
          round
          dense
          icon="menu"
          @click="leftDrawer = !leftDrawer"
        />
        <q-toolbar-title>
          {{ label.application }}
        </q-toolbar-title>
        <q-btn round shadow dense icon="logout" label="Logout" @click="logout" />
      </q-toolbar>
    </q-layout-header>

    <!-- (Optional) A Drawer; you can add one more with side="right" or change this one's side -->
    <q-layout-drawer
      side="left"
      v-model="leftDrawer"
      behavior="mobile"
    >
      <!-- QScrollArea is optional -->
      <q-scroll-area class="fit q-pa-sm">
        <q-list
          no-border
          link
          inset-delimiter
        >
          <!-- normal clinic -->
          <div v-if="!isAdmin">
            <q-item to="/clinic/dashboard" exact>
              <q-item-side icon="home" />
              <q-item-main label="Clinic" :sublabel="activationStatus" />
            </q-item>
            <q-item to="/clinic/dashboard/services" exact v-if="!newAccount || isRegistered">
              <q-item-side icon="fas fa-user-md" />
              <q-item-main label="Services" />
            </q-item>
          </div>


          <!-- super admin user -->
          <div v-else>
            <q-item to="/clinic/dashboard/adminClinics" exact>
              <q-item-side icon="home" />
              <q-item-main label="Clinics" />
            </q-item>
            <q-item to="/clinic/dashboard/adminSpecializations" exact>
              <q-item-side icon="local_hospital" />
              <q-item-main label="Specializations" />
            </q-item>
            <q-item to="/clinic/dashboard/adminSymptoms" exact>
              <q-item-side icon="supervised_user_circle" />
              <q-item-main label="Symptoms" />
            </q-item>
            <q-item to="/clinic/dashboard/adminAnnouncements" exact>
              <q-item-side icon="announcement" />
              <q-item-main label="Announcements" />
            </q-item>
          </div>

        </q-list>
      </q-scroll-area>
    </q-layout-drawer>

    <q-page-container>
      <!-- This is where pages get injected -->
      <div class="q-ma-md">
        <router-view />
      </div>
    </q-page-container>
  </q-layout>
</template>

<script>
import appConfig from '../config/app.config'
import api from '../api/clinic';

export default {
  name: 'DashboardLayout',
  data () {
    return {
      label: {
        application: appConfig.application.name
      },
      leftDrawer: true,
      headerState: true,

      clinic: {
        newAccount: true
      }
    }
  },
  created() {
    this.loadLoginUser();
  },
  computed: {
    isAdmin() {
      const isUserAdmin = this.$store.getters['user/get'].isAdmin;
      this.$cliPickerAnnouncer.initClinicWebsocket(isUserAdmin);

      return isUserAdmin;
    },

    newAccount() {
      return this.clinic.newAccount;
    },

    activationStatus() {
      if (this.$store.getters['user/get'].isActivated) {
        return 'Active';
      } else {
        if (this.$store.getters['user/get'].isActivated != null) {
          return 'Pending Activation';
        } else {
          return '';
        }
      }
      return this.$store.getters['user/get'].isActivated;
    },

    isRegistered() {
      return this.$store.getters['user/get'].isRegistered;
    }
  },
  methods: {
    logout() {
      this.$q.dialog({
        title: 'Confirm Logout',
        message: 'Are you sure you want to logout?',
        ok: 'Ok',
        cancel: 'Cancel'
      }).then(() => {
        this.$clipicker.logout();
      }).catch(() => {
        // do nothing
      })
    },

    loadLoginUser() {
      const email = this.$store.getters['user/get'].details.email;
      api.clinic.loadClinic(email)
        .then(response => {
          const data = response.data;
          if (data.success) {
            this.clinic.newAccount = false;
            const user = data.data;
            if (user.isAdmin == 1) {
              this.$store.dispatch('user/setAdmin', true);
              this.$router.push('/clinic/dashboard/adminClinics');
              this.$cliPickerAnnouncer.showBadgeForInactiveClinics();
            }
          }
        })
        .catch(error => {
          console.log(`Error ${error}`);
        })
    }
  }
}
</script>

<style>
</style>
