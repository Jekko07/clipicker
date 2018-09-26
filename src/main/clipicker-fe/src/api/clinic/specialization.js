import api from './index'

export default {

  listSpecializations(filter) {
    return api.get('/admin/specialization/list', filter);
  },

  listClinicSpecializations(filter) {
    return api.get('/specialization/list', filter);
  },

  // only admins can add, edit, delete specialization

  loadSpecialization(id) {
    return api.get('/admin/specialization/loadSpecialization', {
      id: id
    });
  },

  saveOrUpdateSpec(spec) {
    return api.post('/admin/specialization/saveOrUpdate', spec);
  },

  deleteSpec(id) {
    return api.post('/admin/specialization/delete', {
      id: id
    });
  }
}
