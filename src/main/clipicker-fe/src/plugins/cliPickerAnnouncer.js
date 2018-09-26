import api from '../api/patient';
import clinicApi from '../api/clinic';
import config from '../config/app.config';
import { date } from 'quasar';

require('../statics/js/websocket/sockjs.min');
require('../statics/js/websocket/stomp.min');

export default ({ app, router, store, Vue }) => {
  class CliPickerAnnouncer {
    constructor() {
      let baseUrl = config.isProd ? config.httpLocation : '';
      baseUrl += '/api/announcer';
      this.socket = new SockJS(baseUrl);

      this.announcements = [];
      this.ANNOUNCEMENT_SEEN_KEY = 'ANNOUNCEMENT_SEEN_KEY';
    }

    /**
     * load announcement -----------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

    loadAnnouncements() {
      api.guest.announcement.list()
        .then(response => {
          const data = response.data;
          if (data.success) {
            this.announcements = data.data;
            store.dispatch('announcement/set', this.announcements);
            this.loadUnseenAnnouncementCount();
          }
        })
        .catch(error => {
          console.log(`Error ${error}`);
        })
    }

    loadUnseenAnnouncementCount() {
      const seenAnnouncements = JSON.parse(localStorage.getItem(this.ANNOUNCEMENT_SEEN_KEY));
      let unseenAnnouncementCount = 0;

      if (store.getters['announcement/get'].list.length > 0) {
        store.getters['announcement/get'].list.map(actualAnnouncement => {
          const hasSeenAnnouncement = seenAnnouncements ? seenAnnouncements.some(seenAnnouncement => seenAnnouncement.id === actualAnnouncement.id) : false;
          if (!hasSeenAnnouncement) {
            unseenAnnouncementCount++;
          }
        });
      }
      store.dispatch('announcement/setUnseenCount', unseenAnnouncementCount);
    }


    async markSeenAnnouncement() {
      // todo: save the announcement which are already seen ->
      // valid_announcement vs seen_announcement
      await localStorage.setItem(this.ANNOUNCEMENT_SEEN_KEY, JSON.stringify(store.getters['announcement/get'].list));
      this.loadAnnouncements();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------
     */


    /**
     * websocket here --------------------------------------------------------------------------------------------------------------------------------------------------
     */

    initGuestWebsocket(isCordova) {

      this.stompClient = Stomp.over(this.socket);

      this.stompClient.connect({}, (frame) => {
        console.log('Connected: ', frame);


        // ******* Announcements subscriptions ***************************************
        this.stompClient.subscribe('/announcement/listener/createAnnouncement', (response) => {
          const announcement = JSON.parse(response.body);
          this.loadAnnouncements();

          // for android calendar event only -> push local notification
          if (isCordova) {
            this.pushLocalNotification(announcement);
          } else {
            Vue.prototype.$clipicker.info('Announcement: ' + announcement.name, 10000, 'info');
          }
        });

        // for android calendar event only
        if (isCordova) {
          this.stompClient.subscribe('/announcement/listener/deleteAnnouncement', (response) => {
            const announcement = JSON.parse(response.body);
            this.deleteCalendarEvent(announcement);
          });

          this.stompClient.subscribe('/announcement/listener/updateAnnouncement', (response) => {
            const changeAnnouncement = JSON.parse(response.body);
            this.updateCalendarEvent(changeAnnouncement);

          });
        }

      });
    }

    initClinicWebsocket(isAdmin) {
      this.adminStompClient = Stomp.over(this.socket);


      this.adminStompClient.connect({}, (frame) => {
        console.log('Connected: ', frame);

        if (isAdmin) {
          // ******* New registered clinics subscriptions ***************************************
          this.adminStompClient.subscribe('/adminNotify/listener/newRegisteredClinic', (response) => {
            const clinic = JSON.parse(response.body);
            //console.log('new registered clinic', clinic);
            store.dispatch('user/notifyPendingClinic', true);
          });
        } else {
          // ******* New registered clinics subscriptions ***************************************
          this.adminStompClient.subscribe(`/clinicNotify/listener/activated/${store.getters['user/get'].details.email}`, (response) => {
            const clinic = JSON.parse(response.body);
            Vue.prototype.$clipicker.info('Congratulations! your account has been activated');
            store.dispatch('user/setActivationStatus', true);
          });
        }


      });
    }

    showBadgeForInactiveClinics() {
      clinicApi.clinic.list({
        status: 0
      })
        .then(response => {
          const data = response.data;
          if (data.success) {
            const inactiveClinics = data.data;
            if (inactiveClinics.length > 0) {
              store.dispatch('user/notifyPendingClinic', true);
            }
          }
        })
        .catch(error => {
          console.log(`Error ${error}`)
        })
    }


    // for cordova devices only
    // ************************************************************************************************************************************
    // start

    pushLocalNotification(announcement) {
      Vue.prototype.$q.cordova.plugins.notification.local.schedule({
        id: announcement.id,
        title: announcement.name,
        text: announcement.description + '\n' + date.formatDate(announcement.startTime, 'MMMM Do YYYY hh:mm A') + '\n' + announcement.location,
        led: { color: '#549046', on: 500, off: 500 },
        vibrate: true,
        actions: [{
          id: 'addCalendar',
          title: 'Add to calendar'
        }]
      });

      // if yes
      Vue.prototype.$q.cordova.plugins.notification.local.on('addCalendar', async (notification, eopts) => {
        api.guest.announcement.load(notification.id)
          .then(response => {
            const data = response.data;
            if (data.success) {
              const announcement = data.data;
              this.createCalendarEvent(announcement);
            }
          }).catch(error => {
          console.log(`Error ${error}`);
          alert(`Error ${error}`);
        })
      });
    }

    createCalendarEvent(announcement, isModified) {
      const success = (message) => {
        if (message && !isModified) {
          //Vue.prototype.$clipicker.info('Event successfully added to calendar.', 5000, 'info');
        }
      };
      const error = (message) => {
        //alert("Error createCalendarEvent: " + message);
      };

      const dataEvent = this.createDataEvent(announcement);
      window.plugins.calendar.createEvent(dataEvent.title, dataEvent.location, dataEvent.notes, dataEvent.startTime, dataEvent.endTime, success, error);
    }

    deleteCalendarEvent(announcement, isModified) {
      const success = (message) => {
        if (message && !isModified) {
          if (isModified) {
            //Vue.prototype.$clipicker.info('Event was modified to calendar.', 5000, 'info');
          } else {
            //Vue.prototype.$clipicker.info('Event was postponed to calendar.', 5000, 'info');
          }

        }
      };
      const error = (message) => {
        //alert("Error deleteCalendarEvent: " + message);
      };

      const dataEvent = this.createDataEvent(announcement);
      window.plugins.calendar.deleteEvent(dataEvent.title, dataEvent.location, dataEvent.notes, dataEvent.startTime, dataEvent.endTime, success, error);

    }

    updateCalendarEvent(changeAnnouncement) {
      const this1 = this;
      this.deleteCalendarEvent(changeAnnouncement.originalAnnouncement, true);
      setTimeout(() => {
        this1.createCalendarEvent(changeAnnouncement.newAnnouncement, true);
      }, 8000)
    }

    createDataEvent(announcement) {
      return {
        title: announcement.name,
        location: announcement.location,
        notes: announcement.description,
        startTime: new Date(announcement.startTime),
        endTime: new Date(announcement.expireTime)
      }

    }


    // end
    // ************************************************************************************************************************************

    /**
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------
     */

  }

  Vue.prototype.$cliPickerAnnouncer = new CliPickerAnnouncer();
}
