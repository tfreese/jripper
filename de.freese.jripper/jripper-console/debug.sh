#! /bin/sh
#
# Thomas Freese

#JRE_PAR="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y"
JRE_PAR=""
CP="target/classes:../jripper-core/target/classes"
CP=$CP":/home/tommy/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar"

java $JRE_PAR -cp $CP de.freese.jripper.console.Main