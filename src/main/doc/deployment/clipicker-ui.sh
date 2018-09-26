~/apps/scripts/clipicker/clipicker-git.sh

port=1818
project_dir=/home/marco/apps/Development/clipicker
deploy_dir=/home/marco/apps/sites/clipicker/vue-app

appConfigFile=$project_dir/src/main/clipicker-fe/src/config/app.config.js

# replace production contents
sed -i "s/isProd=false/isProd=true/g" $appConfigFile

cd $project_dir

# cd in webapp for vue js app
cd src/main/clipicker-fe && yarn build

# compress
cd dist/spa-mat && zip vue-app.zip -r *

# extract to vue-folder
unzip -o vue-app.zip -d $deploy_dir

kill -9 `netstat -pnutl | grep $port | awk '{print $7}' | awk -F'/node' '{print $1}'`
nohup quasar serve --history --p 1818 $deploy_dir  > ~/apps/logs/clipicker-ui.log &

