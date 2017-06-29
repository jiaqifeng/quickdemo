#!/usr/bin/env python
# TCP Echo Server -  tcpserver.py

import sys
from SocketServer import TCPServer, StreamRequestHandler
from time import ctime

host = ''
textport = sys.argv[1]
addr = ( host, int(textport) )

class MyReqHandler(StreamRequestHandler):
    def handle(self):
        print '...connect from:', self.client_address
        print 'message:', self.rfile.readline()

tcpServ = TCPServer(addr, MyReqHandler)
print 'waiting for connection...'
tcpServ.serve_forever()


