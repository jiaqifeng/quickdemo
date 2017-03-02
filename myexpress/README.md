This is an example nodejs project to test pinpoint.

# precondition
nodejs agent should placed at ../../pinpoint-node-agent
You can find the code at https://github.com/jiaqifeng/pinpoint-node-agent which was forked from peaksail's.

change pinpoint-node-agent/agent/conf/pinpoint.config files for pinpoint collector settings. For example I am using pinpoint quickstart for testing, set ip to 127.0.0.1, and port as 29996,29995,29994

# run
make # run nodejs app

make http # access app, using httpie

make curl # access app, using curl

# project generation
express --session --css stylus --ejs myexpress
and I add a jack page for test.
