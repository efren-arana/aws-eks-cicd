package com.paymentchain.billing.enums;

public enum ExceptionType {
    BUSINESS_CORE,
    BUSINESS_RULE,    
    TECHNICAL,
    ;

    public static ExceptionType fromValue(String v) {
        return valueOf(v);
    }

    public String value() {
        return name();
    }
}
