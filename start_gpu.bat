@echo off
echo Starting JScience in GPU Mode...
echo Note: Requires CUDA drivers installed.
java -cp target/classes;target/test-classes -Dorg.jscience.compute.mode=GPU org.jscience.JScience
pause
