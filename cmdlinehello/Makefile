include ../common.gmk

run: mvnrebuild
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa: mvnrebuild
	java '-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=EchoService' -cp target/classes/ com.jack.pinpoint.cmdline.hello.Hello

.PHONY: run runpp curl

