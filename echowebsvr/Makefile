run:
	mvn tomcat:run -Dmaven.tomcat.port=8099
runa:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=EchoService' mvn tomcat:run -Dmaven.tomcat.port=8099

curl:
	curl http://localhost:8099/echo-websvr/hello

.PHONY: run runpp curl
