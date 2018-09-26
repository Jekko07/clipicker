/*
export function someMutation (state) {
}
*/
export default {
  login(state, payload) {
    state.authenticated = true
    state.details = payload.details
    state.token = payload.token
  },

  logout(state) {
    state.authenticated = false
    state.details = {}
    state.token = ''
    state.isAdmin = false;
    state.notifyPendingClinic = false;
  },

  setAdmin(state, isAdmin) {
    state.isAdmin = isAdmin;
  },

  notifyPendingClinic(state, isNotify) {
    state.notifyPendingClinic = isNotify;
  },

  setActivationStatus(state, isActive) {
    state.isActivated = isActive;
  },

  setRegistrationStatus(state, isRegistered) {
    state.isRegistered = isRegistered;
  }
}
