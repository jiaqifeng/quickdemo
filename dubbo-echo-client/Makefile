include ../common.gmk

build:
	mvn clean compile

run:build
	mvn tomcat:run -Dmaven.tomcat.port=8091

runa: build
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=dubboclient1 -Dpinpoint.applicationName=DUBBO_CLIENT' mvn tomcat:run -Dmaven.tomcat.port=8091

curl:
	curl http://localhost:8091/echo-client/dubbo


standalone:
	mvn clean compile exec:java -Dexec.mainClass=com.jack.pinpoint.dubbo.client.AppMain

