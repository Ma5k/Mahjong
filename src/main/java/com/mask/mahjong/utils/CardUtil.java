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

    public static String cardName(int cardNum) {
        StringBuilder sb = new StringBuilder();
        int pointNum = cardNum % 10;
        int suitNum = cardNum / 10;
        switch (pointNum) {
            case 1:
                sb.append("一");
                break;
            case 2:
                sb.append("二");
                break;
            case 3:
                sb.append("三");
                break;
            case 4:
                sb.append("四");
                break;
            case 5:
                sb.append("五");
                break;
            case 6:
                sb.append("六");
                break;
            case 7:
                sb.append("七");
                break;
            case 8:
                sb.append("八");
                break;
            case 9:
                sb.append("九");
                break;
            default:
                sb.append("错误点数");
                break;
        }
        switch (suitNum){
            case 0:
                sb.append("筒");
                break;
            case 1:
                sb.append("条");
                break;
            case 2:
                sb.append("万");
                break;
            default:
                sb.append("错误花色");
                break;
        }
        return sb.toString();
    }
}
