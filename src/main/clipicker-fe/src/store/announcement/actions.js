export default {
  set({ commit, state }, list) {
    commit('set', list);
  },

  add({ commit, state }, announcement) {
    commit('add', announcement)
  },


  setUnseenCount({ commit, state }, count) {
    commit('setUnseenCount', count);
  }
}
