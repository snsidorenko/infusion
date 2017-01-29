
@echo off

echo --- BUILD...
set path=%PATH%;E:\work\java\apache-ant-1.8.2\bin
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_92
call ant -DserverId=LOC -Dsource_dir=c:/temp/spring/app -Dbuild_dir=C:/work/apache-tomcat-7.0.55/webapps/app -Dtemp_dir=e:/Temp


echo --- DONE...

pause