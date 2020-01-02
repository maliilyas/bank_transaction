package com.transaction.db;

import static com.customer.portal.db.tables.CustomerAccount.CUSTOMER_ACCOUNT;
import static com.customer.portal.db.tables.CustomerTransaction.CUSTOMER_TRANSACTION;

import com.transaction.db.DbUtil.TransactionStatus;
import com.transaction.model.CreateTransactionRequest;
import java.util.Date;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record;

public class TransactionManager {

  /**
   * The transaction Manager for doing a transaction
   * @param transaction the transaction request body.
   * @param transactionId the transaction id.
   */
  public static void completeTransaction(final CreateTransactionRequest transaction,
      final long transactionId) {
    final DSLContext dslCtx = DbHandler.getInstance().dslContext();
    dslCtx.transaction(configuration -> {

      // Fetching the sender customer's account
      List<Record> fromCustomerRecords = dslCtx.select().from(CUSTOMER_ACCOUNT)
          .where(CUSTOMER_ACCOUNT.IBAN.eq(transaction.getFromIban())).fetch();

      if (fromCustomerRecords.isEmpty() || fromCustomerRecords.get(0) == null) {
        updateTransactionStatus(TransactionStatus.DENIED, "Sender Customer does not exist or wrong credentials.", transactionId);
        throw new DbException(
            "Transaction can not follow since sender customer does not exist or wrong credentials are provided.");
      }

      Record fromCustomerRecord = fromCustomerRecords.get(0);

      double fromCustomerBalanceAfterTransaction =
          fromCustomerRecord.get(CUSTOMER_ACCOUNT.BALANCE) - transaction.getAmount();
      if (fromCustomerBalanceAfterTransaction < 0) {
        updateTransactionStatus(TransactionStatus.DENIED, "More Taxes and less salary made Jack a poor guy", transactionId);
        throw new DbException(
            "Money can't buy love but you do not have enough to make this transaction.");
      }

      dslCtx.update(CUSTOMER_ACCOUNT)
          .set(CUSTOMER_ACCOUNT.BALANCE, fromCustomerBalanceAfterTransaction)
          .where(CUSTOMER_ACCOUNT.IBAN.eq(transaction.getFromIban())).execute();

      // Fetching the receiver customer's account
      List<Record> toCustomerRecords = dslCtx.select().from(CUSTOMER_ACCOUNT)
          .where(CUSTOMER_ACCOUNT.IBAN.eq(transaction.getToIban())).fetch();

      if (!toCustomerRecords.isEmpty() || toCustomerRecords.get(0) != null) {
        // Reciever Customer is also the member of Homework Bank
        double toCustomerBalance =
            toCustomerRecords.get(0).get(CUSTOMER_ACCOUNT.BALANCE) + transaction.getAmount();
        dslCtx.update(CUSTOMER_ACCOUNT).set(CUSTOMER_ACCOUNT.BALANCE,
            toCustomerBalance)
            .where(CUSTOMER_ACCOUNT.IBAN.eq(transaction.getToIban())).execute();
      } else {
        // since we do not have any information of Receiver's customer's bank account, we just assume we have topped off the account via other bank
      }
    });
  }

  /**
   * Updating the transaction Status.
   * @param transactionStatus the status of transaction.
   * @param msg the message explaining the transaction's status.
   * @param transactionId the transaction id.
   */
  static void  updateTransactionStatus(final TransactionStatus transactionStatus, final String msg,
      final long transactionId) {
    DbHandler.getInstance().dslContext().update(CUSTOMER_TRANSACTION)
        .set(CUSTOMER_TRANSACTION.TRANSACTION_STATUS, transactionStatus.name())
        .set(CUSTOMER_TRANSACTION.TRANSACTION_MESSAGE, msg)
        .where(CUSTOMER_TRANSACTION.ID.eq(transactionId)).execute();
  }


}
