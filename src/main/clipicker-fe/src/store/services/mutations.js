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

  doneQuerySearch(state) {
    state.querySearch = 0;
  }
}
