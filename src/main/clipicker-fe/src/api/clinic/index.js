import axios from 'axios'
import api from '../utils'
import store from '../../store';

// list of apis here
import clinic from './clinic';
import spec from './specialization';
import symptom from './symptom';
import announcement from './announcement'


export default {
  get(url, request, nonAuth) {
    if (!nonAuth) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${store.getters['user/get'].token}`;
    }
    return api.get(axios, url, request);
  },

  post(url, request, nonAuth) {
    if (!nonAuth) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${store.getters['user/get'].token}`;
    }
    return api.post(axios, url, request);
  },

  clinic: clinic,
  spec: spec,
  symptom: symptom,
  announcement: announcement

}
