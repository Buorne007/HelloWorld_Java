@echo off
REM Run script for Spring Boot Login Web Application

echo.
echo ===============================================
echo Spring Boot Login Web Application - Running
echo ===============================================
echo.

if not exist target\login-webapp-1.0.0.jar (
    echo Jar file not found. Building first...
    call build.bat
    if errorlevel 1 (
        echo Build failed!
        exit /b 1
    )
)

echo Starting application on http://localhost:8080
echo Press Ctrl+C to stop the server
echo.

java -jar target/login-webapp-1.0.0.jar

pause
