include ../common.gmk

PORT=8092

config:
	sed -i 's/profiler.log4j.logging.transactioninfo=false/profiler.log4j.logging.transactioninfo=true/' pinpoint-agent/pinpoint.config
	#sed -i 's/profiler.sampling.rate=1/profiler.sampling.rate=2/' pinpoint-agent/pinpoint.config
	sed -i 's/INFO/DEBUG/' pinpoint-agent/lib/log4j.xml

run: mvnrebuild
	mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

runa: mvnrebuild config
	MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-$(AGENT_VERSION).jar $(AGENT_ARGS) -Dpinpoint.agentId=localhost-$(PORT) -Dpinpoint.applicationName=MongoClient' mvn tomcat:run -Dmaven.tomcat.port=$(PORT)

curl:
	curl http://localhost:$(PORT)/mongo/mongo

excep:
	curl http://localhost:$(PORT)/echo/entry

attack:
	curl http://localhost:$(PORT)/echo/attack

.PHONY: run runa curl

