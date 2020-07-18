package com.mask.mahjong;

/**
 * 花色枚举
 * @author Mask
 */

public enum SuitEnum {
    TONG("tong",1),TIAO("tiao",2),WAN("wan",3);

    private String suit;

    private int number;

    SuitEnum(String suit, int number) {
        this.suit = suit;
        this.number = number;
    }

    public String getSuit(){
        return this.suit;
    }

    public int getNumber(){
        return this.number;
    }
}
