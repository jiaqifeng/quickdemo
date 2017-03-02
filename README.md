# quickdemo
Some quick demo app for test APM.

# howto start simple jumper & echo
**only tested under ubuntu**

git clone pinpoint and quickdemo under same directory

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
