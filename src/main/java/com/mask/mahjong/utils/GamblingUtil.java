package com.mask.mahjong.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 牌局工具类
 *
 * @author Mask
 */
public class GamblingUtil {

    /**
     * 发牌
     */
    public void deal() {

    }

    /**
     * 判定是否胡牌
     * 1~9表示一到九筒
     * 11~19表示一到九条
     * 21~29表示一到九万
     * 不带计算赖子判定一次是否是【黑摸】，再从手牌分开赖子和普通牌判定是否胡牌
     *
     * @return
     */
    public static String huJudgment(List<Integer> cards, int laizi) {
        String judgementResult = null;
        int totalCards = cards.size() + laizi;
        if (totalCards % 3 != 2) {
            judgementResult = "XIANGGONG";
        } else if (jiangJudgment(cards, laizi)) {
            Collections.sort(cards);
            judgementResult = "HU";
        }
        return judgementResult;
    }

    /**
     * 先判断有没有【将】，把牌中的【将】剔除再拿剩下的牌进行下一步判断
     *
     * @param cards
     * @param laizi
     * @return
     */
    public static boolean jiangJudgment(List<Integer> cards, int laizi) {
        for (int i = 0; i < cards.size(); i++) {
            //和上一张是同样的牌时跳过，避免重复判定
            if (i > 0 && cards.get(i).equals(cards.get(i - 1))) {
                continue;
            }
            //先找【将】、或者用赖子拼成的【将】
            if (i < cards.size() || laizi > 0) {
                //剩余的牌组
                List<Integer> remainingCards = new ArrayList<Integer>(cards);
                //剩余的赖子数
                int remainingLaizi = laizi;
                System.out.println("-------------------------------------------------------");
                System.out.println("当前牌组：");
                System.out.println(CardUtil.toString(remainingCards, laizi));
                int currentCard = remainingCards.get(i);
                System.out.println("移除牌：" + CardUtil.cardName(currentCard));
                remainingCards.remove(i);
                int sameCardIndex = remainingCards.indexOf(currentCard);
                if (sameCardIndex > -1) {
                    System.out.println("移除牌：" + CardUtil.cardName(remainingCards.get(sameCardIndex)));
                    remainingCards.remove(sameCardIndex);
                } else {
                    System.out.println("移除牌：赖子");
                    remainingLaizi--;
                }
                System.out.println("移除【将】后的牌组：");
                System.out.println(CardUtil.toString(remainingCards, remainingLaizi));
                System.out.println("-------------------------------------------------------");
                if (shunJudgment(remainingCards, remainingLaizi) || keJudgment(remainingCards, remainingLaizi)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断牌组是否为【顺子】，移除【顺子】后判断剩余牌组是否为【刻子】
     *
     * @param cards
     * @param laizi
     * @return
     */
    public static boolean shunJudgment(List<Integer> cards, int laizi) {
        if (cards.size() == 0) {
            return true;
        }
        //若牌组的第一张牌是顺子中的一张
        for (int first = cards.get(0) - 2; first <= cards.get(0); first++) {
            //顺子中的第一张牌不会点数大于7，没有赖子的情况下，顺子中的第一张牌不会比牌组里第一张牌小
            if (first % 10 > 7 || (laizi == 0 && first < cards.get(0))) {
                continue;
            }
            int shunCount = 0;
            for (int i = 0; i < 3; i++) {
                if (cards.indexOf(first + i) >= 0) {
                    shunCount++;
                }
            }
            //找到一个顺子
            if (shunCount == 3 || shunCount + laizi >= 3) {
                //剩余的牌组
                List<Integer> remainingCards = new ArrayList<Integer>(cards);
                //剩余的赖子数
                int remainingLaizi = laizi;
                System.out.println("-------------------------------------------------------");
                System.out.println("当前牌组：");
                System.out.println(CardUtil.toString(remainingCards, laizi));
                //从剩余牌组中移除顺子成员
                for (int i = 0; i < 3; i++) {
                    //顺子成员所在位置下标
                    int deletePos = remainingCards.indexOf(first + i);
                    if (deletePos >= 0) {
                        System.out.println("移除牌：" + CardUtil.cardName(remainingCards.get(deletePos)));
                        remainingCards.remove(deletePos);
                    } else {
                        System.out.println("移除牌：赖子");
                        remainingLaizi--;
                    }
                }
                System.out.println("移除【顺子】后的牌组：");
                System.out.println(CardUtil.toString(remainingCards, remainingLaizi));
                System.out.println("-------------------------------------------------------");
                //剩下的牌组进行刻子判断
                if (shunJudgment(remainingCards, remainingLaizi) || keJudgment(remainingCards, remainingLaizi)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param cards
     * @param laizi
     * @return
     */
    public static boolean keJudgment(List<Integer> cards, int laizi) {
        if (cards.size() == 0) {
            return true;
        }
        //若牌组的第一张牌是刻子中的一张
        int keziCount = 1;
        int keziCard = cards.get(0);
        if (cards.get(1) == keziCard) {
            keziCount++;
        }
        if (cards.get(2) == keziCard) {
            keziCount++;
        }
        if (keziCount == 3 || keziCount + laizi >= 3) {
            //牌组剩余的牌
            List<Integer> remainingCards = new ArrayList<Integer>(cards);
            //剩余的赖子数
            int remainingLaizi = laizi;
            System.out.println("-------------------------------------------------------");
            System.out.println("当前牌组：");
            System.out.println(CardUtil.toString(remainingCards, laizi));
            //从剩余牌组中移除刻子成员
            for (int i = 0; i < 3; i++) {
                //刻子成员所在的位置下标
                int deletePos = remainingCards.indexOf(keziCard);
                if (deletePos >= 0) {
                    System.out.println("移除牌：" + remainingCards.get(deletePos));
                    remainingCards.remove(deletePos);
                } else {
                    System.out.println("移除牌：赖子");
                    remainingLaizi--;
                }
            }
            System.out.println("移除【刻子】后的牌组：");
            System.out.println(CardUtil.toString(remainingCards, remainingLaizi));
            System.out.println("-------------------------------------------------------");
            if (shunJudgment(remainingCards, remainingLaizi) || keJudgment(remainingCards, remainingLaizi)) {
                return true;
            }
        }
        return false;
    }
}
