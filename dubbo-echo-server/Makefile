include ../common.gmk

build:
	mvn clean compile

run: build
	mvn exec:java -Dexec.mainClass=com.jack.pinpoint.dubbo.server.DubboEchoServer

runa: build
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=dubboserver1 -Dpinpoint.applicationName=DUBBO_SERVER' mvn exec:java -Dexec.mainClass=com.jack.pinpoint.dubbo.server.DubboEchoServer
