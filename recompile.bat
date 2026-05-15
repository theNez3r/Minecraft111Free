@echo off
echo Building...
java -jar RetroMCP-Java-CLI.jar build
timeout /t 3 /NOBREAK >nul 2>&1

echo -----------------
echo Done
pause

exit