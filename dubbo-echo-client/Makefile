include ../common.gmk

PORT=8091

build:
	mvn clean compile

run:build
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa: build
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=dubbo-client-$(PORT) -Dpinpoint.applicationName=DUBBO_CLIENT' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

curl:
	curl http://localhost:$(PORT)/echo-client/dubbo


standalone:
	mvn clean compile exec:java -Dexec.mainClass=com.jack.pinpoint.dubbo.client.AppMain

