/**
 * NutriSci Application Module
 * Includes Database and Jfreechart Modules for SQL Server operations
 */
module NutriSci_3311 {
  
  	requires org.jfree.jfreechart;
	  requires java.desktop;
    // Required modules for SQL operations
    requires java.sql;
    requires java.base;
    
    // Export database module packages for other modules to use
    exports com.nutrisci.database;
    exports com.nutrisci.database.config;
    exports com.nutrisci.database.dao;
    exports com.nutrisci.database.dao.impl;
    exports com.nutrisci.database.dto;
    exports com.nutrisci.database.exceptions;

}