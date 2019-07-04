SET WTKDIR=c:/WTK22
SET CP=%WTKDIR%/lib/cldcapi10.jar;%WTKDIR%/lib/midpapi20.jar;./classes.zip 
del .\output\*.* /Q
"c:/program files/java/jdk1.5.0_06/bin/javac.exe" -source 1.3 -target 1.1 -bootclasspath %CP% ./src/*.java ./src/ru/develgame/JNDSWindowsManager/*.java ./src/ru/develgame/JNDSWindowsManager/Forms/*.java ./src/ru/develgame/JNDSWindowsManager/Components/*.java ./src/ru/develgame/JNDSWindowsManager/Actions/*.java
%WTKDIR%/bin/preverify -classpath %CP% ./src
del .\src\ru\develgame\JNDSWindowsManager\Forms\*.class /Q
del .\src\ru\develgame\JNDSWindowsManager\Components\*.class /Q
del .\src\ru\develgame\JNDSWindowsManager\Actions\*.class /Q
del .\src\ru\develgame\JNDSWindowsManager\*.class /Q
del .\src\*.class /Q
