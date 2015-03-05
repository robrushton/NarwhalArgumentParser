@echo off
cd Demos
echo "Running -h demo..."
javac VolumeCalculator.java
@echo on
java VolumeCalculator -h
@echo off
echo.
echo "-h demo complete!"
pause
