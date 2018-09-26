<template>
  <div>
    <div v-show="load">
      <div class="form-container">
        <div class="q-mb-sm q-body-1" v-show="editable" >
          <span> Please fill up required fields:</span>
        </div>

        <q-btn label="Edit" icon="edit" color="primary" class="q-mb-sm" v-show="!newSpec && !editable" @click="edit"/>
        <q-input :stack-label="label.name" v-model="specialization.name" :disable="!editable" />
        <q-input :stack-label="label.description" v-model="specialization.description" type="textarea" rows="4" :max-height="100" :disable="!editable" />

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
        newSpec: false,
        label: {
          name: 'Specialization name *',
          description: 'Description *'
        },
        specialization: {
          id: this.$props.id,
          name: '',
          description: '',
        }
      }
    },

    created() {
      if (this.specialization.id) {
        this.loadSpecialization();
      } else {
        this.newSpec = true;
        this.editable = true;
      }
    },

    methods: {
      loadSpecialization() {
        api.spec.loadSpecialization(this.specialization.id)
          .then(response => {
            this.load = true;
            const data = response.data;
            // specialization exist already
            if (data.success) {
              this.editable = false;
              this.newSpec = false;
              this.specialization = data.data;
            } else {
              this.editable = true;
              this.newSpec = true;
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      saveOrUpdate() {
        this.$v.specialization.$touch();
        if (this.$v.specialization.$error) {
          this.$clipicker.error('Please fill up required fields.')
        } else {
          // save data here;
          api.spec.saveOrUpdateSpec(this.specialization)
            .then(response => {
              const data = response.data;

              if (data.success) {
                this.$clipicker.info(data.message);
                this.newSpec = false;
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
      specialization: {
        name: { required },
        description: { required }
      }
    }
  }
</script>
<style lang="stylus" scoped>
  .form-container
    .q-input
      margin-bottom 8px
</style>


