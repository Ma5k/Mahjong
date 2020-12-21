package com.mask.mahjong.utils;

public enum SuitEnum {
    TONG("tong", 1),
    TIAO("tiao", 2),
    WAN("wan", 3);

    private String suit;

    private int num;

    private SuitEnum(String suit, int num) {
        this.suit = suit;
        this.num = num;
    }

    public static int getNum(String suit) {
        SuitEnum[] suitEnums = values();
        for (SuitEnum suitEnum : suitEnums) {
            if (suitEnum.suit().equals(suit)) {
                return suitEnum.num();
            }
        }
        return 0;
    }

    public static String getSuit(int num) {
        SuitEnum[] suitEnums = values();
        for (SuitEnum suitEnum : suitEnums) {
            if (suitEnum.num() == num) {
                return suitEnum.suit();
            }
        }
        return null;
    }

    private String suit() {
        return this.suit;
    }

    private int num() {
        return this.num;
    }
}
