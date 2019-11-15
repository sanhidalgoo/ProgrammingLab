JC=javac
JVM=java 
FILE=

.SUFFIXES: .java .class

.java.class: 
	$(JC) $*.java

CLASSES = \
        ./src/Unify.java

MAIN = ./src/Unify

default: ./src/Unify.java

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	echo '#!/bin/bash' >> unify	
	mv ./src/Unify.class ./
	echo java Unify '$$1' >> unify
	chmod 755 unify

clean:
	$(RM) *.class
	$(RM) unify*

