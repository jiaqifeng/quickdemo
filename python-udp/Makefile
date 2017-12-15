# test nginx for udp stream
# below is example for usage

run:
	./udpThriftserver.py 29993

server:
	./server.py 54321

client:
	./client.py localhost 54321

# not tested, just for ref
genpy:
	-mkdir pinpoint
	~/work/pinpoint/thrift/src/compiler/linux/thrift-0.9.2 --gen py -o pinpoint attackchecker.thrift
	#~/work/pinpoint/thrift/src/compiler/linux/thrift-0.9.2 --gen py -o pinpoint Command.thrift
	#~/work/pinpoint/thrift/src/compiler/linux/thrift-0.9.2 --gen py -o pinpoint Pinpoint.thrift
	~/work/pinpoint/thrift/src/compiler/linux/thrift-0.9.2 --gen py -o pinpoint Trace.thrift
	cp -r pinpoint/gen-py/Trace .
	cp -r pinpoint/gen-py/attackchecker .
