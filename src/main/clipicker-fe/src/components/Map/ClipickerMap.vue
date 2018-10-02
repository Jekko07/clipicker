<template>
  <div class="fullscreen2">
    <div>
      <div id="clipicker-map" class="fullscreen" v-if="loadMap">
      </div>
      <!-- for map buttons function -->
      <div class="buttons-container" v-if="mapLoaded" style="z-index: 999999999">
        <q-btn
          round
          color="primary"
          @click="saveSingleMarker"
          class="fixed"
          icon="where_to_vote"
          style="right: 75px; bottom: 18px; z-index: 999999"
          v-if="mode === 'single'"
        />

        <!-- for filter -->
        <q-btn
          round
          color="primary"
          @click="showServicesFilter"
          class="fixed"
          icon="fas fa-filter"
          style="right: 140px; bottom: 18px; z-index: 999999"
          v-if="mode === 'interactive'"
        />

        <q-btn
          round
          color="primary"
          @click="queryAllClinics"
          class="fixed"
          icon="location_on"
          style="right: 75px; bottom: 18px; z-index: 999999"
          v-if="mode === 'interactive'"
        />

        <q-btn
          round
          color="primary"
          @click="closeMap"
          class="fixed"
          icon="cancel"
          style="right: 18px; bottom: 18px; z-index: 999999"
        />

        <div v-if="showServicesFilterDialog">
          <clinic-services-filter :max-distance="maxDistance" />
        </div>


      </div>

      <!-- element tools to use in a map -> using ref -->
      <div v-show="false">

          <!-- for current location control -->
          <a  ref="locate-control" @click="showMyCurrentLocation">
            <i class="q-icon fas fa-map-marker-alt" style="font-size: 2em; z-index: 99999" />
          </a>

        <div>
          <!-- ref for get map directions -->
          <div class="clinic-marker-info" v-for="clinic in listFilterClinics" :key="clinic.id" :ref="'marker-info-' + clinic.id ">
            <div>
              <b>{{ clinic.name }}</b>
              <div class="q-mt-sm">
                {{ clinic.address }}
              </div>
              <div class="q-mt-sm">
                Business hours: {{ clinic.bus_start_time }} : {{ clinic.bus_end_time }}
              </div>
              <div class="q-mt-sm">
                Contact number: {{ clinic.contactNumber }}
              </div>
              <div class="q-mt-sm">
                Distance: {{ clinic.distance ? round(clinic.distance/1000, 2) + ' km' : 'not yet set'  }}
              </div>
            </div>

            <!-- direction is for interactive mode refs only -->
            <div style="width: 150px; margin: 0 auto;" class="flex flex-center" v-if="mode === 'interactive'">
              <q-btn label="Get Directions" size="sm" icon="directions" color="primary" @click="getDirections(clinic)" />
            </div>
          </div>
        </div>

      </div>

    </div>
  </div>
</template>

<script>
import appConfig from '../../config/app.config';
import ClinicServicesFilter from '../Clinic/ClinicServicesFilter'

require('leaflet/dist/leaflet.css');
require('leaflet.fullscreen/Control.FullScreen.css')
require('leaflet-providers');
require('leaflet');
require('leaflet.fullscreen');

require('leaflet-routing-machine');
require('leaflet-routing-machine/dist/leaflet-routing-machine.css')

require('../../statics/js/leaflets/plugins/leaflet-pulse-icon/L.Icon.Pulse.min')
require('../../statics/js/leaflets/plugins/leaflet-pulse-icon/L.Icon.Pulse.min.css')

