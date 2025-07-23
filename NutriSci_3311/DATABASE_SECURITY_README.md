# NutriSci Database Security Setup

## 🔐 Security Overview

This project uses secure database configuration management to protect sensitive credentials from being committed to version control.

## 📋 Initial Setup Instructions

### 1. Configure Database Properties

1. Navigate to `src/config/`
2. Copy `database.properties.template` to `database.properties`
3. Update `database.properties` with your actual database credentials:

```properties
# Update these values with your actual database settings
db.url=jdbc:sqlserver://your-server:1433;databaseName=YourDatabase;trustServerCertificate=true
db.username=your_username
db.password=your_password
```

### 2. Verify Git Ignore

The `.gitignore` file has been configured to automatically exclude:
- `src/config/database.properties` (contains sensitive credentials)
- `config/database.properties` (alternative location)
- All backup files (`*.properties.backup`, `*.properties.bak`)
- Environment-specific configs (`database-dev.properties`, etc.)

## 🚨 Security Warnings

**NEVER commit the following files:**
- `database.properties` (contains real credentials)
- Any file with actual database passwords
- Backup files with `.backup` or `.bak` extensions

**ALWAYS commit:**
- `database.properties.template` (safe template without credentials)
- Configuration code changes in `DatabaseConfig.java`

## 🔧 Configuration Management

### DatabaseConfig Class
- Located in `com.nutrisci.database.config.DatabaseConfig`
- Singleton pattern for centralized configuration
- Loads properties from `/config/database.properties`
- Provides secure access to database settings

### DatabaseConnectionManager Updates
- No longer uses hardcoded credentials
- Automatically loads configuration from properties file
- Maintains same public API for existing code

## 🧪 Testing Database Connection

You can verify your database configuration is working by:

1. Ensuring `database.properties` exists and has correct values
2. Running any DAO test that attempts database connection
3. Check for proper error messages if configuration is missing

## 🔄 Environment-Specific Configurations

For different environments, you can create:
- `database-dev.properties` (development)
- `database-test.properties` (testing)
- `database-prod.properties` (production)

All these files are automatically ignored by Git.

## 🛠️ Troubleshooting

### "Database configuration file not found" Error
- Ensure `database.properties` exists in `src/config/`
- Verify the file is not empty
- Check that all required properties are set

### "Property not found or empty" Error
- Verify all required properties in `database.properties`:
  - `db.url`
  - `db.username`
  - `db.password`
  - `db.driver`

### Connection Issues
- Validate database URL format
- Confirm database server is running
- Check username/password credentials
- Verify network connectivity to database server