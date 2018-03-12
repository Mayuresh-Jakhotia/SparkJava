echo on
title SparkJava Sample Web Service
md Java

::Download java
bitsadmin /transfer javaDownloadJob "http://javadl.oracle.com/webapps/download/AutoDL?BundleId=230539_2f38c3b165be4555a1fa6e98c45e0808" "%~dp0Java\java.exe"

::Install Java
"%~dp0Java\java.exe" /s INSTALLDIR="%~dp0\Java\JDK"

"%~dp0Java\JDK\bin\java.exe" -jar target/my-app-1.0.jar