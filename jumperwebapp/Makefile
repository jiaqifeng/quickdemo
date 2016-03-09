run:
	mvn tomcat:run -Dmaven.tomcat.port=8098
runa:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=Jumper' mvn tomcat:run -Dmaven.tomcat.port=8098

curl:
	curl http://localhost:8098/jumper-webapp/jumper

.PHONY: run runpp curl
