include ../common.gmk

PORT=8097
#for tomcat7 plugin, port is specified in pom.xml

config:
	sed -i 's/profiler.async.runnablelist=$$/profiler.async.runnablelist=com.jack.pinpoint.jumper.AsyncServlet$$MyRunnable/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.logback.logging.transactioninfo=false/profiler.logback.logging.transactioninfo=true/' pinpoint-agent/pinpoint.config

run: mvnrebuild
	mvn tomcat7:run

runa: mvnrebuild config
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=Jumper' mvn tomcat7:run

run6: mvnrebuild
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa6: mvnrebuild config
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=Jumper' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runhttp4: config
	mvn -f httpclient4.pom.xml clean compile 
	mvn -f httpclient4.pom.xml tomcat7:run

runhttp4a: config
	mvn clean
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=Jumper' mvn -f httpclient4.pom.xml tomcat7:run

curl:
	curl http://localhost:$(PORT)/jumper/httpclient

jdk:
	curl http://localhost:$(PORT)/jumper/jdkhttp

hys:
	curl http://localhost:$(PORT)/jumper/hystrix

thr:
	curl http://localhost:$(PORT)/jumper/thrift

asy:
	curl http://localhost:$(PORT)/jumper/async

asyl:
	curl http://localhost:$(PORT)/jumper/asynclong

ning:
	curl http://localhost:$(PORT)/jumper/ning

oke:
	curl http://localhost:$(PORT)/jumper/oke #okhttp execute

okq:
	curl http://localhost:$(PORT)/jumper/okq 

ggs:
	curl http://localhost:$(PORT)/jumper/ggs

gga:
	curl http://localhost:$(PORT)/jumper/gga

hc4a:
	curl http://localhost:$(PORT)/jumper/hc4a

thrift:
	cd src/main; thrift -r -gen java thrift/my.thrift ; cp ./gen-java/com/jack/maven/webapp/*.java ./java/com/jack/pinpoint/jumper/; cd ../..

jetty:
	mvn -f jettypom.xml jetty:run -Djetty.port=$(PORT)

jettya:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=JumperJetty' mvn -f jettypom.xml jetty:run -Djetty.port=$(PORT)

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
