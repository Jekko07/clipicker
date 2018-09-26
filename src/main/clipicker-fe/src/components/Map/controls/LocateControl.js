// provide ref="locate-control" in vue component
// vue component to hold the control

(function () {
  L.Control.LocateControl = L.Control.extend({
    onAdd(map) {
      this.component = this.options.vmComponent;
      this.locateControl = this.component.$refs['locate-control'];
      this.component.$el.querySelector('.leaflet-control-zoom.leaflet-bar.leaflet-control').appendChild(this.locateControl);

      return document.createElement('div');
    }
  });
})();

