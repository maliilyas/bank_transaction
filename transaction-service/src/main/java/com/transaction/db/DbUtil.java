package com.transaction.db;

import static com.customer.portal.db.tables.CustomerAccount.CUSTOMER_ACCOUNT;
import static com.customer.portal.db.tables.CustomerTransaction.CUSTOMER_TRANSACTION;

import com.customer.portal.db.tables.records.CustomerTransactionRecord;
import com.transaction.model.CreateTransactionRequest;
import java.util.List;
import org.jooq.Record;

public class DbUtil {

  public enum TransactionStatus {
    DONE,
    DENIED,
  }

  public static List<Record> fetchCustomer(final String username, final String password,
      final String iban) {
    return DbHandler.getInstance().dslContext().select().from(CUSTOMER_ACCOUNT)
        .where(
            CUSTOMER_ACCOUNT.USER_NAME.eq(username)
                .and(CUSTOMER_ACCOUNT.PASSWORD.eq(password))
                .and(CUSTOMER_ACCOUNT.IBAN.eq(iban))).fetch();

  }

  public static long createTransaction(final CreateTransactionRequest transaction) {
    CustomerTransactionRecord customerTransaction = DbHandler.getInstance().dslContext()
        .newRecord(CUSTOMER_TRANSACTION);
    customerTransaction.setAmount(transaction.getAmount());
    customerTransaction.setCreationTime(System.currentTimeMillis());
    customerTransaction.setCustomerFromIban(transaction.getFromIban());
    customerTransaction.setCustomerToIban(transaction.getToIban());
    customerTransaction.setTransactionMessage(transaction.getTransactionMessage());
    customerTransaction.setTransactionReference(transaction.getTransactionReference());
    customerTransaction.setTransactionStatus(TransactionStatus.DONE.name());
    customerTransaction.store();
    return customerTransaction.getId();
  }

}
