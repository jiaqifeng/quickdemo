include ../common.gmk

PORT=8098

config:
	sed -i 's/INFO/DEBUG/' pinpoint-agent/lib/log4j.xml

run: mvnrebuild
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa: mvnrebuild config
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=RABBITMQ_SENDER' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

curl:
	curl http://localhost:$(PORT)/rabbitmq-webapp/rabbitmq

.PHONY: run runpp curl
