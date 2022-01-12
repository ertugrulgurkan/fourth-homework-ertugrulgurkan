package com.ertugrul.spring.enums;

public enum DebtType {
    LATE_FEE("LATE_FEE"),
    NORMAL("NORMAL");

    private final String type;

    DebtType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
