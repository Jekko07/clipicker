export default {
  set({ commit, state }, filter) {
    commit('set', filter);
  },

  show({ commit, state }, isShow) {
    commit('show', isShow)
  },

  querySearch({ commit, state }) {
    commit('querySearch');
  },

  querySearchAll({ commit }, state) {
    commit('querySearchAll');
  },

  doneQuerySearch({ commit }) {
    commit('doneQuerySearch');
  },

  setClinicMaxNearbyDistance({ commit }, distance) {
    commit('setClinicMaxNearbyDistance', distance);
  }
}
