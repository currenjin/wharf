# Wharf
🚢 A docker environment generator that just works.

## Overview
Wharf automatically analyzes your project and generates Docker configuration files. No manual setup, no complex configurations - just run one command and get your Docker environment ready.

## Features
### 🔍 Smart Detection
- Automatically detects your project type
- Currently supports Spring Boot and Node.js projects
- Identifies project structure and dependencies

### 📦 Auto Generation
- Generates optimized docker-compose.yml
- Creates Dockerfile with best practices
- Sets up proper environments for development

### ⚡ Zero Configuration
- No additional setup required
- Sensible defaults for all configurations
- Works out of the box

## Quick Start
### Download the latest release:
```bash
# Clone the project
git clone https://github.com/currenjin/wharf.git

# Build
cd wharf
./gradlew clean build
```

### Initialize Docker configuration in your project:
```bash
# Move to your project directory
cd /path/to/your/project

# Run Wharf
java -jar /path/to/wharf.jar init
```

### That's it! Wharf will:
1. Analyze your project
2. Generate Docker configurations
3. Create required files (docker-compose.yml, Dockerfile)

## Supported Project Types
### Spring Boot
- Automatically detects Spring Boot projects
- Configures Java runtime environment
- Sets up proper Spring profiles

### Node.js
- Detects Node.js projects via package.json
- Configures Node.js runtime
- Sets up npm for dependency management

## Generated Files
### docker-compose.yml
```yaml
version: '3.8'
services:
  app:
    image: [appropriate-image]
    ports:
      - [appropriate-port]
    environment:
      - [appropriate-env-vars]
```

### Dockerfile
#### Spring Boot:
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Node.js:
```dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
CMD ["npm", "start"]
```

## Contributing
We welcome contributions! Please feel free to submit a Pull Request.

## License
MIT (c) currenjin