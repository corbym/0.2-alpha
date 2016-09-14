#!/bin/bash
GROOVYMUD_HOME=/path/to/installation

libdir="$GROOVYMUD_HOME/lib"
# Build the classpath
CP=$CP\:$GROOVYMUD_HOME/config

for jar in `ls $libdir`
do
   CP=$CP\:$libdir/$jar
done

MUDSPACE=$GROOVYMUD_HOME/mudspace
LIB=$libdir

"java" -server $CONF -classpath "$CP" -Dworkspace.loc="$GROOVYMUD_HOME" -Dmaven.repo="$LIB" -Dmudspace="$MUDSPACE" -Djava.security.manager -Djava.security.policy=$GROOVYMUD_HOME/config/policy -Djava.security.auth.policy=$GROOVYMUD_HOME/config/policy.jaas -Djava.security.auth.login.config=$GROOVYMUD_HOME/config/jaas.config org.groovymud.engine.JMudEngine $@
