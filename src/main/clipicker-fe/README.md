clipicker

> WIP
use okta signin-widget
enable cors site ->  https://developer.okta.com
   

docs:
* https://developer.okta.com/docs/api/getting_started/enabling_cors#granting-cross-origin-access-to-websites
* https://developer.okta.com/code/javascript/okta_sign-in_widget
* https://github.com/okta/okta-signin-widget#new-oktasigninconfig
* https://developer.okta.com/quickstart/#/vue/java/spring


```bash
# nginx server settings:
server {
    listen       80;
    server_name  clipicker;

    location / {
      proxy_pass http://localhost:1818;
    }  
  
}


server {
    listen       80;
    server_name  clipicker.api;
  
    location / {
      proxy_pass http://localhost:2018/;
    }
     
}

facebook developer account:
https://developers.facebook.com/apps/296679917802638/settings/basic/:
facebook 

appid:
296679917802638

appsecret:
89a62f95770f4127c4a82621b2a7198e



```

