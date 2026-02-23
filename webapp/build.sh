#!/bin/bash
# Build script for Spring Boot Login Web Application

echo ""
echo "======================================="
echo "Spring Boot Login Web Application - Build"
echo "======================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed"
    echo "Please install Maven from https://maven.apache.org/"
    exit 1
fi

echo "Building project..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1
fi

echo ""
echo "======================================="
echo "Build completed successfully!"
echo "======================================="
echo ""
echo "Run the application with: mvn spring-boot:run"
echo "Or manually with: java -jar target/login-webapp-1.0.0.jar"
echo ""
