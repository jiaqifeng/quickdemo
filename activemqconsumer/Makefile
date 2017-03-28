include ../common.gmk

sd:
	mvn exec:java -Dexec.mainClass=com.jack.exapmle.rabbitmq.Sender
run:
	mvn exec:java -Dexec.mainClass=com.jack.pinpoint.activemq.consumer.Receiver

runa:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=ActivemqConsumer' mvn exec:java -Dexec.mainClass=com.jack.pinpoint.activemq.consumer.Receiver
