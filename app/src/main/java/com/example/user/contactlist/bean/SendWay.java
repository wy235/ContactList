package com.example.user.contactlist.bean;

public enum SendWay {
    SENDWAY_EMAIL, // email
    SENDWAY_SMS, // 短信
    SENDWAY_PHONE, // 电话
    SENDWAY_MAIL, // 邮寄
    SENDWAY_WEIXIN, // 微信
    SENDWAY_GALA,	// 旮旯
    SENDWAY_QRCODE;	// 二维码

    public static int toInt(SendWay type) {
        switch (type) {
            case SENDWAY_EMAIL:
                return 0;
            case SENDWAY_SMS:
                return 1;
            case SENDWAY_PHONE:
                return 2;
            case SENDWAY_MAIL:
                return 3;
            case SENDWAY_WEIXIN:
                return 4;
            case SENDWAY_GALA:
                return 5;
            case SENDWAY_QRCODE:
                return 6;
            default:
                return -1;
        }
    }

    public static String toString(SendWay type) {
        switch (type) {
            case SENDWAY_EMAIL:
                return "0";
            case SENDWAY_SMS:
                return "1";
            case SENDWAY_PHONE:
                return "2";
            case SENDWAY_MAIL:
                return "3";
            case SENDWAY_WEIXIN:
                return "4";
            case SENDWAY_GALA:
                return "5";
            case SENDWAY_QRCODE:
                return "6";
            default:
                return "";
        }
    }

    public static SendWay fromInt(int type) {
        switch (type) {
            case 0:
                return SENDWAY_EMAIL;
            case 1:
                return SENDWAY_SMS;
            case 2:
                return SENDWAY_PHONE;
            case 3:
                return SENDWAY_MAIL;
            case 4:
                return SENDWAY_WEIXIN;
            case 5:
                return SENDWAY_GALA;
            case 6:
                return SENDWAY_QRCODE;
            default:
                return null;
        }
    }

    public static SendWay fromString(String type) {
        switch (type) {
            case "0":
                return SENDWAY_EMAIL;
            case "1":
                return SENDWAY_SMS;
            case "2":
                return SENDWAY_PHONE;
            case "3":
                return SENDWAY_MAIL;
            case "4":
                return SENDWAY_WEIXIN;
            case "5":
                return SENDWAY_GALA;
            case "6":
                return SENDWAY_QRCODE;
            default:
                return null;
        }
    }
}
