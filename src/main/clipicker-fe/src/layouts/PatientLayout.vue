<template>
  <q-layout view="lHh Lpr lFf"> <!-- Be sure to play with the Layout demo on docs -->

    <!-- (Optional) The Header -->
    <q-layout-header>
        <q-tabs v-model="defaultTab">
          <q-route-tab
            slot="title"
            icon="location_on"
            name="nearby-clinic"
            to="/patient/nearby-clinic"
            replace
            label="Nearby Clinic"
            exact
          />
          <q-route-tab
            slot="title"
            icon="local_hospital"
            name="all-clinic"
            to="/patient/all-clinic"
            replace
            label="All Clinic"
            exact
          />
          <q-route-tab
            slot="title"
            icon="announcement"
            name="announcements"
            to="/patient/announcements"
            @click="markSeenAnnouncement"
            replace
            label="Announcements"
            exact>
            <div v-if="unseenAnnouncementCount > 0">
              <q-chip color="red" class="shadow-1" dense>{{unseenAnnouncementCount}}</q-chip>
            </div>

          </q-route-tab>
        </q-tabs>
    </q-layout-header>
    <q-page-container class="q-pa-md">
      <!-- This is where pages get injected -->
      <router-view />
    </q-page-container>

  </q-layout>
</template>

<script>
import { Platform } from 'quasar'

export default {
  name: 'PatientLayout',
  data () {
    return {
      leftDrawer: true,
      defaultTab: 'nearby-clinic'
    }
  },
  computed: {
    unseenAnnouncementCount() {
      return this.$store.getters['announcement/get'].unseenCount;
    }
  },
  created() {
    this.$cliPickerAnnouncer.loadAnnouncements();
    this.$cliPickerAnnouncer.initGuestWebsocket(Platform.is.cordova);
  },
  mounted() {
    this.showNearbyClinic();
  },
  methods: {
    showNearbyClinic() {
      this.$router.push('/patient/nearby-clinic');
    },
    markSeenAnnouncement() {
      this.$cliPickerAnnouncer.markSeenAnnouncement();
    }
  }
}
</script>

<style>
</style>
