package com.metehan.vindexcore.common.exception;

public class RecurringNotFoundException extends RuntimeException{

    public RecurringNotFoundException(String id) {
        super("Recurring not found with id: " + id);
    }

}
