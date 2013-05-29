@echo off
echo [PreRequirment] Download yuicompressor-2.4.8pre.jar from http://yuilibrary.com/downloads/yuicompressor and put it here.

cd %~dp0
java -jar yuicompressor-2.4.8pre.jar -o ..\src\main\webapp\static\styles\default.min.css ..\src\main\webapp\static\styles\default.css

java -jar yuicompressor-2.4.8pre.jar -o ..\src\main\webapp\static\styles\siteV2.0.css ..\src\main\webapp\static\styles\siteV2.0-origin.css

java -jar yuicompressor-2.4.8pre.jar -o ..\src\main\webapp\static\zepto\android.js ..\src\main\webapp\static\zepto\android-origin.js

pause