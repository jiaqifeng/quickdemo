include ../common.gmk

PORT=8085

run: mvnrebuild
	mvn spring-boot:run

runa: mvnrebuild
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=AddressBook' mvn spring-boot:run

curl:
	http :8085/api/v1/address?id=1

pack:
	mvn package
execa:
	java -javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT-VERSION).jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=SPRING_KAFKA_PRODUCER -jar target/producer-1.0-SNAPSHOT.jar
