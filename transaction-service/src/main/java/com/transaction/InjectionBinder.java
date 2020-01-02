package com.transaction;

import com.transaction.service.ValidationTransactionService;
import com.transaction.service.impl.ValidationTransactionImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class InjectionBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(ValidationTransactionImpl.class).to(ValidationTransactionService.class);
  }
}
