run:
	mvn clean compile
	mvn exec:java -Dexec.mainClass=com.jack.pinpoint.utils.ConvertTxId -Dexec.args="'00 0E 6B 61 66 6B 61 50 31 D7 8E 91 C2 C1 2A 01'"

example:
	mvn exec:java -Dexec.mainClass=com.jack.pinpoint.utils.Long2Date -Dexec.args="1460696020803"

# copy jar used by hbase shell, also copy pinpoint-commons-1.6.1-SNAPSHOT.jar pinpoint-profiler-1.6.1-SNAPSHOT.jar
copy:
	mvn package && cp target/utils-1.6.0-RC1.jar ~/work/pinpoint/quickstart/hbase/hbase/lib/
