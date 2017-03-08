include ../common.gmk

run: build
	mvn tomcat:run -Dmaven.tomcat.port=8098

build:
	mvn clean compile

runagent: build
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=kafkaP1 -Dpinpoint.applicationName=KAFKA_PRODUCER' mvn tomcat:run -Dmaven.tomcat.port=8098

runagentbg: build stop
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=kafkaP1 -Dpinpoint.applicationName=KAFKA_PRODUCER' nohup mvn tomcat:run -Dmaven.tomcat.port=8098 &> tomcat.log & echo $$! > tomcat.pid

stop:
	- if [ -f tomcat.pid ]; then kill `cat tomcat.pid` || rm tomcat.pid; fi

curl:
	curl http://localhost:8098/producer/producer?msg=hello

.PHONY: run runpp curl