require('../../statics/js/leaflets/plugins/leaflet-awesome-markers/leaflet.awesome-markers')
require('../../statics/js/leaflets/plugins/leaflet-awesome-markers/leaflet.awesome-markers.css')
require('./controls/LocateControl');

  export default {
    name: "ClipickerMap",
    props: [
      'options'
    ],
    components: {
      ClinicServicesFilter
    },
    data() {
      return {
        mode: this.$props.options.mode,  // (mode=single -> use for setting map location with single marker,     mode=interactive -> use for setting map location with multiple markers and directions, etc. )
        showMap: this.$props.options.load,
        closeMapFn: this.$props.options.closeMapFn,
        mapLoaded: false,
        opts: this.$props.options,
        clinics: this.$props.options.clinics,
        currentLocation: {},

        // use this object for single clinic use only
        single: {
          clinic: {},
        },

        // use this object for multiple clinics
        interactive: {
          clinics: [],
          maxDistance: 0,
        },
        routeControl: null,

        // leaflets and mapbox settings
        L: L,
        map: {},
        maxZoom: 16,
        defaultZoom: 14,
        defaultLocation: [ 7.061703, 125.610835 ],  // new washington default location

        // clinic marker:
        clinicMarker: L.AwesomeMarkers.icon({
          prefix: 'fas',
          icon: 'fa-notes-medical',
          markerColor: 'red',
          version: 5
        }),

        // filter for services
        servicesFilterModal: true,
        showAllClinics: false,
        showCurrentLocationError: false

      }
    },

    computed: {
      loadMap() {
        this.opts = this.$props.options;
        const load = this.showMap = this.$props.options.load;
        if (!load) {
          if (this.map != null && this.map != undefined && this.map.remove) {
            this.map.remove();
          }
        } else {
          if (!this.mapLoaded) {
            if (this.mode === 'single' || ( this.mode === 'interactive')) {
              setTimeout(this.initMap, 1);
            }
          } else {
            if (this.$store.getters['services/get'].querySearch > 0 && this.opts.clinics && this.opts.clinics.length > 0) {
              const RENDER_MARKER_DELAY_IN_SECONDS = 2000;
              this.resetInteractiveMode();
              this.interactive.clinics = this.opts.clinics;
              if (this.showAllClinics) {
                setTimeout(() => {
                  this.calculateDistance();
                  this.resetClinicMarkers();
                  this.showClinicMarkers();
                  this.$store.dispatch('services/doneQuerySearch');
                }, RENDER_MARKER_DELAY_IN_SECONDS);
              } else {
                setTimeout(() => {
                  this.calculateDistance();
                  this.resetClinicMarkers();
                  this.showClinicMarkers();
                  this.filterMarkerByDistance();
                  this.$store.dispatch('services/doneQuerySearch');
                }, RENDER_MARKER_DELAY_IN_SECONDS);
              }
            }
          }
        }
        return this.showMap;
      },

      listFilterClinics() {
        return this.interactive.clinics;
      },

      showServicesFilterDialog() {
        return this.$store.getters['services/get'].show;
      },

      maxDistance() {
        if (this.currentLocation.lat > 0) {
          this.interactive.clinics = this.opts.clinics;
          this.calculateDistance();
          const maxDistance = this.interactive.maxDistance > 0 ? Math.round(Math.round(this.interactive.maxDistance)/1000) + 1 : 100;
          if (maxDistance == 1) {
            this.$store.dispatch('services/show', false);
            this.setCurrentLocation();
            setTimeout(() => {
              this.$store.dispatch('services/show', true);
            }, 2000);
          }

          return maxDistance;
        } else {
          this.interactive.clinics = this.opts.clinics;
          this.$store.dispatch('services/show', false);
          this.setCurrentLocation();
          setTimeout(() => {
            this.$store.dispatch('services/show', true);
          }, 2000);
          if (!this.showCurrentLocationError) {
            this.$clipicker.error('Current location not yet found! Please wait...');
            this.showCurrentLocationError = true;
          }

        }

      }
    },

    watch: {
      currentLocation(value) {
        if (value.lat) {
          this.calculateDistance();
        }
      }
    },

    beforeDestroy() {
      this.closeMap();
    },

    mounted() {
      // todo: detemine what map mode behavior
      const debug = 'asas212121212';
    },

    methods: {
      reset() {
        this.showMap = false;
        this.mapLoaded = false;
        this.resetModeSettings();
      },

      resetModeSettings() {
        if (this.isSingleMode()) {
          this.resetSingleMode();
        } else {
          this.resetInteractiveMode();
        }
      },

      isSingleMode() {
        return this.mode == 'single';
      },

      closeMap() {
        this.reset();
        if (this.closeMapFn) {
          this.closeMapFn();
        }
      },

      initMap() {
        if (!this.$el.querySelector('#clipicker-map')) {
          return;
        }
        this.setupMapContainer();
        if (this.isSingleMode()) {
          this.initSingleMode();
        } else {
          this.initInteractiveMode();
        }
      },

      setupMapContainer() {
        this.map = this.L.map('clipicker-map');
        this.map.attributionControl = false;
        //this.map.setView(this.defaultLocation, this.defaultZoom);
        this.setCurrentLocation();
        this.L.tileLayer(appConfig.application.map.mapbox.publicStyle).addTo(this.map);

        this.map.addControl(new this.L.Control.FullScreen());
        this.map.addControl(new this.L.Control.LocateControl({
          vmComponent: this
        }));
        this.mapLoaded = true;
      },

      setCurrentLocation() {
        this.map.setView(this.defaultLocation, this.defaultZoom);
        this.map.locate({maxZoom: 16});
        this.map.on('locationfound', (e) => {
          const currentLocationIcon = this.L.icon.pulse({iconSize:[20,20],color:'#487edc'});
          if (this.currentPlaceMarker != null) {
            this.currentPlaceMarker.remove();
          }
          this.currentLocation = e.latlng;
          this.calculateDistance();
          this.currentPlaceMarker = this.L.marker(e.latlng, { icon: currentLocationIcon }).addTo(this.map);
          this.map.setView(e.latlng, this.defaultZoom);
        }, this);
      },


      showMyCurrentLocation(evt) {
        this.map.locate({maxZoom: 16});
        this.map.on('locationfound', (e) => {
          this.map.flyTo(e.latlng, 16);
          const currentLocationIcon = this.L.icon.pulse({iconSize:[20,20],color:'#487edc'});
          if (this.currentPlaceMarker != null) {
            this.currentPlaceMarker.remove();
          }
          this.currentPlaceMarker = this.L.marker(e.latlng, { icon: currentLocationIcon }).addTo(this.map);
        }, this);

        evt.stopPropagation();
      },

      createMarker(latLngLocation, clinic) {
        const marker = this.L.marker(latLngLocation, { icon: this.clinicMarker }).addTo(this.map);
        if (!this.isSingleMode()) {
          const markerInfo = this.$refs['marker-info-' + clinic.id][0];
          marker.bindPopup(markerInfo);
        } else {
          marker.bindPopup(this.constructMarkerInfo(clinic));
        }

        return marker;
      },


      // start single mode ***************************************************************************************************************************************************

      // init single mode map behavior
      initSingleMode() {
        this.single.clinic = this.opts.clinic;
        this.map.on('click', this.singleMarkerListener);
        this.setSingleMarker();

      },

      setSingleMarker() {
        if (this.single.clinic.lat != null && this.single.clinic.lat != null) {
          this.single.clinic.marker = this.createMarker([this.single.clinic.lat, this.single.clinic.lng], this.single.clinic);
        }
      },

      saveSingleMarker(evt) {
        let lat = null;
        let lng = null;
        if (this.single.clinic.marker) {
          lat = this.single.clinic.marker.getLatLng().lat;
          lng = this.single.clinic.marker.getLatLng().lng;

        }
        if (lat == null || lng == null) {
          this.$clipicker.error('Please select the clinic map location!');
          evt.stopPropagation();
          return;
        }

        this.single.clinic.lat = lat;
        this.single.clinic.lng = lng;
        this.closeMap();
      },

      singleMarkerListener(e) {
        if (this.single.clinic.marker) {
          this.single.clinic.marker.remove();
        }

        this.single.clinic.marker = this.createMarker(e.latlng);
      },

      resetSingleMode() {
        this.$delete(this.single.clinic, 'marker');
      },

      constructMarkerInfo(clinic) {
        if (clinic == null || clinic == undefined) {
          return '';
        }
        const name = clinic.name ? clinic.name : '';
        const address  = clinic.address ? clinic.address : '';
        const markerInfo = `<b>${name}</b> <br/> ${address} `;

        return markerInfo;
      },

      // end single mode *****************************************************************************************************************************************************




      // start multiple mode *************************************************************************************************************************************************

      // init interactive mode behavior
      initInteractiveMode() {
        this.interactive.clinics = this.opts.clinics;
      },

      resetInteractiveMode() {
        this.resetClinicMarkers();
        this.$delete(this.interactive, 'clinics');
        if (this.routeControl != null) {
          this.routeControl.remove();
        }
      },

      showClinicMarkers() {
        this.interactive.clinics.map(clinic => {
          clinic.marker = this.createMarker([clinic.lat, clinic.lng], clinic);
        });
      },

      resetClinicMarkers() {
        if (!this.interactive.clinics) {
          return;
        }

        this.interactive.clinics.map(clinic => {
          if (clinic.marker) {
            clinic.marker.remove();
          }
        });
      },

      getDirections(clinicDestination) {
        if (this.routeControl === null) {
          this.routeControl = this.createRoute(this.L.latLng(this.currentLocation.lat, this.currentLocation.lng), this.L.latLng(clinicDestination.lat, clinicDestination.lng));
        } else {
          this.routeControl.remove();
          this.routeControl = this.createRoute(this.L.latLng(this.currentLocation.lat, this.currentLocation.lng), this.L.latLng(clinicDestination.lat, clinicDestination.lng));
        }

        this.routeControl.addTo(this.map);

      },

      createRoute(start, destination) {
        const routeControl = this.L.Routing.control({
          routeWhileDragging: true,
          reverseWaypoints: true,
          showAlternatives: true,
          collapsible: true,
          show: false,
          altLineOptions: {
            styles: [
              {color: 'black', opacity: 0.15, weight: 9},
              {color: 'white', opacity: 0.8, weight: 6},
              {color: 'blue', opacity: 0.5, weight: 2}
            ]
          },
          waypoints: [ start, destination ],
          router: this.L.Routing.mapbox(appConfig.application.map.mapbox.token)
        });

        return routeControl;
      },

      filterMarkerByDistance() {
        const maxDistance = this.$store.getters['services/get'].filter.distance.max * 1000; // in meters
        this.interactive.clinics.map(clinic => {
           if (maxDistance < clinic.distance) {
             if (clinic.marker) {
               clinic.marker.remove();
             }
           }
        })

      },

      queryAllClinics() {
        this.$store.dispatch('services/querySearchAll');
        this.showAllClinics = true;
      },

      calculateDistance() {
        this.interactive.clinics.map(clinic => {
          clinic.distance = this.currentLocation.distanceTo(this.L.latLng(clinic.lat, clinic.lng));
        })
        this.interactive.clinics = this.interactive.clinics.sort((a, b) => a.distance - b.distance);
        this.interactive.maxDistance = this.interactive.clinics && this.interactive.clinics.length > 0 ? this.interactive.clinics[this.interactive.clinics.length - 1].distance : 1;
      },


      // end multiple mode ***************************************************************************************************************************************************

      showServicesFilter() {
        this.showAllClinics = false;
        this.$store.dispatch('services/show', true);
      },

      round(value, decimals) {
        return Number(Math.round(value+'e'+decimals)+'e-'+decimals);
      }

    }
  }
</script>

<style lang="stylus">
  .leaflet-bottom
    .leaflet-control-attribution
      display none !important

</style>
