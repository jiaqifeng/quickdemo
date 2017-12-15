#!/usr/bin/env python
# attackInfo Server - receive and print
# author Jiaqi Feng
import socket, traceback, sys, struct

import struct
from ctypes import *

from thrift.protocol import TCompactProtocol
from thrift.transport import TTransport

from attackchecker.ttypes import TAttackInfo

host = ''
textport = sys.argv[1]

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.bind((host, int(textport)))

def trans(s):
    return "b'%s'" % ''.join('\\x%.2x' % x for x in s)

def printhex(s):
    i=0;
    for c in s:
        print "0x%2x " % ord(c),
        i=i+1
        if (i%16 == 0):
            print ""        
    print ""

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

while 1:
    try:
        message, address = s.recvfrom(8192)
        #print "Got data from", address, ": ", ''.join(map(lambda x:('/x' if len(hex(x))>=4 else '/x0')+hex(x)[2:],message))
        #print type(message), # <type 'str'>
        #print "get message:"
        #printhex(message)

        transport = TTransport.TMemoryBuffer(message)
        prot = TCompactProtocol.TCompactProtocol(transport)

        header = PinpointHeader(message[:4])
        print "get header sig=%#x, version=%#x, type=%d" % (header.signature, header.version, header.type)
        transport.read(4) # jump over header, see HeaderTBaseSerializer
        attackInfo = TAttackInfo()
        attackInfo.read(prot)
        #s.sendto(message, address)
        print(attackInfo)
    except (KeyboardInterrupt, SystemExit):
        raise
    except:
        traceback.print_exc()
