include ../common.gmk

PORT=8099

config:
	sed -i 's/^profiler.entrypoint=$$/profiler.entrypoint=com.jack.pinpoint.echo.EntryPoint.myEntryPoint,com.jack.pinpoint.echo.ThriftServlet.doTalkService,java.lang.Runtime.exec,com.jack.pinpoint.echo.ErrorServlet.exec/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.log4j.logging.transactioninfo=false/profiler.log4j.logging.transactioninfo=true/' pinpoint-agent/pinpoint.config
	#sed -i 's/profiler.sampling.rate=1/profiler.sampling.rate=2/' pinpoint-agent/pinpoint.config
	sed -i 's/INFO/DEBUG/' pinpoint-agent/lib/log4j.xml
	sed -i 's/profiler.attackchecker.file.pattern=$$/profiler.attackchecker.file.pattern=\/etc/' pinpoint-agent/pinpoint.config

run: mvnrebuild config
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa: mvnrebuild config
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=EchoService' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

ra:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=EchoService' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

curl:
	curl http://localhost:$(PORT)/echo/hello

excep:
	curl http://localhost:$(PORT)/echo/entry

attack:
	curl http://localhost:$(PORT)/echo/attack

jetty:
	mvn -f jettypom.xml jetty:run -Djetty.port=8099

jettya:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=EchoJetty' mvn -f jettypom.xml jetty:run -Djetty.port=$(PORT)

# package a executable jar
dist:
	mvn clean package tomcat7:exec-war

# run the executable jar
execdist:
	- rm -rf .extract
	java  -jar target/echo-websvr-1.0-SNAPSHOT-war-exec.jar -httpPort 8099

execdista:
	- rm -rf .extract
	java -javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=EchoService -jar target/echo-1.0-SNAPSHOT-war-exec.jar -httpPort 8099

curl3:
	http :8099/echo/hello

.PHONY: run runpp curl

