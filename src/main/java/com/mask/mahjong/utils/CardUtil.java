package com.mask.mahjong.utils;

import java.util.List;

/**
 * @author Mask
 */
public class CardUtil {
    public static String toString(List<Integer> cards, int laizi) {
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < cards.size();i++) {
            sb.append(cardName(cards.get(i)));
            sb.append("|");
        }
        for (int i =0; i < laizi; i++) {
            sb.append("赖子|");
        }
        return sb.toString();
    }

    public static String cardCode(int cardNum) {
        return cardPoint(cardNum,false) + "_" + cardSuit(cardNum,false);
    }

    public static String cardName(int cardNum) {
        return cardPoint(cardNum) + cardSuit(cardNum);
    }

    public static String cardPoint(int cardNum) {
        return cardPoint(cardNum,true);
    }

    public static String cardPoint(int cardNum,boolean name) {
        String point = null;
        int pointNum = cardNum % 10;
        switch (pointNum) {
            case 1:
                point = name?"一":"one";
                break;
            case 2:
                point = name?"二":"two";
                break;
            case 3:
                point = name?"三":"three";
                break;
            case 4:
                point = name?"四":"four";
                break;
            case 5:
                point = name?"五":"five";
                break;
            case 6:
                point = name?"六":"six";
                break;
            case 7:
                point = name?"七":"seven";
                break;
            case 8:
                point = name?"八":"eight";
                break;
            case 9:
                point = name?"九":"nine";
                break;
            default:
                point = name?"错误点数":"error_point";
                break;
        }
        return point;
    }

    public static String cardSuit(int cardNum) {
        return cardSuit(cardNum,true);
    }

    public static String cardSuit(int cardNum,boolean name) {
        String suit = null;
        int suitNum = cardNum / 10;

        switch (suitNum){
            case 0:
                suit = name?"筒":"tong";
                break;
            case 1:
                suit = name?"条":"tiao";
                break;
            case 2:
                suit = name?"万":"wan";
                break;
            default:
                suit = name?"错误花色":"error_suit";
                break;
        }
        return suit;
    }
}
