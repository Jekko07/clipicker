
const okta = () => {
  if (window.location.href.indexOf('clipicker.club') >= 0) {
    return {
      clientId: '0oag6zo1jsChhPVgT0h7',
      issuer: 'https://dev-553248.oktapreview.com/oauth2/default',
      redirectUri: 'https://clipicker.club/implicit/callback',
      scope: 'openid profile email',
      idps: {
        google: '0oag72a5dhf0lxmAv0h7',
        facebook: '0oag8ndum70ZGjfea0h7'
      }
    };
  } else {
    return {
      clientId: '0oag10n6natLlZEGu0h7',
      issuer: 'https://dev-180609.oktapreview.com/oauth2/default',
      redirectUri: 'http://clipicker/implicit/callback',
      scope: 'openid profile email',
      idps: {
        google: '0oag0zrxkfIDl7ltW0h7',
        facebook: '0oag17bg7rf1ufteX0h7'
      }
    };
  }
}

export default {
  oidc: okta(),
  resourceServer: {
    
  },
};
