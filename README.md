# quickdemo
Some quick demo app for test APM.

# howto start simple jumper & echo
**only tested under ubuntu**

git clone pinpoint and quickdemo under same directory
**git co 1.6.0-RC1**
Currently, only tested with 1.6.0-RC1 of pinpoint

build pinpoiont, and start pinpoint quick start

* in one console run:
cd quickdemo/echowebsvr
make copy

* in one console run:
cd quickdemo/jumperwebapp
make ra

* in one console run:
cd quickdemo/jumperwebapp
make curl

then you should see topology like below in web

![topology](doc/jumper-echo-map.png)

# nodejs helloworld
see myexpress

# dubbo echo example

1. start zookeeper first
2. run dubbo-echo-server
3. run dubbo-echo-client

