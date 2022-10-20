#!/bin/bash

export BUILD=1
export CLEAN=0
export NO_VOLUME=0

createVolumes() {
  docker volume create umfrage-db-data
  docker volume create umfrage-db-backup
}

showHelp() {
# `cat << EOF` This means that cat should stop reading when EOF is detected
cat << EOF
Usage: ./build.sh -b -c

-c        --clean       clean run of the container. Restart with fresh volumes
-v        --no-volumes  restart without an attached volume
-b        --no-build    only restart the container but do not build the image

EOF
# EOF is found above and hence cat command stops reading. This is equivalent to echo but much neater when printing out.
}

# $@ is all command line parameters passed to the script.
# -o is for short options like -v
# -l is for long options with double dash like --version
# the comma separates different long options
# -a is for long options with single dash like -version
options=$(getopt -l "help,no-build,clean" -o "hbc" -a -- "$@")

# set --:
# If no arguments follow this option, then the positional parameters are unset. Otherwise, the positional parameters
# are set to the arguments, even if some of them begin with a ‘-’.
eval set -- "$options"

while true
do
case $1 in
-h | --help)
    showHelp
    exit 0
    ;;
-b|--no-build)
  BUILD=0
  ;;
-c|--clean)
  CLEAN=1
  ;;
-v|--no-volume)
  NO_VOLUME=1
  ;;
esac
shift
done


if [[ $BUILD == 1 ]]; then
  docker build -t mts11.umfrage.db .
fi

container=$(docker ps | grep mts11.umfrage.db)
volumes=$(docker volume ls | grep umfrage)

# check if volumes exist if not create volumes
if [[ $volumes == "" && $NO_VOLUME == 0 ]]; then
  createVolumes
fi

# check if container is running if true terminate container
if [[ $container == "" ]]; then
  instance=$(docker ps -aqf "name=umfrage-db" )
  docker kill "$instance"
fi

if [[ $NO_VOLUMES == 1 ]];then
  docker volume rm umfrage-db-data
  docker volume rm umfrage-db-backup
  createVolumes
fi

# check if volume flag is set
if [[ $NO_VOLUME == 0 ]];then
  docker run -p 1433:1433 -d -v umfrage-db-data:/var/opt/mssql/data -v umfrage-db-backup:/var/opt/mssql/backup --name=umfrage-db mts11.umfrage.db
else
  docker run -p 1433:1433 -d --name=umfrage-db mts11.umfrage.db
fi

unset BUILD
unset CLEAN
unset NO_VOLUME