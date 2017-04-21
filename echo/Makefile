include ../common.gmk

PORT=8099

run: mvnrebuild
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa: mvnrebuild
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=EchoService' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

curl:
	curl http://localhost:$(PORT)/echo/hello

jetty:
	mvn -f jettypom.xml jetty:run -Djetty.port=8099

jettya:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=EchoJetty' mvn -f jettypom.xml jetty:run -Djetty.port=$(PORT)

# package a executable jar
dist:
	mvn clean package tomcat7:exec-war

# run the executable jar
execdist:
	- rm -rf .extract
	java  -jar target/echo-websvr-1.0-SNAPSHOT-war-exec.jar -httpPort 8099

.PHONY: run runpp curl

