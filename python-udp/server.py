#!/usr/bin/env python
# UDP Echo Server -  udpserver.py
# code by www.cppblog.com/jerryma
import socket, traceback, sys

host = ''
textport = sys.argv[1]

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.bind((host, int(textport)))

while 1:
    try:
        message, address = s.recvfrom(8192)
        print "Got data from", address, ": ", message
        s.sendto(message, address)
    except (KeyboardInterrupt, SystemExit):
        raise
    except:
        traceback.print_exc()
