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

  doneQuerySearch({ commit }) {
    commit('doneQuerySearch');
  }
}
