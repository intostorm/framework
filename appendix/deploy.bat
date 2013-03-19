@echo off
for %%i in (%0) do set dir=%%~dpi 
cd /d %dir%
cd ..
call mvn deploy -DskipTests=true
pause 
