include ../common.gmk

sd:
	mvn exec:java -Dexec.mainClass=com.jack.exapmle.rabbitmq.Sender

run: mvnrebuild
	mvn exec:java -Dexec.mainClass=com.jack.exapmle.rabbitmq.Receiver

runa: mvnrebuild debuglog
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=rabbit-consumer1 -Dpinpoint.applicationName=RABBITMQ_CONSUMER' mvn exec:java -Dexec.mainClass=com.jack.exapmle.rabbitmq.Receiver

runt: debuglog
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=rabbit-consumer1 -Dpinpoint.applicationName=RABBITMQ_TOPIC_CONSUMER' mvn exec:java -Dexec.mainClass=com.jack.exapmle.rabbitmq.TopicReceiver

show:
	@echo "MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=rabbit-consumer1 -Dpinpoint.applicationName=RABBITMQ_TOPIC_CONSUMER' mvn exec:java -Dexec.mainClass=com.jack.exapmle.rabbitmq.TopicReceiver"
