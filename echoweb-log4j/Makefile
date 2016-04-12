run: build
	mvn tomcat:run -Dmaven.tomcat.port=8099

runa:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=EchoService' mvn tomcat:run -Dmaven.tomcat.port=8099

copy:
	-mkdir oneapm
	-cp /work/javaagent3/target/oneapm.jar oneapm/
	-cp /work/javaagent3/agent-bridge/target/agent-bridge.jar oneapm/
	-cp /work/javaagent3/oneapm-api/target/oneapm-api.jar oneapm/
	-cp /work/javaagent3/log4j-1.0/target/log4j-1.0.jar oneapm/extensions/
	#-cp /work/javaagent3/log4j-1.0/target/log4j-1.0.jar src/main/resources/


# lets try run it under oneapm
runj:
	-rm oneapm/logs/oneapm.log
	MAVEN_OPTS='-Duser.country=US -Doneapm.config.file=src/main/resources/oneapm.properties -cp ".;../target/;oneapm-api.jar;oneapm.jar;agent-bridge.jar" -javaagent:oneapm/oneapm.jar' mvn tomcat:run -Dmaven.tomcat.port=8099

build:
	mvn clean compile
curl:
	curl http://localhost:8099/echo-websvr/echo

.PHONY: run runpp curl
