package com.transaction.configuration;

import java.io.IOException;
import java.util.Properties;

public class TransactionProperties {
  private static final String CONGIG_PROP_FILENAME = "/config.properties";
  private static Properties properties;

  private TransactionProperties() {
    // do nothing
  }
  /**
   * Getting the properties from config.properties for connection pool and database
   */
  private static Properties properties() {
    if (properties == null) {
      try {
        properties = new Properties();
        properties.load(TransactionProperties.class.getResourceAsStream(CONGIG_PROP_FILENAME));
      } catch (IOException ex) {
        throw new ConfigurationException("Error fetching properties.", ex);
      }
    }
    return properties;
  }

  public static String getJdbcUrl() {
    return properties().getProperty("db.url");
  }

  public static String getDbDriver() {
    return properties().getProperty("db.driver");
  }

  public static String getDbUsername() {
    return properties().getProperty("db.username");
  }

  public static String getDbPassword() {
    return properties().getProperty("db.password");
  }

  public static String getJooqSqlDialect() {
    return properties().getProperty("jooq.sql.dialect");
  }

  public static int getHikariPoolMaxSize() {
    return Integer.parseInt(properties().getProperty("db.pool.maxsize"));
  }

  public static boolean getHikariAutocommit() {
    return Boolean.parseBoolean(properties().getProperty("db.pool.autocommit"));
  }

}
