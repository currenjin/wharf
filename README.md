# Wharf
![🚢](https://github.com/user-attachments/assets/80b6e60d-9b8c-42c5-b195-8a9af46d1077)

![Author](https://img.shields.io/badge/author-currenjin-5c7cfa)
![GitHub License](https://img.shields.io/github/license/currenjin/wharf)
![GitHub Release](https://img.shields.io/github/v/release/currenjin/wharf)


## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Quick Start](#quick-start)
- [Supported Project Types](#supported-project-types)
- [Generated Files](#generated-files)

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
### Install with Script (Recommended)
```bash
# Install wharf (will ask for sudo password)
curl -fsSL https://raw.githubusercontent.com/currenjin/wharf/main/install.sh | sudo sh

# Move to your project directory and initialize
cd /path/to/your/project
wharf init
```

### Manual Installation
```bash
# Clone the project
git clone https://github.com/currenjin/wharf.git

# Build
cd wharf
./gradlew clean build

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
    build: .
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
We welcome contributions! Please feel free to submit a Pull Request.

## License
MIT (c) currenjin
