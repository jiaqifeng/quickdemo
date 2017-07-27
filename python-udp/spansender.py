#!/usr/bin/env python
# send pinpoint Span through UDP

import socket, sys, struct, datetime, time
from ctypes import *

from thrift.protocol import TCompactProtocol
from thrift.transport import TTransport

from Trace.ttypes import *

def packTxId(agentid, starttime, sequence):
    # refer TransactionIdUtils.writeTransactionId()
    # byte:0, PrefixedString:agentid, VLong:time, VLong:sequence
    trans = TTransport.TMemoryBuffer()
    prot=TCompactProtocol.TCompactProtocol(trans)
    prot.state = TCompactProtocol.VALUE_WRITE
    prot.writeByte(0)
    #prot.writeI32(len(agentid))
    #trans.write(agentid)
    prot.writeString(agentid)
    prot.writeI64(starttime)
    prot.writeI64(sequence)
    return trans.getvalue()
                
host = "localhost" #sys.argv[1]
textport = 29996   #"sys.argv[2]

header=struct.pack('4B', 0xef, 0x10, 0, 40)

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
try:
    port = int(textport)
except ValueError:
    port = socket.getservbyname(textport, 'udp')
s.connect((host, port))

#s.sendall(header)

transport = TTransport.TMemoryBuffer()
prot = TCompactProtocol.TCompactProtocol(transport)

# fill in span
agentid="agent1"
agentstarttime=int(round(time.time()*1000))
sequence=1
txIdstr=agentid+"^"+str(agentstarttime)+"^"+str(sequence)
txId=struct.pack('B'+str(len(txIdstr))+'s', 0, txIdstr)

#TSpan(agentId:agent1, applicationName:EchoService, agentStartTime:1501121527534, transactionId:00 01 EE FD C2 8E D8 2B 01, spanId:-1385129957079395241, startTime:1501121560702, elapsed:77, rpc:/echo/hello, serviceType:1010, endPoint:localhost:8099, remoteAddr:127.0.0.1, flag:0, spanEventList:[TSpanEvent(sequence:0, startElapsed:7, endElapsed:70, serviceType:1011, depth:1, apiId:8)], apiId:9, applicationServiceType:1000)
span=TSpan(agentId=agentid, applicationName='EchoService', spanId=-1385129957079395241, startTime=1501121560702, elapsed=77, rpc='/echo/hello', serviceType=1010, endPoint='localhost:8099', remoteAddr="127.0.0.1", flag=0, apiId=9, applicationServiceType=1000)
#span.transactionId=packTxId(agentid, agentstarttime,sequence)
span.transactionId=struct.pack('9B', 0x00, 0x01, 0xEE,0xFD, 0xC2, 0x8E, 0xD8 ,0x2B, 0x01)

span.write(prot)
alldata=header+transport.getvalue()
for i in range(0, 1):
    s.sendall(alldata)
# while 1:
#     print "Enter data to transmit:"
#     data = sys.stdin.readline().strip()
#     s.sendall(data)
#     print "Looking for replies; press Ctrl-C or Ctrl-Break to stop."
#     buf = s.recv(2048)
#     if not len(buf):
#         break
#     print "Server replies: ",
#     sys.stdout.write(buf)
#     print "\n"
