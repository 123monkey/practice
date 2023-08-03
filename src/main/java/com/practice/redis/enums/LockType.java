package com.practice.redis.enums;

/**
 * /锁类信息：普通锁、读锁、写锁
 */
public enum  LockType {

    LOCK("Lock"),
    READ_LOCK("ReadLock"),
    WRITE_LOCK("WriteLock");

    private String value;

    private LockType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static LockType fromString(String value) {
        for (LockType type : LockType.values()) {
            if (type.getValue().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }

        throw new IllegalArgumentException("Mismatched type with value=" + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
