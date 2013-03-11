@echo off
for %%i in (%0) do set dir=%%~dpi 
cd /d %dir%
start mvn install:install-file -Dfile=ojdbc6.jar -DgroupId=oracle.jdbc -DartifactId=OracleJdbc -Dversion=11.2.0.1.0 -Dpackaging=jar -DgeneratePom=true
start mvn install:install-file -Dfile=pinyin4j-2.5.0.jar -DgroupId=com.pinyin -DartifactId=pinyin4j -Dversion=2.5.0 -Dpackaging=jar -DgeneratePom=true
start mvn install:install-file -Dfile=itextasian.jar -DgroupId=com.lowagie -DartifactId=itextasian -Dversion=1.4.2 -Dpackaging=jar -DgeneratePom=true

