<template>
  <q-page padding>
    <div v-for="announcement in announcements" :key="announcement.id" class="q-py-md">
      <q-jumbotron>
        <div class="q-display-1">{{ announcement.name }}</div>
        <div class="q-subheading">
          {{ announcement.description }}
        </div>
        <hr class="q-hr q-my-lg">
        <div class="q-title q-mb-md"> {{ announcement.location }}</div>
        <div class="q-body-1 q-mb-md"> <strong>Start time: </strong> {{ formatDatetime(announcement.startTime) }}</div>
        <div class="q-body-1 q-mb-md"> <strong>End time: </strong> {{ formatDatetime(announcement.expireTime) }}</div>
      </q-jumbotron>
    </div>

  </q-page>
</template>

<script>
import { date } from 'quasar';

export default {
  name: 'Announcements',
  data() {
    return {

    }
  },
  computed: {
    announcements() {
      return this.$store.getters['announcement/get'].list;
    },

  },
  beforeCreate() {
    this.$cliPickerAnnouncer.markSeenAnnouncement();
  },
  methods: {
    formatDatetime(dateTime) {
      return date.formatDate(dateTime, 'YYYY-MMM-DD hh:mm A');
    }
  }

}
</script>

<style>
</style>
