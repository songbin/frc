REM Set jdk bin path
REM set PATH=%PATH%;C:\Program Files\Java\jdk1.7.0_75\bin
set curdir=%~dp0
cd /d %curdir%


cd ..
rd /s/q bin
md bin
cd bin
md frc
cd frc
md libs
md properties
cd ..
cd ..

cd src

jar cf frc.jar com 
move frc.jar ..\bin\frc\libs
copy *.xml ..\bin\frc\properties
cd ..
copy  libs\*.jar bin\frc\libs
copy properties\*.properties bin\frc\properties
copy script\*.cmd bin\frc
copy script\*.sh bin\frc
cd ..
pause