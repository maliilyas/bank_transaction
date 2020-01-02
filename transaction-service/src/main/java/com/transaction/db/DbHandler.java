package com.transaction.db;

import static com.transaction.configuration.TransactionServiceProperties.getDbDriver;
import static com.transaction.configuration.TransactionServiceProperties.getDbPassword;
import static com.transaction.configuration.TransactionServiceProperties.getDbUsername;
import static com.transaction.configuration.TransactionServiceProperties.getHikariAutocommit;
import static com.transaction.configuration.TransactionServiceProperties.getHikariPoolMaxSize;
import static com.transaction.configuration.TransactionServiceProperties.getJdbcUrl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

public class DbHandler {

  private static DSLContext dslCtx;

  private static final DbHandler instance;

  static {
    try {
      instance = new DbHandler();
    } catch (Exception e) {
      throw new DbException("Error happened while initiating db context.", e);
    }
  }

  public DbHandler() {
    dslCtx = DSL.using(getConnection(), SQLDialect.H2,
        new Settings().withExecuteWithOptimisticLocking(true));
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

  public DSLContext dslContext() {
    return dslCtx;
  }
}

