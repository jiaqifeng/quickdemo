#!/usr/bin/env python
import sys
from socket import *  
  
HOST = 'localhost'  
PORT = int(sys.argv[1])
BUFSIZ = 128  
ADDR = (HOST, PORT)  
  
while True:  
    client = socket(AF_INET, SOCK_STREAM)  
    client.connect(ADDR)  
    data = raw_input('>')  
    if not data:  
        break  

    client.send('%s\r\n' % data)  
    #data = client.recv(BUFSIZ)  
    #if not data:  
    #    break  
    #print data.strip()  
      
client.close()  
