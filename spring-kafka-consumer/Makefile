include ../common.gmk

run: build
	mvn exec:java -Dexec.mainClass=com.jack.pinpoint.springkafka.consumer.Consumer -Dkafkatopic=cool
#-Dkafkatopic=cool -Dkafkahost=1.1.1.1 -Dechohost=www.baidu.com

build:
	mvn clean compile

runa: build
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=SPRING_KAFKA_COMSUMER' mvn exec:java -Dexec.mainClass=com.jack.pinpoint.springkafka.consumer.Consumer -Dkafkatopic=$(TOPIC)

runagentbg: build stop
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=SPRING_KAFKA_COMSUMER' nohup mvn tomcat:run -Dmaven.tomcat.port=8098 &> tomcat.log & echo $$! > tomcat.pid

stop:
	- if [ -f tomcat.pid ]; then kill `cat tomcat.pid` || rm tomcat.pid; fi

curl:
	curl http://localhost:8098/producer/producer?msg=hello

pack:
	mvn package

exec:pack
	java -jar target/consumer-1.0-SNAPSHOT-executable.jar

execa:pack
	java -javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT-VERSION).jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=SPRING_KAFKA_COMSUMER -jar target/consumer-1.0-SNAPSHOT-executable.jar

.PHONY: run runpp curl pack exec execa
