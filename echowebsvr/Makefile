include ../common.gmk

run:
	mvn tomcat:run -Dmaven.tomcat.port=8099
runa:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT-VERSION).jar -Dpinpoint.agentId=echo1 -Dpinpoint.applicationName=EchoService' mvn tomcat:run -Dmaven.tomcat.port=8099

long:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT-VERSION).jar -Dpinpoint.agentId=echo-long-1234567890123456789012345678901234567890 -Dpinpoint.applicationName=Echo-long-12345678901234567890abcdefghijklmnopqrstuvwxyzabcdefgh' mvn tomcat:run -Dmaven.tomcat.port=8099

curl:
	curl http://localhost:8099/echo-websvr/hello

# package a executable jar
dist:
	mvn clean package tomcat7:exec-war

# run the executable jar
execdist:
	- rm -rf .extract
	java  -jar target/echo-websvr-1.0-SNAPSHOT-war-exec.jar -httpPort 8099

.PHONY: run runpp curl

