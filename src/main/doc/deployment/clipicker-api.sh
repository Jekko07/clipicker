~/apps/scripts/clipicker/clipicker-git.sh

port=2018
project_dir=/home/marco/apps/Development/clipicker

appProfile=prod
dbPass=PK7p!bYq5#2au#!!UN

setupDbFile=$project_dir/src/main/resources/db/changelog/001/setupDB.change.xml
applicationFile=$project_dir/src/main/resources/application.yml

cd $project_dir

# replace production contents:
sed -i 's@^spring.profiles.active:.*$@spring.profiles.active: '$appProfile'@g' $applicationFile
sed -i "s/123456/$dbPass/g" $setupDbFile

# build the api
mvn clean -DskipTests=true package

# cp to running apps folder and run the api
mkdir -p ~/apps/running
\cp $project_dir/target/clipicker-0.0.1.jar ~/apps/running/
kill -9 `netstat -pnutl | grep $port | awk '{print $7}' | awk -F'/java' '{print $1}'`
nohup java -jar ~/apps/running/clipicker-0.0.1.jar > ~/apps/logs/clipicker.log &