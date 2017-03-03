# setup activemq server under ubuntu
下载 http://archive.apache.org/dist/activemq/ 5.14.3
解压到~/package/activemq-5.14.3,启动如下

activemq-5.14.3/bin$ ./activemq start
INFO: Loading '/home/jack/package/activemq-5.14.3//bin/env'
INFO: Using java '/usr/java/jdk1.8.0_71/bin/java'
INFO: Starting - inspect logfiles specified in logging.properties and log4j.properties to get details
INFO: pidfile created : '/home/jack/package/activemq-5.14.3//data/activemq.pid' (pid '28520')

退出
./activemq stop

浏览器 http://localhost:8161/admin/ 账号admin/admin

