# Spring Boot Login Web Application - Quick Start

## One-Minute Setup

### Windows
```batch
cd webapp
build.bat
run.bat
```

Open http://localhost:8080

### Linux/Mac
```bash
cd webapp
chmod +x build.sh run.sh
./build.sh
./run.sh
```

Open http://localhost:8080

## Test Credentials

```
Username: admin
Password: admin123
```

## What You Get

✅ Full-featured web application
✅ Responsive design (mobile-friendly)
✅ Login & Registration
✅ User Dashboard
✅ Password Management
✅ Modern UI
✅ REST API

## Structure

```
webapp/
├── pom.xml              # Maven dependencies
├── src/
│   ├── main/java/       # Java source code
│   └── main/resources/  # HTML, CSS, config
├── build.bat/sh         # Build scripts
├── run.bat/sh           # Run scripts
└── dev.bat/sh           # Development mode
```

## Pages

- Home: http://localhost:8080/
- Login: http://localhost:8080/login
- Register: http://localhost:8080/register
- Dashboard: http://localhost:8080/dashboard (login required)

## API Endpoints

- `POST /api/login` - Login user
- `POST /api/register` - Register user
- `GET /api/profile` - Get user profile
- `POST /api/change-password` - Change password
- `POST /api/logout` - Logout user

## Development

For hot-reload during development:

**Windows**: `dev.bat`
**Linux/Mac**: `./dev.sh`

This runs with Spring DevTools for auto-reload.

## Troubleshooting

**Build fails**: Install Maven from maven.apache.org

**Port in use**: Change `server.port` in `src/main/resources/application.properties`

**App won't start**: Check Java version with `java -version`

## See Also

- [README.md](README.md) - Full documentation
- [../README.md](../README.md) - Console version documentation
