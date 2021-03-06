include ../common.gmk

PORT=8097
#for tomcat7 plugin, port is specified in pom.xml

config:
	sed -i 's/profiler.async.classlist=$$/profiler.async.classlist=com.jack.pinpoint.jumper.AsyncServlet$$MyRunnable/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.logback.logging.transactioninfo=false/profiler.logback.logging.transactioninfo=true/' pinpoint-agent/pinpoint.config

run: update-webxml-tomcat mvnrebuild
	mvn tomcat7:run

runa: update-webxml-tomcat mvnrebuild config
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=jumper-$(PORT) -Dpinpoint.applicationName=Jumper' mvn tomcat7:run

run6: update-webxml-tomcat mvnrebuild
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa6: update-webxml-tomcat mvnrebuild config
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=jumper-$(PORT) -Dpinpoint.applicationName=Jumper' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runhttp4: update-webxml-tomcat config
	mvn -f httpclient4.pom.xml clean compile 
	mvn -f httpclient4.pom.xml tomcat7:run

runhttp4a: update-webxml-tomcat config
	mvn -f httpclient4.pom.xml clean compile
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=jumper-$(PORT) -Dpinpoint.applicationName=Jumper' mvn -f httpclient4.pom.xml tomcat7:run

curl:
	curl http://localhost:$(PORT)/jumper/hc3

hc4a:
	curl http://localhost:$(PORT)/jumper/hc4a

hc4s:
	curl http://localhost:$(PORT)/jumper/hc4s

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

oks:
	curl http://localhost:$(PORT)/jumper/oks

oka:
	curl http://localhost:$(PORT)/jumper/oka

ggs:
	curl http://localhost:$(PORT)/jumper/ggs

gga:
	curl http://localhost:$(PORT)/jumper/gga

thrift:
	cd src/main; thrift -r -gen java thrift/my.thrift ; cp ./gen-java/com/jack/maven/webapp/*.java ./java/com/jack/pinpoint/jumper/; cd ../..

update-webxml-tomcat:
	if ! diff --brief src/main/webapp/WEB-INF/web.xml src/main/webapp/WEB-INF/web.xml.tomcat; then cp src/main/webapp/WEB-INF/web.xml.tomcat src/main/webapp/WEB-INF/web.xml; fi

update-webxml-jetty:
	if ! diff --brief src/main/webapp/WEB-INF/web.xml src/main/webapp/WEB-INF/web.xml.jetty; then cp src/main/webapp/WEB-INF/web.xml.jetty src/main/webapp/WEB-INF/web.xml; fi

jetty: update-webxml-jetty
	cp src/main/webapp/WEB-INF/web.xml.jetty src/main/webapp/WEB-INF/web.xml
	mvn -f jettypom.xml clean compile
	mvn -f jettypom.xml jetty:run -Djetty.port=$(PORT)

jettya: update-webxml-jetty
	mvn -f jettypom.xml clean compile
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=jumper-$(PORT) -Dpinpoint.applicationName=JumperJetty' mvn -f jettypom.xml jetty:run -Djetty.port=$(PORT)

.PHONY: run runpp curl
