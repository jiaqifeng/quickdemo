include ../common.gmk

PORT=8097

run: mvnrebuild
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa: stop
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=Jumper' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

curl:
	curl http://localhost:$(PORT)/jumper/httpclient

c2:
	curl http://localhost:$(PORT)/jumper/jdkhttp

c3:
	curl http://localhost:$(PORT)/jumper/hystrix

c4:
	curl http://localhost:$(PORT)/jumper/thrift

thrift:
	cd src/main; thrift -r -gen java thrift/my.thrift ; cp ./gen-java/com/jack/maven/webapp/*.java ./java/com/jack/pinpoint/jumper/; cd ../..

.PHONY: run runpp curl

#######################################
# run background, not used any more

stop:
	- if [ -f tomcat.pid ]; then kill `cat tomcat.pid` || rm tomcat.pid; fi
#	- if [ -f tomcat.log ]; then rm tomcat.log; fi

rb: stop
	nohup mvn tomcat:run -Dmaven.tomcat.port=$(PORT) &> tomcat.log & echo $$! > tomcat.pid

rab: stop
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=jumper1 -Dpinpoint.applicationName=Jumper' nohup mvn tomcat:run -Dmaven.tomcat.port=8097 &> tomcat.log & echo $$! > tomcat.pid
