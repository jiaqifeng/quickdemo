include ../common.gmk

run: mvnrebuild
	mvn exec:java -Dexec.mainClass=com.jack.pinpoint.springkafka.consumer.Consumer -Dkafkatopic=cool

#-Dkafkatopic=cool -Dkafkahost=1.1.1.1 -Dechohost=www.baidu.com

runa: mvnrebuild debuglog
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=agent-consumer -Dpinpoint.applicationName=SPRING_KAFKA_COMSUMER' mvn exec:java -Dexec.mainClass=com.jack.pinpoint.springkafka.consumer.Consumer -Dkafkatopic=$(TOPIC)

curl:
	curl http://localhost:8098/producer/producer?msg=hello

pack:
	mvn package

exec:pack
	java -jar target/consumer-1.0-SNAPSHOT-executable.jar

execa:pack
	java -javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT-VERSION).jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=SPRING_KAFKA_COMSUMER -jar target/consumer-1.0-SNAPSHOT-executable.jar

.PHONY: run runpp curl pack exec execa
