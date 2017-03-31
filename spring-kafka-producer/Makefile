include ../common.gmk

run: build
	mvn tomcat:run -Dmaven.tomcat.port=8098

build:
	mvn clean compile

runa: build
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=SPRING_KAFKA_PRODUCER' mvn tomcat:run -Dmaven.tomcat.port=8098

runagentbg: build stop
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=SPRING_KAFKA_PRODUCER' nohup mvn tomcat:run -Dmaven.tomcat.port=8098 &> tomcat.log & echo $$! > tomcat.pid

stop:
	- if [ -f tomcat.pid ]; then kill `cat tomcat.pid` || rm tomcat.pid; fi

curl:
	curl http://localhost:8098/producer/producer?msg=hello
#	curl 'http://localhost:8098/producer/producer?msg=fuck&topic=someday&host=www.baidu.com'

# package a executable jar
dist:
	mvn clean package tomcat7:exec-war

# run the executable jar
execdist:
	- rm -rf .extract
	java  -jar target/producer-1.0-SNAPSHOT-war-exec.jar -httpPort 8098

.PHONY: run runpp curl
