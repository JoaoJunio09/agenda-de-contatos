package com.joaojunio.contact.model.enums;

public enum UserStatus {

    ACTIVE(1),
    INACTIVE(2),
    BANNED(3);

    private int code;

    private UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UserStatus fromCode(int code) {
        for (UserStatus value : UserStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid UserStatus code : " + code);
    }
}
