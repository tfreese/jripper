#! /bin/sh
#
# Thomas Freese

JRE_PAR=""
#JRE_PAR=$JRE_PAR -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y
CP="target/classes:../jripper-core/target/classes"
CP=$CP":/home/tommy/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar"
CP=$CP":/home/tommy/.m2/repository/org/slf4j/slf4j-api/1.7.2/slf4j-api-1.7.2.jar"

java $JRE_PAR -cp $CP de.freese.jripper.console.JRipperConsole