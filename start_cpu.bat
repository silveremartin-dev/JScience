@echo off
echo Starting JScience in CPU Mode...
java -cp target/classes;target/test-classes -Dorg.jscience.compute.mode=CPU org.jscience.JScience
pause
