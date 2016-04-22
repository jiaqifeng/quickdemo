# run background

rb: stop
	nohup mvn tomcat:run -Dmaven.tomcat.port=8098 &> tomcat.log & echo $$! > tomcat.pid

r:
	mvn tomcat:run -Dmaven.tomcat.port=8097

ra: stop
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.6.0-SNAPSHOT.jar -Dpinpoint.agentId=jumper1 -Dpinpoint.applicationName=Jumper' mvn tomcat:run -Dmaven.tomcat.port=8098

rab: stop
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.6.0-SNAPSHOT.jar -Dpinpoint.agentId=jumper1 -Dpinpoint.applicationName=Jumper' nohup mvn tomcat:run -Dmaven.tomcat.port=8098 &> tomcat.log & echo $$! > tomcat.pid

curl:
	curl http://localhost:8098/jumper-webapp/jumper

c2:
	curl http://localhost:8098/jumper-webapp/jdkhttp

stop:
	- if [ -f tomcat.pid ]; then kill `cat tomcat.pid` || rm tomcat.pid; fi
#	- if [ -f tomcat.log ]; then rm tomcat.log; fi

copy:
	-rm -r pinpoint-agent
	-mkdir pinpoint-agent
	cp -r ~/work/pinpoint/agent/target/pinpoint-agent-1.6.0-SNAPSHOT/* pinpoint-agent/
	sed -i 's/profiler.collector.ip=127.0.0.1/profiler.collector.ip=172.17.42.1/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.span.port=9996/profiler.collector.span.port=29996/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.stat.port=9995/profiler.collector.stat.port=29995/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.tcp.port=9994/profiler.collector.tcp.port=29994/' pinpoint-agent/pinpoint.config

.PHONY: run runpp curl

