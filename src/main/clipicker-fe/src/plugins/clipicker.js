import { Platform } from 'quasar';
import { date } from 'quasar'

export default ({ app, router, store, Vue }) => {
  class Clipicker {
    constructor() {
      this.OKTA_KEY = 'okta-token-storage';
      this.REFRESH_TIME_IN_MINUTES = 30;
      this.initTokenRefresh = false;
      this.IS_CLINIC_TYPE = 'IS_CLINIC_TYPE';
      this.IS_USER_TYPE_SET = 'IS_USER_TYPE_SET';

      this.loadOkta();
    }

    login() {
      const token = this.okta.accessToken.accessToken;
      const user = this.okta.idToken.claims;
      store.dispatch('user/login', {
        authenticated: true,
        token: token,
        details: user
      });
    }

    loadOkta() {
      this.okta = localStorage.getItem(this.OKTA_KEY) != null ? JSON.parse(localStorage.getItem(this.OKTA_KEY)) : null;
      this.instance = Vue.prototype;
      this.setUserAgent();
    }

    isAuthenticated() {
      this.loadOkta();
      if (this.okta != null && this.okta.accessToken != null && this.okta.accessToken.tokenType.toLowerCase() === 'bearer') {
        this.login();
        this.initAuthRefresh();
        return true;
      } else {
        return false;
      }
    }

    async initAuthRefresh() {
      const ONE_SECOND = 1000;
      const ONE_MINUTE = 60 * ONE_SECOND;
      if (!this.initTokenRefresh) {
        setInterval(async () => {
          const accessToken = await this.instance.$auth.getAccessToken();
          await this.loadOkta();
          console.log('refresh token', accessToken);
        }, ONE_MINUTE * this.REFRESH_TIME_IN_MINUTES);
      }
    }

    logout(isForce) {
      if (!isForce) {
        this.instance.$auth.logout();
      } else {
        localStorage.removeItem(this.OKTA_KEY);
      }
      store.dispatch('user/logout');
      this.okta = null;
      router.push('/clinic');
    }

    setUserAgent() {
      if (Platform.is.cordova) {
        /*UserAgent.get((ua) => {
          alert(JSON.stringify(ua));

        });*/
        //UserAgent.set('Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19');
      }
    }

    error(msg) {
      this.instance.$q.notify({
        message: msg,
        type: 'negative',
        color: 'primary',
        icon: 'warning',
        textColor: 'white',
        position: 'center',
        timeout: 2000
      });
    }

    info(msg, delayed, iconType) {
      this.instance.$q.notify({
        message: msg,
        type: 'positive',
        color: 'primary',
        icon: iconType ? iconType : 'done',
        textColor: 'white',
        position: 'center',
        timeout: delayed ? delayed : 2000
      });
    }

    /**
     * adding reusable callback call anywhere
     * set callback from origin and invokedCallback anywhere and reset after it was used)
     * @param callback
     */
    setCallback(callback) {
      this.callback = callback;
    }

    invokedCallback() {
      if (this.callback) {
        this.callback();
        this.callback = null;
      }
    }

    isUserTypeSet() {
      return localStorage.getItem(this.IS_USER_TYPE_SET) && localStorage.getItem(this.IS_USER_TYPE_SET) == 'true' ? true : false;
    }

    setClinicType(isClinicType) {
      localStorage.setItem(this.IS_USER_TYPE_SET, 'true');
      localStorage.setItem(this.IS_CLINIC_TYPE, isClinicType ? 'true' : 'false');
    }

    isClinicType() {
      return localStorage.getItem(this.IS_CLINIC_TYPE) == 'true' ? true : false;
    }

    buildBusTime(time, isFormatted) {
      return isFormatted ? date.formatDate(date.buildDate({
        hours: time.split(':')[0],
        minutes: time.split(':')[1]
      }), 'hh:mm A') : date.buildDate({
        hours: time.split(':')[0],
        minutes: time.split(':')[1]
      });
    }
  }

  Vue.prototype.$clipicker = new Clipicker();
}
