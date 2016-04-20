# run background

rb: stop
	nohup mvn tomcat:run -Dmaven.tomcat.port=8098 &> tomcat.log & echo $$! > tomcat.pid

r:
	mvn tomcat:run -Dmaven.tomcat.port=8097

ra: stop
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=jumper1 -Dpinpoint.applicationName=Jumper' mvn tomcat:run -Dmaven.tomcat.port=8098

rab: stop
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=jumper1 -Dpinpoint.applicationName=Jumper' nohup mvn tomcat:run -Dmaven.tomcat.port=8098 &> tomcat.log & echo $$! > tomcat.pid

curl:
	curl http://localhost:8098/jumper-webapp/jumper

c2:
	curl http://localhost:8098/jumper-webapp/jdkhttp

stop:
	- if [ -f tomcat.pid ]; then kill `cat tomcat.pid` || rm tomcat.pid; fi
#	- if [ -f tomcat.log ]; then rm tomcat.log; fi

.PHONY: run runpp curl

