package com.java.guide.enums;

public enum PinType {

    REGISTER(100000, "ע��ʹ��"),
    FORGET_PASSWORD(100001, "��������ʹ��"),
    UPDATE_PHONE_NUMBER(100002, "�����ֻ�����ʹ��");

    private final int code;
    private final String message;

    PinType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PinType{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}