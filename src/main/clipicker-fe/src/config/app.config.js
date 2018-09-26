
//const isProd = window.location.href.indexOf('clipicker.club') >= 0;
const isProd=false;
const appConfig = () => {
  if (isProd) {
    return {
      name: 'CliPicker',
      map: {
        mapbox: {
          token: 'pk.eyJ1IjoiY2xpcGlja2VyZGV2IiwiYSI6ImNqbWc1YzBwaTBvMGozcG52OWZtaWZud2MifQ.OEsHlETM3Y9ZSKNZyvZIAg',
          style: 'mapbox://styles/clipickerdev/cjmg5kpfa938n2rpanwgcx9v2',
          publicStyle2:
            'https://api.mapbox.com/styles/v1/clipickerdev/cjmg5kpfa938n2rpanwgcx9v2.html?fresh=true&title=true&access_token=pk.eyJ1IjoiY2xpcGlja2VyZGV2IiwiYSI6ImNqbWc1YzBwaTBvMGozcG52OWZtaWZud2MifQ.OEsHlETM3Y9ZSKNZyvZIAg#9.0/37.780000/-122.424100/0',
          publicStyle:
            'https://api.mapbox.com/styles/v1/mapbox/streets-v10/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiY2xpcGlja2VyZGV2IiwiYSI6ImNqbWc1YzBwaTBvMGozcG52OWZtaWZud2MifQ.OEsHlETM3Y9ZSKNZyvZIAg'

        }
      }
    };
  } else {
    return {
      name: 'CliPicker',
      map: {
        mapbox: {
          token: 'pk.eyJ1IjoiY2xpcGlja2VyZGV2IiwiYSI6ImNqbWc1YzBwaTBvMGozcG52OWZtaWZud2MifQ.OEsHlETM3Y9ZSKNZyvZIAg',
          style: 'mapbox://styles/clipickerdev/cjmg5kpfa938n2rpanwgcx9v2',
          publicStyle2:
            'https://api.mapbox.com/styles/v1/clipickerdev/cjmg5kpfa938n2rpanwgcx9v2.html?fresh=true&title=true&access_token=pk.eyJ1IjoiY2xpcGlja2VyZGV2IiwiYSI6ImNqbWc1YzBwaTBvMGozcG52OWZtaWZud2MifQ.OEsHlETM3Y9ZSKNZyvZIAg#9.0/37.780000/-122.424100/0',
          publicStyle:
            'https://api.mapbox.com/styles/v1/mapbox/streets-v10/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiY2xpcGlja2VyZGV2IiwiYSI6ImNqbWc1YzBwaTBvMGozcG52OWZtaWZud2MifQ.OEsHlETM3Y9ZSKNZyvZIAg'

        }
      }
    };
  }

}

export default {
  isProd: isProd,
  application: appConfig(),
  httpLocation: isProd ? 'https://clipicker.club' : 'http://clipicker',
  server: isProd ? 'https://clipicker.club/api' : 'http://clipicker/api',
  logo: isProd ? 'https://clipicker.club/statics/clipicker.png' : 'http://clipicker/statics/clipicker.png',
}
