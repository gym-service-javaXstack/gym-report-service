package com.example.gymreport.util.aspect;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "NOSQL_TYPE", havingValue = "mongo", matchIfMissing = true)
public class TransactionIdHolder {
    private static final ThreadLocal<Boolean> tempIdContext = new ThreadLocal<>();

    public static boolean isTransactionIdSetInContext() {
        return tempIdContext.get() != null && tempIdContext.get();
    }

    public static void setTransactionIdInContext(Boolean isSet) {
        tempIdContext.set(isSet);
    }

    public static void clearTransactionIdFromContext() {
        tempIdContext.remove();
    }
}
