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

test: $(MAIN).class
	mv ./src/Unify.class ./
	for i in 1 2 3 4 5 6 7 8; do echo "Constraint Set $$i"; java Unify ./test/cs$$i.txt; echo ""; done
clean:
	$(RM) *.class
	$(RM) unify*

