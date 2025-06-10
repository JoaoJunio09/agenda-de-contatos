package com.joaojunio.contact.model.enums;

public enum UserAdmin {

    ALLOWED(1),
    NOT_ALLOWED(2);

    private int code;

    private UserAdmin(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UserAdmin fromCode(int code) {
        for (UserAdmin value : UserAdmin.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid UserAdmin code : " + code);
    }
}
