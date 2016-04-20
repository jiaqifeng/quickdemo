
# below is not checked.
# for each in a termial, run as:
# kafkaproducerwebapp: mk copy runagent
# kafka-java-examples: mk copy run1a
# echowebsvr: mk runa
kafka-run:
	$(MAKE) -C kafka-java-examples runagentbg
	$(MAKE) -C kafkaproducerwebapp runagentbg

kafka-stop:
	$(MAKE) -C kafka-java-examples stop
	$(MAKE) -C kafkaproducerwebapp stop

kafka-curl:
	$(MAKE) -C kafkaproducerwebapp c2

hystrix-run:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=jumper1 -Dpinpoint.applicationName=Jumper' nohup mvn -f jumperwebapp/pom.xml tomcat:run -Dmaven.tomcat.port=8098 &> log/jumper.log & echo $$! > log/jumper.pid
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=echo1 -Dpinpoint.applicationName=Echo' nohup mvn -f echowebsvr/pom.xml tomcat:run -Dmaven.tomcat.port=8099 &> log/echosvr.log & echo $$! > log/echosvr.pid

hystrix-stop:
	- if [ -f log/jumper.pid ]; then kill `cat log/jumper.pid` || rm log/jumper.pid; fi
	- if [ -f log/echosvr.pid ]; then kill `cat log/echosvr.pid` || rm log/echosvr.pid; fi

hystrix-check:
	- netstat -anp 2> /dev/null | grep 8098
	- netstat -anp 2> /dev/null | grep 8099

hystrix-test:
	curl http://localhost:8098/jumper-webapp/jumper
