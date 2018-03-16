echo on
title SparkJava Cleaning Up Everything

::Create Temp File 'u'
type NUL > %temp%\u

::Find Process by Port & Save Unique Elements In a File
FOR /F "usebackq tokens=5" %%i IN (`netstat -ano ^| find "4567"`) DO ( find "%%i" "%temp%\u" >nul 2>&1 || <nul set/p=%%i>> "%temp%\u")

::Check if any PID was found. If not, it means server is stopped. Goto EMPTY section
set size=0
for /f %%i in ("%temp%\u") do set size=%%~zi
if %size% == 0 GOTO EMPTY

::Set the file content in a variable (one ProcessID)
set /p ProcessID=<%temp%\u

taskkill /PID %ProcessID% /F

:EMPTY

::Delete file
del "%temp%\u"

::Delete Java
rmdir /s /q "%~dp0Java"
