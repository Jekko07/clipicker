import api from './index'

export default {

  listSymptoms(filter) {
    return api.get('/admin/symptom/list', filter);
  },

  listClinicSymptoms(filter) {
    return api.get('/symptom/list', filter);
  },

  loadSymptom(id) {
    return api.get('/admin/symptom/loadSymptom', {
      id: id
    });
  },

  saveOrUpdateSymptom(symptom) {
    return api.post('/admin/symptom/saveOrUpdate', symptom);
  },

  deleteSymptom(id) {
    return api.post('/admin/symptom/delete', {
      id: id
    });
  }
}
