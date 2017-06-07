include ../common.gmk

PORT=8085


run: mvnrebuild
	mvn spring-boot:run

runa: mvnrebuild
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=AddressBook' mvn spring-boot:run -Dmaven.tomcat.port=$(PORT)

curl:
	http :8085/api/v1/address?id=1
