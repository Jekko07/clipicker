<template>
  <div>
    <div v-show="load">
      <div class="form-container">
        <div class="q-mb-sm q-body-1" v-show="editable" >
          <span> Please fill up required fields:</span>
        </div>

        <q-btn label="Edit" icon="edit" color="primary" class="q-mb-sm" v-show="!newAnnouncement && !editable" @click="edit"/>
        <q-input :stack-label="label.name" v-model="announcement.name" :disable="!editable" />
        <q-input :stack-label="label.description" v-model="announcement.description" type="textarea" rows="4" :max-height="100" :disable="!editable" />
        <q-input :stack-label="label.location" v-model="announcement.location" type="textarea" rows="4" :max-height="100" :disable="!editable" />

        <!-- Date & Time -->
        <q-datetime :stack-label="label.startTime" v-model="announcement.startTime" type="datetime" :format="label.dateFormat" :disable="!editable" />
        <q-datetime :stack-label="label.expireTime" v-model="announcement.expireTime" type="datetime" :format="label.dateFormat" :disable="!editable" />

        <div class="q-pt-lg">
          <q-btn label="Save" color="primary" class="q-mr-sm" @click="saveOrUpdate" :disable="!editable"/>
          <q-btn label="Cancel" color="primary" @click="cancel" :disable="!editable" />
        </div>
      </div>


    </div>

  </div>
</template>
<script>
  import api from '../../api/clinic';
  import { required } from 'vuelidate/lib/validators';

  export default {
    props: [
      'id'
    ],
    components: {
    },
    data() {
      return {
        load: true,
        editable: false,
        newAnnouncement: false,
        label: {
          name: 'Announcement name *',
          description: 'Description *',
          location: 'Location *',
          startTime: 'Start time *',
          expireTime: 'Expire time *',
          dateFormat: 'YYYY-MM-DD HH:mm:ss'
        },
        announcement: {
          id: this.$props.id,
          name: '',
          description: '',
          location: '',
          startTime: '',
          expireTime: ''
        }
      }
    },

    created() {
      if (this.announcement.id) {
        this.loadAnnouncement();
      } else {
        this.newAnnouncement = true;
        this.editable = true;
      }
    },

    methods: {
      loadAnnouncement() {
        api.announcement.load(this.announcement.id)
          .then(response => {
            this.load = true;
            const data = response.data;
            // announcement exist already
            if (data.success) {
              this.editable = false;
              this.newAnnouncement = false;
              this.announcement = data.data;
            } else {
              this.editable = true;
              this.newAnnouncement = true;
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      saveOrUpdate() {
        this.$v.announcement.$touch();
        if (this.$v.announcement.$error) {
          if (this.announcement.expireTime < new Date().toISOString() || this.announcement.expireTime <= this.announcement.startTime) {
            this.$clipicker.error('Please specify valid date time.')
          } else {
            this.$clipicker.error('Please fill up required fields.')
          }

        } else {
          // save data here;
          api.announcement.saveOrUpdate(this.announcement)
            .then(response => {
              const data = response.data;
              if (data.success) {
                this.$clipicker.info(data.message);
                this.newAnnouncement = false;
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
      }

    },

    validations: {
      announcement: {
        name: { required },
        description: { required },
        location: { required },
        startTime: {
          required,
        },
        expireTime: {
          required,
          minValue: value => value > new Date().toISOString()
        }
      }
    }
  }
</script>
<style lang="stylus" scoped>
  .form-container
    .q-input
      margin-bottom 8px
</style>


