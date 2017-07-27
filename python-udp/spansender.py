#!/usr/bin/env python
# send pinpoint Span through UDP for perf test

import socket, sys, struct, datetime, time
from ctypes import *

from thrift.protocol import TCompactProtocol
from thrift.transport import TTransport

from Trace.ttypes import *

def printhex(s):
    i=0;
    for c in s:
        print "0x%2x " % ord(c),
        i=i+1
        if (i%16 == 0):
            print ""
    print ""

# pinpoint java encode like protobuf? why not use thrift!
def packTxId(agentid, starttime, sequence):
    # refer TransactionIdUtils.writeTransactionId()
    # byte:0, PrefixedString:agentid, VLong:time, VLong:sequence
    trans = TTransport.TMemoryBuffer()
    prot=TCompactProtocol.TCompactProtocol(trans)
    prot.state = TCompactProtocol.VALUE_WRITE
    prot.writeByte(0)
    prot.writeI32(len(agentid)) # this should be vlen(zigzag(int32))
    trans.write(agentid)
    TCompactProtocol.writeVarint(trans, starttime)  # this should be vlen(long), no zigzag, like BytesUtils.writeVar64() in java
    TCompactProtocol.writeVarint(trans, sequence)
    #printhex(trans.getvalue())
    return trans.getvalue()  # type is str

#################### main ####################
host = "localhost" #sys.argv[1]
textport = 29996   #"sys.argv[2]

#common udp header for span
header=struct.pack('4B', 0xef, 0x10, 0, 40)

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
try:
    port = int(textport)
except ValueError:
    port = socket.getservbyname(textport, 'udp')
s.connect((host, port))

########## pack span data

# fill in span
agentid="agent1"
agentstarttime=int(round(time.time()*1000))
sequence=1

#TSpan(agentId:agent1, applicationName:EchoService, agentStartTime:1501121527534, transactionId:00 01 EE FD C2 8E D8 2B 01, spanId:-1385129957079395241, startTime:1501121560702, elapsed:77, rpc:/echo/hello, serviceType:1010, endPoint:localhost:8099, remoteAddr:127.0.0.1, flag:0, spanEventList:[TSpanEvent(sequence:0, startElapsed:7, endElapsed:70, serviceType:1011, depth:1, apiId:8)], apiId:9, applicationServiceType:1000)
span=TSpan(agentId=agentid,
           applicationName='EchoService',
           agentStartTime=agentstarttime,
           spanId=-1385129957079395241,
           startTime=int(round(time.time()*1000)),
           elapsed=77,
           rpc='/echo/hello',
           serviceType=1010,
           endPoint='localhost:8099',
           remoteAddr="127.0.0.1",
           flag=0,
           apiId=9,
           applicationServiceType=1000)
span.transactionId=packTxId(agentid, agentstarttime,sequence)
spanevent=TSpanEvent(sequence=0,
                     startElapsed=7,
                     endElapsed=70,
                     serviceType=1011,
                     depth=1,
                     apiId=8)
span.spanEventList=[spanevent]


for i in range(0, 300):
    transport = TTransport.TMemoryBuffer()
    prot = TCompactProtocol.TCompactProtocol(transport)
    span.spanId=span.spanId+1
    span.transactionId=packTxId(agentid, agentstarttime,i)
    span.write(prot)
    alldata=header+transport.getvalue()
    s.sendall(alldata)
