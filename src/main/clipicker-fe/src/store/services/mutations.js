export default {
  set(state, filter) {
    state.filter = filter;
  },

  show(state, isShow) {
    state.show = isShow;
  },

  querySearch(state) {
    state.querySearch = state.querySearch + 1;
  },

  querySearchAll(state) {
    state.querySearch = state.querySearch + 1;
    state.querySearchAll = true;
  },

  doneQuerySearch(state) {
    state.querySearch = 0;
  },

  setClinicMaxNearbyDistance(state, distance) {
    state.filter.distance.max = distance;
  }
}
