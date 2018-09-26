import api from './index'

export default {
  list() {
    return api.get('/guest/announcement/list');
  },

  load(id) {
    return api.get('/guest/announcement/load', {
      id: id
    });
  }
}
