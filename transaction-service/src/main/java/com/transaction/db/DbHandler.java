package com.transaction.db;

import static com.transaction.configuration.TransactionProperties.getDbDriver;
import static com.transaction.configuration.TransactionProperties.getDbPassword;
import static com.transaction.configuration.TransactionProperties.getDbUsername;
import static com.transaction.configuration.TransactionProperties.getHikariAutocommit;
import static com.transaction.configuration.TransactionProperties.getHikariPoolMaxSize;
import static com.transaction.configuration.TransactionProperties.getJdbcUrl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class DbHandler {

  private static DSLContext dslContext;
  private static DataSource dbConnection;
  private static final DbHandler instance;

  static {
    try {
      instance = new DbHandler();
    } catch (Exception e) {
      throw new DbException("Error happened while initiating db context.", e);
    }
  }

  public DbHandler() {
    dbConnection = getConnection();
    dslContext = DSL.using(dbConnection, SQLDialect.H2);
  }

  public static DbHandler getInstance() {
    return instance;
  }


  private DataSource getConnection() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(getJdbcUrl());
    config.setDriverClassName(getDbDriver());
    config.setUsername(getDbUsername());
    config.setPassword(getDbPassword());
    config.setAutoCommit(getHikariAutocommit());
    config.setMaximumPoolSize(getHikariPoolMaxSize());
    return new HikariDataSource(config);
  }

  public DSLContext dbContext() {
    return dslContext;
  }
  public DataSource dbConnection(){return dbConnection;}

}

