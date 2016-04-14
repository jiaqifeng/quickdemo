sd:
	mvn exec:java -Dexec.mainClass=com.jack.exapmle.rabbitmq.Sender
rc:
	mvn exec:java -Dexec.mainClass=com.jack.exapmle.rabbitmq.Receiver

runa:
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=agent1 -Dpinpoint.applicationName=RABBITMQ_CONSUMER' mvn exec:java -Dexec.mainClass=com.jack.exapmle.rabbitmq.Receiver
