#!/usr/bin/env bash


showHelp() {
# `cat << EOF` This means that cat should stop reading when EOF is detected
cat << EOF
Init app taking advantage of the app-compose file.

-h, -help,      --help                      Display help

-c, --clean                                 remove old volumes and init with fresh data. Should only be used in development.

-r, --restart                               restart the application
EOF
# EOF is found above and hence cat command stops reading. This is equivalent to echo but much neater when printing out.
}
echo "init mts11.lf7.umfrage"
# $@ is all command line parameters passed to the script.
# -o is for short options like -v
# -l is for long options with double dash like --version
# the comma separates different long options
# -a is for long options with single dash like -version

options=$(getopt -l "help,clean,restart" -o "hcr" -a -- "$@")

removeVolumes() {
  docker volume rm umfrage-db-backup
  docker volume rm umfrage-db-data
  docker volume rm umfrage-db-log
  docker volume rm umfrage-db-secrets
}
eval set -- "$options"
while true
do
case $1 in
-h | --help)
    showHelp
    exit 0
    ;;
-c | --clean)
    removeVolumes
    docker-compose -f ./app-compose.yml up
    exit 0
    ;;

-r | --restart)
    docker-compose -f ./app-compose.yml down
    docker-compose -f ./app-compose.yml up
    exit 0
    ;;
--)
  docker-compose -f ./app-compose.yml up
  exit 0
  ;;
esac
shift
done