<template>
  <q-page>
    <div id="signup-container">

      <!-- custom registration -->
      <div class="auth-container">

        <div class="okta-sign-in-header auth-header">
          <img :src="logo" class="auth-org-logo" alt="">
          <div data-type="beacon-container" class="beacon-container"></div>
        </div>

        <div class="signup-wrapper q-pt-md">

          <div class="signup-section" v-show="!showWelcome">
            <h2> Create Account </h2>

            <div style="padding: 0 20px">
              <div class="q-my-lg">

                <q-input v-model="user.firstName" placeholder="First Name *" class="q-my-lg register-input" />
                <q-input v-model="user.lastName" placeholder="Last Name *" class="q-my-lg register-input" />

                <q-input v-model="user.email" placeholder="Email *" class="q-my-lg register-input" type="email"
                         :before="[{
                  icon: 'mail'
                }]"
                />
                <div class="q-px-sm">
                  <img :src="captchaImgUrl" style="border: none; float: left"/>
                  <q-btn round icon="refresh" color="primary" @click="reloadCaptchaImg" />
                </div>

                <q-input v-model="user.captchaCode" placeholder="Captcha code *" class="q-my-lg register-input"
                         maxlength="6"
                         :before="[{
                  icon: 'vpn_key'
                }]"
                />
              </div>
              <div class="q-py-sm">
                <q-btn label="Register" class="register-btn" @click="register" />
              </div>
            </div>
          </div>

          <div class="signup-success-section" v-show="showWelcome">
            <div v-if="welcomeName != null">
              <p style="font-weight: bold"> Welcome {{ welcomeName }}, </p>
              <p class="q-mt-md"> You have successfully registered your account with Clipicker using Okta! </p>
              <p> Please check your email to activate. </p>

              <p class="q-mt-xl"> Congratulations once again! </p>
            </div>
          </div>

          <div class="auth-footer q-mt-lg">
            <a href="#" class="link help" @click.prevent="signin"> Back to Sign In </a>
          </div>
        </div>
      </div>

    </div>
  </q-page>

</template>

<script>
import appConfig from '../../config/app.config'
import api from '../../api/clinic'
import { required, email } from 'vuelidate/lib/validators'

  export default {
    name: "Register",
    data() {
      return {
        logo: appConfig.logo,
        captchaImgUrl: appConfig.server + '/captcha/generate',
        user: {
          firstName: '',
          lastName: '',
          email: '',
          captchaCode: ''
        },
        successResponse: false,
        welcomeName: null
      }
    },
    validations: {
      user: {
        firstName: { required },
        lastName: { required },
        email: { required, email },
        captchaCode: { required }
      }
    },
    computed: {
      showWelcome() {
        return this.successResponse;
      }
    },
    methods: {
      signin() {
        this.$router.push({
          path: '/clinic/signin'
        })
      },
      register() {
        this.$v.user.$touch();
        if (this.$v.user.$error) {
          this.$clipicker.error('Please fill up required fields.')
        } else {
          api.clinic.register(this.user)
            .then(response => {
              if (response.data.success) {
                this.welcomeName = response.data.data.profile.firstName;
                this.$clipicker.info('Registered Successfully');
                this.successResponse = true;
              } else {
                this.$clipicker.error(response.data.message);
              }
            })
            .catch(error => {
              console.log(`Error ${error}`);
            })
        }
      },
      reloadCaptchaImg() {
        this.captchaImgUrl = appConfig.server + '/captcha/generate?p=' + (new Date().getTime())
      }
    }
  }
</script>

<style lang="stylus" scoped>
  @import '~src/css/app.styl';

  .signup-container
    padding 60px 42px 20px
  .auth-container
    font-size: 14px;
    line-height: 1.5;
    margin-top: 10px;
    text-align: center;
    text-transform: none;
    font-family: "montserrat", Arial, Helvetica, sans-serif;
    color: #777


    h2, h3
      font-size: 15px;
      font-weight bold
      color: #5e5e5e;

   .link
      color #777;
      text-align left !important
      font-size 12px

    .register-btn
      width 100% !important
      height 50px
      display block

  button
    color #fff !important
    okta-bg-color-fn()

  .auth-header
    padding: 25px 90px 25px;
    position: relative;
    border-bottom: 1px solid #ddd;
    z-index: 10;
    -webkit-transition: padding-bottom .4s;
    transition: padding-bottom .4s;

  .auth-org-logo
    display: block;
    margin: 0 auto;
    max-width: 100px;
    max-height: 100px;

  .signup-wrapper
    max-width 316px
    margin 0 auto

  .auth-footer
    text-align center
    margin 40px 20px 0 20px !important
    padding: 30px 50px;
    -webkit-transition: padding-top .4s;
    transition: padding-top .4s;
    border-top: 1px solid #ddd;
    margin-top: 5px;

  .link
      margin-left: 10px;
      okta-color-fn()
      text-decoration: none;
      &:hover
        text-decoration underline

  .register-input
    font-size 13px !important
    border 1px solid #e2e0e0 !important
    padding 0 10px
    height 40px
    &:focus
      okta-bg-color-fn()

</style>
