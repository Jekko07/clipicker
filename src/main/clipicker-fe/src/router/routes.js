import Auth from '@okta/okta-vue';
import store from "../store";

import Landing from '../layouts/Landing.vue';
import UserSelection from '../pages/UserSelection';

import PatientLayout from '../layouts/PatientLayout';
import NearbyClinic from '../pages/Patient/NearbyClinic';
import PatientClinicList from '../pages/Patient/PatientClinicList'
import PatientAnnouncements from '../pages/Patient/Announcements';

import ClinicLayout from '../layouts/ClinicLayout';
import Authenticate from '../pages/Clinic/Homepage';
import Signin from '../pages/Clinic/Signin';
import Register from '../pages/Clinic/Register';
import DashboardLayout from '../layouts/DashboardLayout';

import ClinicInfo from '../pages/Clinic/Dashboard/ClinicInfo'
import ClinicServices from '../pages/Clinic/Dashboard/ClinicServices'

import ClinicTabList from '../pages/Clinic/Admin/ClinicTabList'
import ClinicList from '../pages/Clinic/Admin/ClinicList';
import ClinicInactiveList from '../pages/Clinic/Admin/ClinicInactiveList'
import SpecializationList from '../pages/Clinic/Admin/SpecializationList';
import SymptomList from '../pages/Clinic/Admin/SymptomList';
import AnnouncementList from '../pages/Clinic/Admin/AnnouncementList';

import ListTemplate from '../pages/Clinic/Admin/ListTemplate'
import Clinic from '../components/Clinic/Clinic'
import Specialization from '../components/Clinic/Specialization'
import Announcement from '../components/Clinic/Announcement'
import Symptom from '../components/Clinic/Symptom'


const routes = [{
    path: '/',
    component: Landing,
    children: [
        { path: '', component: UserSelection },
        {
            path: '/patient',
            component: PatientLayout,
            children: [
                { path: '', component: NearbyClinic },
                { path: 'nearby-clinic', component: NearbyClinic },
                { path: 'all-clinic', component: PatientClinicList },
                { path: 'announcements', component: PatientAnnouncements },
            ]
        },
        {
            path: '/clinic',
            component: ClinicLayout,
            beforeEnter: (to, from, next) => filterRoute(to, from, next, store),
            children: [
                { path: '', component: Authenticate },
                { path: 'signin', component: Signin, meta: { notRequiresAuth: true } },
                { path: 'register', component: Register, meta: { notRequiresAuth: true } },
                {
                    path: 'dashboard',
                    component: DashboardLayout,
                    children: [
                        /* for normal clinic users */
                        { path: '', component: ClinicInfo },
                        { path: 'services', component: ClinicServices },

                        /* for super admin users ClinicList */
                        {
                          path: 'adminClinics',
                          component: ListTemplate,
                          children: [
                            { path: '',
                              component: ClinicTabList,
                              children: [
                                { path: '', component: ClinicList },
                                { path: 'inactive', component: ClinicInactiveList }
                              ]
                            },
                            {
                              path: 'edit/email/:email/own/:own/',
                              component: Clinic,
                              props: true,
                              children: [
                                { path: 'services', component: ClinicServices }
                              ]
                            },
                            {
                              path: 'add/email/:email/own/:own',
                              component: Clinic,
                              props: true
                            },
                          ]
                        },
                        /* for super admin users specialization list */
                        {
                          path: 'adminSpecializations',
                          component: ListTemplate,
                          children: [
                            { path: '', component: SpecializationList },
                            {
                              path: 'edit/id/:id/',
                              component: Specialization,
                              props: true
                            },
                            {
                              path: 'add/',
                              component: Specialization,
                            },
                          ]
                        },

                        { path: 'adminSymptoms',
                          component: ListTemplate,
                          children: [
                            { path: '', component: SymptomList },
                            {
                              path: 'edit/id/:id/',
                              component: Symptom,
                              props: true
                            },
                            {
                              path: 'add',
                              component: Symptom,
                            },
                          ]
                        },

                      /* for super admin users announcement list */
                      { path: 'adminAnnouncements',
                        component: ListTemplate,
                        children: [
                          { path: '', component: AnnouncementList },
                          {
                            path: 'add',
                            component: Announcement
                          },
                          {
                            path: 'edit/id/:id/',
                            component: Announcement,
                            props: true
                          },
                        ]

                      },
                    ]
                }
            ]
        },
        { path: '/implicit/callback', component: Auth.handleCallback() }
    ]
}]

// Always leave this as last one
if (process.env.MODE !== 'ssr') {
    routes.push({
        path: '*',
        component: () =>
            import ('pages/Error404.vue')
    })
}

const filterRoute = (to, from, next, store) => {
    (async() => {
        if (to.meta.notRequiresAuth) {
            next();
        } else {
            next({ path: '/clinic/signin', query: { redirect: to.fullPath } });
        }
    })();
}

export default routes
