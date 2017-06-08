# This a demo spring-boot restful using mybatis/h2 application to test pinpoint *async* plugin.
AddressController could call MyRunnable or MyCallable when handling restful service which will throw java.lang.ArithmeticException another access.
Refer the Makefile on how to run.
# TODO
switch h2/mysql db by argument
