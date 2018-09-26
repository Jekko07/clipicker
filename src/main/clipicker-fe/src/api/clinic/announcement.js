import api from './index'

export default {

  list(filter) {
    return api.get('/admin/announcement/list', filter);
  },

  load(id) {
    return api.get('/admin/announcement/load', {
      id: id
    });
  },

  saveOrUpdate(announcement) {
    return api.post('/admin/announcement/saveOrUpdate', announcement);
  },

  delete(id) {
    return api.post('/admin/announcement/delete', {
      id: id
    });
  }
}
