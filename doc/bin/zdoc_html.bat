@echo off

rem zdoc html [source dir] [target dir]
rem zdoc pdf [source dir] [target pdf path]
rem zdoc rtf [source dir] [target rtf path]

for %%i in (%0) do set dir=%%~dpi 
cd /d %dir%

set sourceDir=..\doc
set targetDir=..\src\main\webapp
set indexFileName=index.xml

java -Xms256m -Xmx512m -jar zdoc_all.jar html %sourceDir% %targetDir% %indexFileName% %imagesAddress%
pause;