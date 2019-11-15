# ProgrammingLab

Project of the "Programming Languages" course (2019-2): Unification algorithm implementation

Operating system version used: Manjaro Linux 18.1.2 Juhraya

### Requirements 📋

Programming language used: Java 11.0.5

Compiler version used: javac 11.0.5

### Instructions ⌨️

1. You must clone the repository

```
$ git clone https://github.com/sanhidalgoo/ProgrammingLab.git
$ cd ProgrammingLab
```

2. Now, you have two options to test the algorithm
  
   1. If you want to run the tests for default only must typing:
    
      ```
      $ make test
      ```
      Here you will get the results obtained from the algorithm with the 8 data sets that are in the /test/ directory
    
    2. If you want to run the unification algorithm with your own data set you must write the following on your command line: 

       ```
       $ make run
       ```
       This generates an executable (unify), then you must execute it by sending the path of your file as a parameter:
         
       ```
       $ ./unify <FILE PATH>
       ```
If you want to clean the repository to leave it as "new" type this in the terminal:

   
    $ make clean
   
### Output Format

The output will be a set of substitutions.

A substitution is represented by the symbol '/->'

For example, the expression a/->b should be interpreted as follows a is replaced by b

       
## Author ✒️

Name: Santiago Hidalgo Ocampo

E-mail: shidalgoo1@eafit.edu.co
