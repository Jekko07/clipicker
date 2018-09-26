import axios from 'axios'
import api from '../utils'

import nearbyClinic from './nearby-clinic';
import announcement from './announcement';

export default {
  get(url, request) {
    return api.get(axios, url, request);
  },

  post(url, request) {
    return api.post(axios, url, request);
  },

  guest: {
    nearbyClinic: nearbyClinic,
    announcement: announcement
  }
}
