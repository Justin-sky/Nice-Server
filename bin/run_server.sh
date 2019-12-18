#!/bin/sh

#===========================================================================================
# Java Environment Setting
#===========================================================================================
error_exit ()
{
    echo "ERROR: $1 !!"
    exit 1
}

[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/local/java
[ ! -e "$JAVA_HOME/bin/java" ] && error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)!"
export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"
export BASE_DIR=$(dirname $0)/..
export CLASSPATH=.:${BASE_DIR}/conf:${CLASSPATH}

#===========================================================================================
# JVM Configuration
#===========================================================================================


#JAVA_OPT="${JAVA_OPT}  -Dfile.encoding=UTF-8 -server -Xms2g -Xmx2g  -Xss256K -XX:MaxMetaspaceSize=512m -XX:InitialBootClassLoaderMetaspaceSize=256m -XX:CompressedClassSpaceSize=512m"
JAVA_OPT="${JAVA_OPT} -server -Xms2g -Xmx2g  -Xss512K -XX:MaxMetaspaceSize=256m -XX:CompressedClassSpaceSize=64m"
JAVA_OPT="${JAVA_OPT} -XX:+UseG1GC -XX:ParallelGCThreads=8  -XX:ConcGCThreads=4 -XX:NativeMemoryTracking=detail "
JAVA_OPT="${JAVA_OPT} -verbose:gc -Xloggc:${HOME}/gs_core_gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow"
JAVA_OPT="${JAVA_OPT} -Djava.ext.dirs=${BASE_DIR}/lib:$JAVA_HOME/jre/lib/ext"
#JAVA_OPT="${JAVA_OPT} -Xdebug -Xrunjdwp:transport=dt_socket,address=9555,server=y,suspend=n"
JAVA_OPT="${JAVA_OPT} -cp ${CLASSPATH}"


if [ -z $@ ];then
	$JAVA ${JAVA_OPT} com.ktgame.lipstick.WebSocketServer
else
	$JAVA ${JAVA_OPT} com.ktgame.lipstick.WebSocketServer $@
fi
