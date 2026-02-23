# Login System - Web Application

A modern web-based login system built with Spring Boot, HTML, CSS, and JavaScript. Fully responsive and feature-rich.

## Features

- **User Authentication**: Login with username and password
- **User Registration**: Create new accounts with validation
- **Password Management**: Change password from dashboard
- **User Profile**: View user information
- **Responsive Design**: Works on desktop, tablet, and mobile
- **Modern UI**: Clean and professional interface
- **Data Persistence**: File-based user database
- **REST API**: JSON-based API endpoints

## Prerequisites

- Java 8 or higher
- Maven 3.6+
- Modern web browser

## Installation

1. **Clone/Navigate to the webapp directory**:
   ```bash
   cd webapp
   ```

2. **Build the project**:
   
   **Windows**:
   ```bash
   build.bat
   ```
   
   **Linux/Mac**:
   ```bash
   chmod +x build.sh
   ./build.sh
   ```

3. **Run the application**:
   
   **Windows**:
   ```bash
   run.bat
   ```
   
   **Linux/Mac**:
   ```bash
   chmod +x run.sh
   ./run.sh
   ```

4. **Open in browser**:
   ```
   http://localhost:8080
   ```

## Development Mode

For development with auto-reload:

**Windows**:
```bash
dev.bat
```

**Linux/Mac**:
```bash
chmod +x dev.sh
./dev.sh
```

## Default Test Accounts

| Username | Password  | Name            |
|----------|-----------|-----------------|
| admin    | admin123  | Administrator   |
| john     | john123   | John Doe        |
| jane     | jane123   | Jane Smith      |

## Project Structure

```
webapp/
├── pom.xml                          # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/com/login/
│   │   │   ├── LoginWebApplication.java     # Main Spring Boot app
│   │   │   ├── model/
│   │   │   │   └── User.java               # User model
│   │   │   ├── service/
│   │   │   │   ├── AuthenticationService.java
│   │   │   │   └── UserDatabase.java
│   │   │   └── web/controller/
│   │   │       └── AuthController.java     # REST controllers
│   │   └── resources/
│   │       ├── application.properties      # Configuration
│   │       ├── templates/                  # HTML templates
│   │       │   ├── index.html
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   └── dashboard.html
│   │       └── static/
│   │           └── css/
│   │               └── style.css
│   └── test/                              # Tests
├── target/                          # Compiled output
├── build.bat / build.sh            # Build scripts
├── run.bat / run.sh                # Run scripts
├── dev.bat / dev.sh                # Development mode
└── README.md                        # This file
```

## API Endpoints

All endpoints return JSON responses.

### Authentication Endpoints

#### Login
```
POST /api/login
Parameters:
  - username: string
  - password: string

Response:
{
  "success": boolean,
  "message": string,
  "user": {User object}
}
```

#### Register
```
POST /api/register
Parameters:
  - username: string
  - password: string
  - email: string
  - fullName: string

Response:
{
  "success": boolean,
  "message": string
}
```

#### Logout
```
POST /api/logout

Response:
{
  "success": boolean,
  "message": string
}
```

#### Get Profile
```
GET /api/profile

Response:
{
  "success": boolean,
  "user": {User object}
}
```

#### Change Password
```
POST /api/change-password
Parameters:
  - oldPassword: string
  - newPassword: string
  - confirmPassword: string

Response:
{
  "success": boolean,
  "message": string
}
```

### Page Routes

- `GET /` - Home page
- `GET /login` - Login page
- `GET /register` - Registration page
- `GET /dashboard` - User dashboard (requires login)
- `GET /logout` - Logout and redirect to home

## Technology Stack

- **Backend**: Spring Boot 2.7.14
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Build Tool**: Maven
- **Data Storage**: File-based (serialization)
- **Server**: Embedded Tomcat

## Dependencies

- Spring Boot Web Starter
- Spring Boot Thymeleaf (template engine)
- Lombok (optional, for code generation)
- Jackson (JSON processing)

## Configuration

Edit `src/main/resources/application.properties`:

```properties
# Server Port
server.port=8080

# Application Context Path
server.servlet.context-path=/

# Application Name
spring.application.name=Login System Web Application

# Logging Level
logging.level.root=INFO
logging.level.com.login=DEBUG
```

## Building for Production

### Create JAR file:
```bash
mvn clean package
```

This creates `target/login-webapp-1.0.0.jar`

### Run JAR:
```bash
java -jar target/login-webapp-1.0.0.jar
```

## Troubleshooting

### Maven not found
- Install Maven from https://maven.apache.org/
- Ensure Maven is in your PATH

### Port 8080 already in use
- Change port in `application.properties`
- Or close the application using port 8080

### Build fails
- Ensure Java 8+ is installed: `java -version`
- Clear Maven cache: `mvn clean`
- Check internet connection (for downloading dependencies)

### Application won't start
- Check logs for errors
- Ensure all dependencies downloaded: `mvn dependency:resolve`
- Verify port is accessible

## Testing

### Login Flow
1. Go to http://localhost:8080/login
2. Enter username: `admin` password: `admin123`
3. Should redirect to dashboard

### Registration Flow
1. Go to http://localhost:8080/register
2. Fill in all fields with valid data
3. Should show success message

### Dashboard
1. After login, access http://localhost:8080/dashboard
2. View profile information
3. Change password with validation

## Security Notes

⚠️ **Educational Project** - Not for production use

For production, implement:
- Password hashing (bcrypt, argon2)
- SQL database (MySQL, PostgreSQL)
- Session management
- HTTPS/SSL
- CSRF protection
- Input sanitization
- Rate limiting

## Performance

- Lightweight and fast
- No external dependencies for UI
- Minimal CSS (single file)
- Pure JavaScript (no frameworks)
- Efficient REST API

## Browser Compatibility

- Chrome/Edge (latest)
- Firefox (latest)
- Safari (latest)
- Mobile browsers

## License

This project is provided for educational purposes.

## Support

For issues or questions, refer to the main [README.md](../README.md)

## Version

- **Version**: 1.0.0
- **Date**: February 2026
- **Status**: Complete and Tested
