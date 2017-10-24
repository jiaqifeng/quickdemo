include ../common.gmk

PORT=8097
#for tomcat7 plugin, port is specified in pom.xml

config:
	#sed -i 's/profiler.sampling.rate=1/profiler.sampling.rate=2/' pinpoint-agent/pinpoint.config
	sed -i 's/INFO/DEBUG/' pinpoint-agent/lib/log4j.xml
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

curl:
	curl http://localhost:$(PORT)/jumper/httpclient

c2:
	curl http://localhost:$(PORT)/jumper/jdkhttp

c3:
	curl http://localhost:$(PORT)/jumper/hystrix

c4:
	curl http://localhost:$(PORT)/jumper/thrift

ca:
	curl http://localhost:$(PORT)/jumper/async

cal:
	curl http://localhost:$(PORT)/jumper/asynclong

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
