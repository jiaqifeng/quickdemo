include ../common.gmk

run: build
	mvn tomcat:run -Dmaven.tomcat.port=8098

build:
	mvn clean compile

runa: build
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=kafkaP1 -Dpinpoint.applicationName=KAFKA_PRODUCER' mvn tomcat:run -Dmaven.tomcat.port=8098

runagentbg: build stop
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=kafkaP1 -Dpinpoint.applicationName=KAFKA_PRODUCER' nohup mvn tomcat:run -Dmaven.tomcat.port=8098 &> tomcat.log & echo $$! > tomcat.pid

stop:
	- if [ -f tomcat.pid ]; then kill `cat tomcat.pid` || rm tomcat.pid; fi

curl:
	curl http://localhost:8098/producer-webapp/producer

c2:
	curl http://localhost:8098/producer-webapp/wrapproducer

once:
	mvn clean compile
	mvn exec:java -Dexec.mainClass=com.jack.maven.webapp.OnceProducer

copy:
	-rm -r pinpoint-agent
	-mkdir pinpoint-agent
	cp -r ~/work/pinpoint/agent/target/pinpoint-agent-1.5.2-SNAPSHOT/* pinpoint-agent/
	sed -i 's/profiler.collector.ip=127.0.0.1/profiler.collector.ip=172.17.42.1/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.span.port=9996/profiler.collector.span.port=29996/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.stat.port=9995/profiler.collector.stat.port=29995/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.tcp.port=9994/profiler.collector.tcp.port=29994/' pinpoint-agent/pinpoint.config

.PHONY: run runpp curl
