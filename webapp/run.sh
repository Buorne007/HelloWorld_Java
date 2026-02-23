#!/bin/bash
# Run script for Spring Boot Login Web Application

echo ""
echo "======================================="
echo "Spring Boot Login Web Application - Running"
echo "======================================="
echo ""

if [ ! -f target/login-webapp-1.0.0.jar ]; then
    echo "Jar file not found. Building first..."
    ./build.sh
    if [ $? -ne 0 ]; then
        echo "Build failed!"
        exit 1
    fi
fi

echo "Starting application on http://localhost:8080"
echo "Press Ctrl+C to stop the server"
echo ""

java -jar target/login-webapp-1.0.0.jar
