include ../common.gmk

PORT=8098

run: mvnrebuild
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa: mvnrebuild debuglog
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=SPRING_KAFKA_PRODUCER' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

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

# get help
# java -jar producer-1.0-SNAPSHOT-war-exec.jar -help 
.PHONY: run runpp curl
