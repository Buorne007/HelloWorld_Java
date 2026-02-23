@echo off
REM Build script for Spring Boot Login Web Application
REM This script compiles and packages the application using Maven

echo.
echo ===============================================
echo Spring Boot Login Web Application - Build
echo ===============================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if errorlevel 1 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven from https://maven.apache.org/
    exit /b 1
)

echo Building project...
call mvn clean package -DskipTests

if errorlevel 1 (
    echo Build failed!
    exit /b 1
)

echo.
echo ===============================================
echo Build completed successfully!
echo ===============================================
echo.
echo Run the application with: mvn spring-boot:run
echo Or manually with: java -jar target/login-webapp-1.0.0.jar
echo.
