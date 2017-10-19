include ../common.gmk

run: mvnrebuild
	mvn exec:java -Dexec.mainClass=com.jack.pinpoint.vertx.EchoServer

runa: mvnrebuild debuglog
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=SPRING_KAFKA_COMSUMER' mvn exec:java -Dexec.mainClass=com.jack.pinpoint.springkafka.consumer.Consumer -Dkafkatopic=$(TOPIC)

curl:
	curl http://localhost:8070

.PHONY: run runa curl 
