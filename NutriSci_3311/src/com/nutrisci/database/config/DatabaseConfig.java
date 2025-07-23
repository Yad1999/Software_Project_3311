package com.nutrisci.database.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration manager for database properties.
 * Loads database configuration from properties file and provides secure access.
 */
public class DatabaseConfig {
    
    private static final String CONFIG_FILE = "/config/database.properties";
    private static Properties properties;
    private static DatabaseConfig instance;
    
    private DatabaseConfig() {
        loadProperties();
    }
    
    /**
     * Gets the singleton instance of DatabaseConfig.
     * @return DatabaseConfig instance
     */
    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }
    
    /**
     * Loads database properties from configuration file.
     */
    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("Database configuration file not found: " + CONFIG_FILE);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }
    
    /**
     * Gets database URL from configuration.
     * @return Database URL
     */
    public String getDatabaseUrl() {
        return getProperty("db.url");
    }
    
    /**
     * Gets database username from configuration.
     * @return Database username
     */
    public String getDatabaseUsername() {
        return getProperty("db.username");
    }
    
    /**
     * Gets database password from configuration.
     * @return Database password
     */
    public String getDatabasePassword() {
        return getProperty("db.password");
    }
    
    /**
     * Gets database driver class name from configuration.
     * @return Database driver class name
     */
    public String getDatabaseDriver() {
        return getProperty("db.driver");
    }
    
    /**
     * Gets connection pool maximum size from configuration.
     * @return Maximum pool size
     */
    public int getPoolMaxSize() {
        return Integer.parseInt(getProperty("db.pool.maxSize", "10"));
    }
    
    /**
     * Gets connection pool minimum size from configuration.
     * @return Minimum pool size
     */
    public int getPoolMinSize() {
        return Integer.parseInt(getProperty("db.pool.minSize", "2"));
    }
    
    /**
     * Gets connection timeout from configuration.
     * @return Connection timeout in milliseconds
     */
    public int getConnectionTimeout() {
        return Integer.parseInt(getProperty("db.pool.timeout", "30000"));
    }
    
    /**
     * Gets a property value with no default.
     * @param key Property key
     * @return Property value
     * @throws RuntimeException if property is not found
     */
    private String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Database configuration property not found or empty: " + key);
        }
        return value.trim();
    }
    
    /**
     * Gets a property value with a default fallback.
     * @param key Property key
     * @param defaultValue Default value if property is not found
     * @return Property value or default
     */
    private String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        return value.trim();
    }
}