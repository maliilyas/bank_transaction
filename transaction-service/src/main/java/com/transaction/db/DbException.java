package com.transaction.db;

public class DbException extends RuntimeException {
  public DbException(final String msg, final Throwable t) {
    super(msg, t);
  }

  public DbException(final String msg) {
    super(msg);
  }

}
