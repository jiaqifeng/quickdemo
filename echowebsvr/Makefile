run:
	mvn tomcat:run -Dmaven.tomcat.port=8099
runa:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.6.0-SNAPSHOT.jar -Dpinpoint.agentId=echo1 -Dpinpoint.applicationName=EchoService' mvn tomcat:run -Dmaven.tomcat.port=8099

curl:
	curl http://localhost:8099/echo-websvr/hello

.PHONY: run runpp curl

copy:
	-rm -r pinpoint-agent
	-mkdir pinpoint-agent
	cp -r ~/work/pinpoint/agent/target/pinpoint-agent-1.6.0-SNAPSHOT/* pinpoint-agent/
	sed -i 's/profiler.collector.ip=127.0.0.1/profiler.collector.ip=172.17.42.1/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.span.port=9996/profiler.collector.span.port=29996/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.stat.port=9995/profiler.collector.stat.port=29995/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.tcp.port=9994/profiler.collector.tcp.port=29994/' pinpoint-agent/pinpoint.config
