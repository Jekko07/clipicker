import api from './index'

export default {

    // only admin can list the clinic and modify their information
    list(filter) {
        return api.get('/admin/clinic/list', filter);
    },

    // anyone can register
    // anyone can add, edit the clinic info but only the clinic they own as long as they're registered and was able to login successfully

    register(user) {
        return api.post('/clinic/register', {
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email,
            captchaCode: user.captchaCode
        }, true)
    },

    loadClinic(email) {
        return api.get('/clinic/loadClinic', {
          email: email
        });
    },

    saveOrUpdate(clinic) {
      return api.post('/clinic/saveOrUpdate', clinic);
    },

    delete(email) {
      return api.post('/admin/clinic/delete', {
        email: email
      });
    },

    saveClinicServices(form) {
      return api.post('/clinic/services/save', {
        specIdList: form.specIdList,
        symptomIdList: form.symptomIdList,
        clinicEmail: form.clinicEmail
      })
    },

    loadClinicServices(clinicEmail) {
      return api.get('/clinic/services/load', clinicEmail);
    },

    activate(clinicEmail) {
      return api.post('/admin/clinic/activate', {
        email: clinicEmail
      })
    }
}
