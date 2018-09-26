import config from '../config/app.config'
import Vue from 'vue';

export default {
  get(axios, url, request) {
    axios.defaults.baseURL = config.server;

    return axios({
      method: 'get',
      url: url,
      params: request
    })
      .then(response => Promise.resolve(response))
      .catch(error => {
        if (error.request.status === 401) {
          Vue.prototype.$clipicker.logout(true);
        }
        return Promise.reject(error)
      });
  },
  post(axios, url, request) {
    axios.defaults.baseURL = config.server;

    return axios.post(url, request)
      .then(response => Promise.resolve(response))
      .catch(error => {
        if (error.request.status === 401) {
          Vue.prototype.$clipicker.logout(true);
        }
        return Promise.reject(error);
      });
  }
}
