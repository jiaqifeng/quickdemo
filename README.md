# pinpoint demo apps
Provide some demo app for test pinpoint.

# common usage
Place the source code of pinpoint and this project under same directory.
In each demo app dir:

**make copy** will copy the agent files from pinpoint agent dir after build pinpoint successfully. The pinpoint version will be extract from pinpoint quickstart config automatically.

**make run** will run app without agent.

**make runa** will run app with agent.

**make curl** will access the web url for demo web app


# tested demo app groups
**only tested under ubuntu, with 1.6.0-RC1 and 1.6.1-SNAPSHOT**
## jumper and echo
jumper calls echo in 4 ways, using HttpClient, JDKHttp API, Hystrix, And Thrift.

![topology](doc/jumper-echo-map.png)

## dubbo client and server

tested.

![topology](doc/dubbo-map.png)

## spring kafka producer, consumer and echo

tested.

![topology](doc/spring-kafka-map.png)

## echo to test user plugin
make curl2

## activemq `TODO`

Not tested after last refactor.

## pinpointutils `TODO`
some util formater to be used under hbase shell for pinpoint. Not include now.

# nodejs helloworld
TODO
see myexpress, using nodejs agent from peaksnail

