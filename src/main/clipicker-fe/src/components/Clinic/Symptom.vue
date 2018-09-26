<template>
  <div>
    <div v-show="load">
      <div class="form-container">
        <div class="q-mb-sm q-body-1" v-show="editable" >
          <span> Please fill up required fields:</span>
        </div>
        <q-btn label="Edit" icon="edit" color="primary" class="q-mb-sm" v-show="!newSymp && !editable" @click="edit"/>
        <q-input :stack-label="label.name" v-model="symptom.name" :disable="!editable" />
        <q-input :stack-label="label.description" v-model="symptom.description" type="textarea" rows="4" :max-height="100" :disable="!editable" />

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
        newSymp: false,
        label: {
          name: 'Symptom name *',
          description: 'Description *'
        },
        symptom: {
          id: this.$props.id,
          name: '',
          description: '',
        }
      }
    },

    created() {
      if (this.symptom.id) {
        this.loadSymptom();
      } else {
        this.newSymp = true;
        this.editable = true;
      }
    },

    methods: {
      loadSymptom() {
        api.symptom.loadSymptom(this.symptom.id)
          .then(response => {
            this.load = true;
            const data = response.data;
            // symptom exist already
            if (data.success) {
              this.editable = false;
              this.newSymp = false;
              this.symptom = data.data;
            } else {
              this.editable = true;
              this.newSymp = true;
            }
          })
          .catch(error => {
            console.log(`Error ${error}`);
          })
      },

      saveOrUpdate() {
        this.$v.symptom.$touch();
        if (this.$v.symptom.$error) {
          this.$clipicker.error('Please fill up required fields.')
        } else {
          // save data here;
          api.symptom.saveOrUpdateSymptom(this.symptom)
            .then(response => {
              const data = response.data;

              if (data.success) {
                this.$clipicker.info(data.message);
                this.newSymp = false;
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
      symptom: {
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


