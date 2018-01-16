include ../common.gmk

PORT=8086

config:
	sed -i 's/profiler.async.classlist=$$/profiler.async.classlist=com.jack.pinpoint.addressbook.controller.MyRunnable,com.jack.pinpoint.addressbook.controller.MyCallable/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.attackchecker.sql.pattern=$$/profiler.attackchecker.sql.pattern=jack,cool/'  pinpoint-agent/pinpoint.config
	sed -i 's/INFO/DEBUG/' pinpoint-agent/lib/log4j.xml

run: mvnrebuild
	mvn spring-boot:run

runa: mvnrebuild 
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=RABBIT_CONSUMER_SB' mvn spring-boot:run

curl:
	http :8085/api/v1/address?id=1

add:
	http ":8085/api/v1/address/add?name=cool&address=beij"

del:
	http :8085/api/v1/address/del?name=cool

query:
	http :8085/api/v1/address/query?name=cool

cr:
	curl http://localhost:8085/runnable?id=1
ca:
	curl http://localhost:8085/callable?id=1
pack:
	mvn package
execa:
	java -javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=agent2 -Dpinpoint.applicationName=SPRING_KAFKA_PRODUCER -jar target/addressbook-1.0-SNAPSHOT.jar

# http ':8085/api/v1/address/query?name=/*'
# http ':8085/api/v1/address/query?name=*/'
# http ':8085/api/v1/address/query?name=1=1'
# http ':8085/api/v1/address/query?name=1%20=%201'
# http ':8085/api/v1/address/query?name=sleep(0)'
# http ':8085/api/v1/address/query?name=select(0)'

