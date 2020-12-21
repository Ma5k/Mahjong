package com.mask.mahjong.utils;

import com.mask.mahjong.pojo.Card;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Comparator;

public class CardComparator implements Comparator<Card> {
    @Override
    public int compare(Card card1, Card card2) {
        if(card1.getSuit().equals(card2.getSuit())) {
            return card2.getPoint() - card1.getPoint();
        } else {
            int card1SuitNum = SuitEnum.getNum(card1.getSuit());
            int card2SuitNum = SuitEnum.getNum(card2.getSuit());
            return card2SuitNum - card1SuitNum;
        }
    }
}
