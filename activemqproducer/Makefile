include ../common.gmk

run:
	mvn tomcat:run -Dmaven.tomcat.port=8099
runa:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT-VERSION).jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=ActivemqProducer' mvn spring-boot:run -Dmaven.tomcat.port=8099

curl:
	curl -X POST http://localhost:8080/

http:
	http POST :8080/

.PHONY: run runpp curl

