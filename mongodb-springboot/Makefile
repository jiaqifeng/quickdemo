include ../common.gmk

PORT=8091
# add server.port=8091 to application.properties

run: mvnrebuild
	mvn spring-boot:run -Dmaven.tomcat.port=$(PORT)

runa: mvnrebuild
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=SB-MongoClient' mvn spring-boot:run -Dmaven.tomcat.port=$(PORT)

curl:
	curl http://localhost:$(PORT)/query

c1:
	curl -X POST http://localhost:$(PORT)/insertone

http:
	http POST :$(PORT)/

.PHONY: run runpp curl

