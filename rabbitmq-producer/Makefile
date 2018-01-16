include ../common.gmk

PORT=8098

run: mvnrebuild
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa: mvnrebuild debuglog
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=rabbit-producer-$(PORT) -Dpinpoint.applicationName=RABBITMQ_SENDER' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

# support parameter like :8098/rabbitmq-producer/rabbitmq?msg=cool&exchange=jack
curl:
	curl http://localhost:$(PORT)/rabbitmq-producer/rabbitmq

consumer:
	curl http://localhost:$(PORT)/rabbitmq-producer/start

# no exchange should be specified, for it sould match the consumer side
curltopic:
	curl http://localhost:$(PORT)/rabbitmq-producer/topic?msg=cool

.PHONY: run runpp curl
