#!/usr/bin/env python
# TCP Echo Server -  tcpserver.py

import sys, struct
import logging
from ctypes import *

from attackchecker.ttypes import TAttackInfo

from thrift.Thrift import TProcessor
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
from thrift.server import TServer

#from SocketServer import TCPServer, StreamRequestHandler

host = ''
textport = sys.argv[1]
addr = ( host, int(textport) )

#class MyReqHandler(StreamRequestHandler):
#    def handle(self):
#        print '...connect from:', self.client_address
#        print 'message:', self.rfile.readline()

#tcpServ = TCPServer(addr, MyReqHandler)
#print 'waiting for connection...'
#tcpServ.serve_forever()
def printhex(s):
    i=0;
    for c in s:
        print "0x%2x " % ord(c),
        i=i+1
        if (i%16 == 0):
            print ""        
    print ""

class PacketHeader(Structure):
    _fields_ = [
        ("typehigh", c_ubyte),
        ("typelow", c_ubyte),
    ]

    def __new__(self, buffer=None):
        return self.from_buffer_copy(buffer)

    def __init__(self, buffer=None):
        self.packettype = self.typehigh*256 + self.typelow

class ReadInt(Structure):
    _fields_ = [
        ("type1", c_ubyte),
        ("type2", c_ubyte),
        ("type3", c_ubyte),
        ("type4", c_ubyte),
    ]

    def __new__(self, buffer=None):
        return self.from_buffer_copy(buffer)

    def __init__(self, buffer=None):
        self.value = ((self.type1*256 + self.type2)*256 + self.type3)*256 + self.type4

class AttackInfoProcessor(TProcessor):
    def __init__(self):
        self.packet_map = { 150 : "handshake"}
        
    def process(self, iprot, oprot):
        print("enter AttackInfoProcessor.process")
        #ttttttttttt=iprot.trans.read(2)
        print "unpack packet type=%d" % struct.unpack("!h", iprot.trans.read(2))
        printhex(ttttttttttt)

        header = PacketHeader(ttttttttttt)
        print "header type=%d" % (header.packettype)  # this should be 150 for handshake

        if (header.packettype == 150):
            iprot.trans.read(4) # messageId see ControlHandshakePacket
            length=ReadInt(iprot.trans.read(4)).value
            print "handshake payload length=%d" % length
            iprot.trans.read(length)
            return
        
        (name, itype, seqid) = iprot.readMessageBegin()
        print("receive name=%s type=%d, seqid=%d" % (name, itype, seqid))
        args = TAttackInfo()
        args.read(iprot)
        iprot.readMessageEnd()
        print("receive args", args)
        #self._handler.zip()

      
if __name__ == '__main__':
    print("start main")
    #handler = CalculatorHandler()
    processor = AttackInfoProcessor()
    transport = TSocket.TServerSocket(port=int(textport))
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TCompactProtocol.TCompactProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
    server.logger = logging.getLogger(__name__)
    server.serve()
    print("end of main")
