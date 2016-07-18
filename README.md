# quickdemo
Some quick demo app for test APM.
# start simple jumper & echo

put pinpoint and quickdemo sourcecode dirs under same dir

build pinpoiont, and start pinpoint quick start

cd quickdemo/echowebsvr

make copy

make runa

in another bash shell run 'make curl' to access echo service, then there should be 1 user access echoservice on pinpoint web UI.
