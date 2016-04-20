#!/usr/bin/python
# Filename: hystrix.py

# This start/stop the hystrix test demos
# inlucde 2 web app, jumper -> echo

import os
import sys
import subprocess

def get_sysinfo():
    sys = platform.system()
    return os.getpid(),sys
    
def get_path():
    p=os.path.split(os.path.realpath(__file__))  # ('D:\\workspace\\python\\src\\mysql', 'dao.py')
    p=os.path.split(p[0])
    if not p:
        os.mkdir(p)
    return p[0]


def get_pid_path():
    return get_path() +'/tmp/yqs.pid'


def check_pid(pid = 0,osname=''):
    if pid is None or pid == 0:
        return False
    wincmd = 'tasklist /FI "PID eq %s"  /FI "IMAGENAME eq python.exe "' % str(pid)
    lincmd = 'ps ax |grep %s |grep python' % str(pid)
    cmd,size = (wincmd,150) if osname=='Windows' else (lincmd,20)
    returnstr=subprocess.Popen(cmd,stdout=subprocess.PIPE,stderr=subprocess.PIPE, shell=True)
    data = returnstr.stdout.read()
    return len(data) > size
    
def read_pid():
    if os.path.exists(get_pid_path()):
        try:
            with open(get_pid_path(),'r') as f:
                strpid = f.readline().strip()
                return int(strpid)
        except Exception :
            return None
    return None


def rm_pid():
    if os.path.exists(get_pid_path()):
        os.remove(get_pid_path())
        
def kill(pid):
    """kill function for Win32"""
    kernel32 = ctypes.windll.kernel32
    handle = kernel32.OpenProcess(1, 0, pid)
    return (0 != kernel32.TerminateProcess(handle, 0))

def check_run():
    pid,osname = get_sysinfo()
    if not os.path.exists(get_pid_path()):
        with open(get_pid_path(),'w') as f: f.write(str(pid))
        return False
    
    # start checking
    rs = check_pid(read_pid(),osname)
    if not rs : 
        with open(get_pid_path(),'w') as f: f.write(str(pid))
    return rs

class Control :
    def start(self):
        if check_run():
            print 'pro has run'
        else :
            print 'pro start...'
            time.sleep(1000)
    
    def stop(self):
        filePid = read_pid()
        if filePid is not None and filePid > 0:
            print 'pro has kill %s' % filePid
            kill(filePid)
            rm_pid()
        else :
            print 'Process has closed'
            
    def check(self):
        filePid = read_pid()
        if not filePid or not check_run() :
            message = "Process has closed\n"
            sys.stderr.write(message)
        else :
            message = "The process has been run, the process id:%d\n"
            sys.stderr.write(message % filePid)
                
    def test(self):
        print "usage: start|stop|check|test|help"

    def helpInfo(self):
        print "usage: start|stop|check|test|help"

def start_hystrix():
    #p=subprocess.Popen("MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=echo1 -Dpinpoint.applicationName=EchoService' mvn -f echowebsvr/pom.xml tomcat:run -Dmaven.tomcat.port=8099", stdout=open('log/echo.log', 'w'), stderr=open('log/echo.log', 'w'), shell=True)
    #p=subprocess.Popen('ls')
    #p=subprocess.Popen(["mvn", "-f", "echowebsvr/pom.xml", "tomcat:run", "-Dmaven.tomcat.port=8099"], env={'MAVEN_OPTS':'-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=echo1 -Dpinpoint.applicationName=EchoService'}, stdout=open('log/echo.log', 'w'), stderr=open('log/echo.log', 'w'), shell=False)
    p=subprocess.Popen("MAVEN_OPTS='-javaagent:./pinpoint-agent/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=echo1 -Dpinpoint.applicationName=EchoService' mvn -f echowebsvr/pom.xml tomcat:run -Dmaven.tomcat.port=8099 &> log/echosvr.log & echo $! > log/echosvr.pid", shell=True)

def stop_hystrix():
    p=subprocess.Popen("if [ -f log/echosvr.pid ]; then kill `cat log/echosvr.pid` || rm log/echosvr.pid; fi", shell=True)

####################################################

if __name__ == "__main__":
    contr=Control()
    if len(sys.argv) == 2:
        param = sys.argv[1]
        if 'start' == param:
            #contr.start()
            start_hystrix()
        elif 'stop' == param:
            #contr.stop()
            stop_hystrix()
        elif 'check' == param:
            contr.check()
        elif 'test' == param:
            contr.test()
        elif 'help' == param:
            contr.helpInfo() 
        else:
            print r"not yes cmd"
            sys.exit(2)
    else:
        print "usage: %s start|stop|check|test|help" % sys.argv[0]       
