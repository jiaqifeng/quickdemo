runa: build
	- rm pinpoint-agent/log/*
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=kafkaC1 -Dpinpoint.applicationName=KAFKA_CONSUMER' mvn exec:java -Dexec.mainClass=com.jack.kafka.examples.PinpointConsumer

run1: build
	mvn exec:java -Dexec.mainClass=com.jack.kafka.examples.MessageDispatcher

run:
	mvn clean install
	mvn exec:java -Dexec.mainClass=com.jack.kafka.examples.PinpointConsumer

build:
	mvn clean compile

copy:
	-mkdir pinpoint-agent
	cp -r ~/work/pinpoint/agent/target/pinpoint-agent-1.5.2-SNAPSHOT/* pinpoint-agent/
	sed -i 's/profiler.collector.ip=127.0.0.1/profiler.collector.ip=172.17.42.1/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.span.port=9996/profiler.collector.span.port=29996/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.stat.port=9995/profiler.collector.stat.port=29995/' pinpoint-agent/pinpoint.config
	sed -i 's/profiler.collector.tcp.port=9994/profiler.collector.tcp.port=29994/' pinpoint-agent/pinpoint.config
