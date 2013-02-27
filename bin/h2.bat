@echo off
echo [INFO] Package the war in target dir.

cd %~dp0
cd ..
call mvn initialize -Pstartdb
cd bin
pause