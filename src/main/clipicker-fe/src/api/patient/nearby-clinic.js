import api from './index'

export default {
  list(filters) {
    return api.get('/guest/clinic/list', filters);
  },

  listSpecialization() {
    return api.get('/guest/specialization/list');
  },

  listSymptom() {
    return api.get('/guest/symptom/list');
  }

}
