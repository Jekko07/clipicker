<template>
  <div>
    <div>
      <div class="q-pb-md">
        <q-btn
          round
          color="primary"
          @click="addAnnouncement"
          class="fixed"
          icon="add"
          style="right: 18px; bottom: 18px; z-index: 1"
        />
      </div>
      <div>
        <q-search v-model="search.value"
                  :placeholder="search.label"
                  @blur="searchAnnouncements"
                  clearable
                  no-icon
        />
      </div>
      <q-list inset-separator class="q-mt-md" v-if="announcements.length > 0">
        <q-item v-for="announcement in announcements" :key="announcement.id">
          <q-item-side icon="announcement" color="red-14"/>
          <q-item-main :label="announcement.name"/>
          <q-item-side right>
            <q-btn flat round dense icon="more_vert">
              <q-popover>
                <q-list link>
                  <q-item v-close-overlay @click.native="editAnnouncement(announcement.id)">
                    <q-item-side icon="edit" />
                    <q-item-main label="Edit" />
                  </q-item>
                  <q-item v-close-overlay @click.native="deleteAnnouncement(announcement.id)">
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
        <span v-else>No Announcements yet. Please add!</span>
      </div>
    </div>

  </div>
</template>
<script>
  import api from '../../../api/clinic';

  export default {
    name: "AnnouncementList",
    data() {
      return {
        announcements: [],
        search: {
          label: 'Search for announcement',
          value: '',
        }
      }
    },

    created() {
      this.listAnnouncements();
    },

    methods: {
      searchAnnouncements() {
        this.listAnnouncements({
          name: this.search.value
        })
      },
      listAnnouncements(filter) {
        api.announcement.list(filter)
          .then(response => {
            const data = response.data;
            if (data.success) {
              this.announcements = data.data;
            } else {
              this.$clipicker.error(data.message);
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      editAnnouncement(id) {
        this.prepareRoute();
        this.$router.push({
          path: '/clinic/dashboard/adminAnnouncements/edit' +
            '/id/' + id
        })
      },

      deleteAnnouncement(id) {
        this.$q.dialog({
          title: 'Confirm Announcement Deletion',
          message: 'Are you sure you want to delete?',
          ok: 'Ok',
          cancel: 'Cancel'
        }).then(() => {
          api.announcement.delete(id)
            .then(response => {
              const data = response.data;
              if (data.success) {
                this.$clipicker.info(data.message);
                this.listAnnouncements();
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

      addAnnouncement() {
        this.prepareRoute();
        this.$router.push({
          path: '/clinic/dashboard/adminAnnouncements/add'
        })
      },

      prepareRoute(noCallback) {
        if (!noCallback) {
          this.$clipicker.setCallback(() => {
            this.$router.push('/clinic/dashboard/adminAnnouncements');
            this.listAnnouncements();
          })
        }
      },

    }

  }
</script>
