project_dir=/home/marco/apps/Development/clipicker/


cd $project_dir

git checkout production-release-tag

# clean and get the latest
git reset --hard HEAD
git clean -f
git pull
