#! /bin/sh
#
# Thomas Freese

JRE_PAR="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y"
java $JRE_PAR -cp target/test-classes de.freese.jripper.core.CDParanoia