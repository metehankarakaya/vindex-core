package com.metehan.vindexcore.common.exception;

public class RecurringTransactionImmutableException extends RuntimeException{

    public RecurringTransactionImmutableException(String id) {
        super("Transaction with id " + id + " was generated from a recurring template and cannot be edited or deleted directly");
    }

}
