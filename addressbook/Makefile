include ../common.gmk

PORT=8085

config:
	sed -i 's/profiler.async.classlist=$$/profiler.async.classlist=com.jack.pinpoint.addressbook.controller.MyRunnable,com.jack.pinpoint.addressbook.controller.MyCallable/' pinpoint-agent/pinpoint.config
	sed -i 's/INFO/DEBUG/' pinpoint-agent/lib/log4j.xml

run: mvnrebuild config
	mvn spring-boot:run

runa: mvnrebuild config
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=AddressBook' mvn spring-boot:run

curl:
	http :8085/api/v1/address?id=1

cr:
	curl http://localhost:8085/runnable?id=1
ca:
	curl http://localhost:8085/callable?id=1
pack:
	mvn package
execa:
	java -javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT-VERSION).jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=SPRING_KAFKA_PRODUCER -jar target/producer-1.0-SNAPSHOT.jar
