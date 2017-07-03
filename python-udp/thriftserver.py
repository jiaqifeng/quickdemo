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

class PinpointHeader(Structure):
    _fields_ = [
        ("signature", c_ubyte),
        ("version", c_ubyte),
        ("typehigh", c_ubyte),
        ("typelow", c_ubyte),
    ]

    def __new__(self, buffer=None):
        return self.from_buffer_copy(buffer)

    def __init__(self, buffer=None):
        self.type = self.typehigh*256 + self.typelow

class const:
    APPLICATION_SEND = 1
    APPLICATION_TRACE_SEND = 2
    APPLICATION_TRACE_SEND_ACK = 3
    APPLICATION_REQUEST = 5
    APPLICATION_RESPONSE = 6
    APPLICATION_STREAM_CREATE = 10
    APPLICATION_STREAM_CREATE_SUCCESS = 12
    APPLICATION_STREAM_CREATE_FAIL = 14
    APPLICATION_STREAM_CLOSE = 15
    APPLICATION_STREAM_PING = 17
    APPLICATION_STREAM_PONG = 18
    APPLICATION_STREAM_RESPONSE = 20
    CONTROL_CLIENT_CLOSE = 100
    CONTROL_SERVER_CLOSE = 110
    CONTROL_HANDSHAKE = 150
    CONTROL_HANDSHAKE_RESPONSE = 151
    CONTROL_PING = 200
    CONTROL_PONG = 201
    UNKNOWN = 500
    PACKET_TYPE_SIZE = 2

class ControlMessageDecoder:
    TYPE_CHARACTER_NULL = 'N'
    TYPE_CHARACTER_BOOL_TRUE = 'T'
    TYPE_CHARACTER_BOOL_FALSE = 'F'
    TYPE_CHARACTER_INT = 'I'
    TYPE_CHARACTER_LONG = 'L'
    TYPE_CHARACTER_DOUBLE = 'D'
    TYPE_CHARACTER_STRING = 'S'
    CONTROL_CHARACTER_LIST_START = 'V'
    CONTROL_CHARACTER_LIST_END = 'z'
    CONTROL_CHARACTER_MAP_START = 'M'
    CONTROL_CHARACTER_MAP_END = 'z'

    def decode(self, buffer):
        ptype, = struct.unpack("!c", buffer[:1])
        buffer = buffer[1:]
        print " get type %s" % ptype
        #print type(ptype)
        if (ptype == 'N'):
            return "", buffer[1:];
        if (ptype == 'T'):
            return True, buffer;
        if (ptype == 'F'):
            return False, buffer;
        if (ptype == 'I'):
            value, = struct.unpack("!I", buffer[:4])
            return value, buffer[4:];
        if (ptype == 'L'):
            value, = struct.unpack("!q", buffer[:8])
            return value, buffer[8:];
        if (ptype == 'D'):
            value, = struct.unpack("!d", buffer[:8])
            return value, buffer[8:];
        if (ptype == 'S'):
            length, buffer = self.decodeLength(buffer)
            print "!"+str(length)+"c"
            value, =struct.unpack("!"+str(length)+"s", buffer[:length])
            print "value = %s" % value
            return value, buffer[length:];
        if (ptype == 'M'):
            print "start decode map"
            while (True):
                endchar, = struct.unpack("!c", buffer[:1])
                if (endchar == 'z'):
                    print "decode map end"
                    break;
                else:
                    print "start decode key"
                key, buffer = self.decode(buffer);
                value, buffer = self.decode(buffer);
                print "decode key=%s value=%s" % (key, value)
            return {}

    def decodeLength(self, buffer):
        result = 0
        shift = 0
        i=0
        while (True):
            v, = struct.unpack("!B", buffer[i:i+1])
            result |= (v & 0x7f) << shift;
            i = i + 1
            if ((v & 0x80) != 128):
                break;
            shift += 7;
        print "decodeLength return length=%d, i=%d" % (result, i)
        return result, buffer[i:]
        
class AttackInfoProcessor(TProcessor):
    def __init__(self):
        self.packet_map = { 150 : "handshake"}
        
    def process(self, iprot, oprot):
        print("enter AttackInfoProcessor.process")
        packettype, =struct.unpack("!h", iprot.trans.read(2))
        print "get packet type=%d" % packettype

        if (packettype == const.CONTROL_HANDSHAKE):
            # packet: type:short, messageId:int, length:int, payload:byte[]:see ControlMessageEncoder.encodeMap
            # created in PinpointClientHandshaker.handshakeStart()
            messageId, length = struct.unpack("!2I", iprot.trans.read(8)) # see ControlHandshakePacket
            print "get handshake messageId=%d, payload length=%d" % (messageId, length)
            buffer = iprot.trans.read(length)
            ControlMessageDecoder().decode(buffer)
            # PacketDecoder.decode()
            # type, id=ControlHandshakeResponsePacket.readBuffer(), payloadLen(PayloadPacket.readPayload())
            resp=struct.pack('!hII5s', 151, messageId,5, "hello")
            oprot.trans.write(resp)
            oprot.trans.flush()
            return

        if (packettype == 1): #application
            #buffer, = struct.unpack("!10s", iprot.trans.read(10))
            #printhex(buffer)
            length, = struct.unpack("!I", iprot.trans.read(4))
            print "get app packet length=%d" % length
            header = PinpointHeader(iprot.trans.read(4))
            args = TAttackInfo()
            args.read(iprot)
            iprot.readMessageEnd()
            print("receive args", args)

        
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
