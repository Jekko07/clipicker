export default {
  set(state, list) {
    state.list = list;
  },

  add(state, announcement) {
    state.list.push(announcement);
  },

  setUnseenCount(state, count) {
    state.unseenCount = count;
  }

}
