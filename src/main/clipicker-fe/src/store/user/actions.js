export default {
  login({ commit, state }, payload) {
    if (payload.authenticated) {
      commit('login', payload)
    }
  },

  logout({ commit }) {
    commit('logout')
  },

  setAdmin({ commit }, isAdmin) {
    commit('setAdmin', isAdmin);
  },

  notifyPendingClinic({ commit }, isNotify) {
    commit('notifyPendingClinic', isNotify);
  },

  setActivationStatus({ commit }, isActive) {
    commit('setActivationStatus', isActive);
  },

  setRegistrationStatus({ commit }, isRegistered) {
    commit('setRegistrationStatus', isRegistered);
  }
}
