# run background
rb:
	nohup mvn tomcat:run -Dmaven.tomcat.port=8098 &> tomcat.log & echo $$! > tomcat.pid

r:
	mvn tomcat:run -Dmaven.tomcat.port=8098

ra:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=Jumper' mvn tomcat:run -Dmaven.tomcat.port=8098

rab:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=Jumper' nohup mvn tomcat:run -Dmaven.tomcat.port=8098 &> tomcat.log & echo $$! > tomcat.pid

curl:
	curl http://localhost:8098/jumper-webapp/jumper

c2:
	curl http://localhost:8098/jumper-webapp/jdkhttp

stop:
	kill `cat tomcat.pid`
	-rm tomcat.pid

.PHONY: run runpp curl

