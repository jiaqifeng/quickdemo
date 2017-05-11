#!/usr/bin/env python
# UDP Client - udpclient.py
# code by www.cppblog.com/jerryma
import socket, sys

host = sys.argv[1]
textport = sys.argv[2]

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
try:
    port = int(textport)
except ValueError:
    port = socket.getservbyname(textport, 'udp')
s.connect((host, port))
while 1:
    print "Enter data to transmit:"
    data = sys.stdin.readline().strip()
    s.sendall(data)
    print "Looking for replies; press Ctrl-C or Ctrl-Break to stop."
    buf = s.recv(2048)
    if not len(buf):
        break
    print "Server replies: ",
    sys.stdout.write(buf)
    print "\n"
