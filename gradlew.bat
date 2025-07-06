@echo off
set WRAPPER_DIR=%~dp0gradle\wrapper
java -jar "%WRAPPER_DIR%\gradle-wrapper.jar" %*
