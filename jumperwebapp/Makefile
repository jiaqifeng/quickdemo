include ../common.gmk

# run background

rb: stop
	nohup mvn tomcat:run -Dmaven.tomcat.port=8097 &> tomcat.log & echo $$! > tomcat.pid

build:
	mvn clean compile

run: build
	mvn tomcat:run -Dmaven.tomcat.port=8097

runa: stop
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=jumper1 -Dpinpoint.applicationName=Jumper' mvn tomcat:run -Dmaven.tomcat.port=8097

rab: stop
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=jumper1 -Dpinpoint.applicationName=Jumper' nohup mvn tomcat:run -Dmaven.tomcat.port=8097 &> tomcat.log & echo $$! > tomcat.pid

curl:
	curl http://localhost:8097/jumper-webapp/jumper

c2:
	curl http://localhost:8097/jumper-webapp/jdkhttp

c3:
	curl http://localhost:8097/jumper-webapp/hystrixjumper

c4:
	curl http://localhost:8097/jumper-webapp/thrift

stop:
	- if [ -f tomcat.pid ]; then kill `cat tomcat.pid` || rm tomcat.pid; fi
#	- if [ -f tomcat.log ]; then rm tomcat.log; fi

thrift:
	cd src/main; thrift -r -gen java thrift/my.thrift ; cp ./gen-java/com/jack/maven/webapp/*.java ./java/com/jack/maven/webapp/; cd ../..

.PHONY: run runpp curl

