package com.transaction.db;

import static com.transaction.configuration.TransactionServiceProperties.getDbDriver;
import static com.transaction.configuration.TransactionServiceProperties.getDbPassword;
import static com.transaction.configuration.TransactionServiceProperties.getDbUsername;
import static com.transaction.configuration.TransactionServiceProperties.getHikariNonTransactionAutocommit;
import static com.transaction.configuration.TransactionServiceProperties.getHikariNonTransactionPoolMaxSize;
import static com.transaction.configuration.TransactionServiceProperties.getJdbcUrl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class DbNonTransactionalHandler {

  private static DSLContext dslContext;
  private static final DbNonTransactionalHandler instance;

  static {
    try {
      instance = new DbNonTransactionalHandler();
    } catch (Exception e) {
      throw new DbException("Error happened while initiating db context.", e);
    }
  }

  public DbNonTransactionalHandler() {
    dslContext = DSL.using(getConnection(), SQLDialect.H2);
  }

  public static DbNonTransactionalHandler getInstance() {
    return instance;
  }


  private DataSource getConnection() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(getJdbcUrl());
    config.setDriverClassName(getDbDriver());
    config.setUsername(getDbUsername());
    config.setPassword(getDbPassword());
    config.setAutoCommit(getHikariNonTransactionAutocommit());
    config.setMaximumPoolSize(getHikariNonTransactionPoolMaxSize());
    return new HikariDataSource(config);
  }

  public DSLContext dbContext() {
    return dslContext;
  }
}

