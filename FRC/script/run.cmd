set CURR_DIR=%CD%
set LIB_DIR=%CURR_DIR%\lib
set CONF_DIR=%CURR_DIR%\properties
set PATH=%PATH%;%CONF_DIR%
rem java -classpath %PATH% com.frc.main.Application
java -Djava.ext.dirs=%LIB_DIR% com.frc.main.Application