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
     * @return
     */
    public boolean huJudgment(List<Integer> cards, int laizi) {

        return isHu(cards, laizi);
    }


    /**
     * @param cards 手牌（除赖子）
     * @param laizi 赖子数
     * @return
     */
    public static boolean isHu(List<Integer> cards, int laizi) {
        if ((cards.size() + laizi) % 3 != 2) {
            //手牌数不是2、5、8、11、14不能胡牌（相公）
            return false;
        }
        //先排序，再进行胡牌判定
        Collections.sort(cards);

        for (int i = 0; i < cards.size(); i++) {
            //和上一张是同样的牌时跳过，避免重复判定
            if (i > 0 && cards.get(i).equals(cards.get(i - 1))) {
                continue;
            }
            //先找对子、或者用赖子拼成的对子
            if (i + 1 < cards.size() && cards.get(i).equals(cards.get(i + 1)) || laizi > 0) {
                List<Integer> keOrShunCards = new ArrayList<Integer>(cards);
                int keOrShunLaizi = laizi;
                keOrShunCards.remove(i);
                //从当前数组中把一对将牌移除，判断剩余的牌是否都能成刻子或顺子
                if (keOrShunCards.size() > 0 && keOrShunCards.get(i).equals(cards.get(i))) {
                    keOrShunCards.remove(i);
                } else {
                    keOrShunLaizi--;
                }
                if (isKeOrShun(keOrShunCards, keOrShunLaizi)) {
                    return true;
                }
            }
        }
        //赖子作为将的情况
        if (laizi >= 2 && isKeOrShun(cards, laizi - 2)) {
            return true;
        }
        return false;
    }

    /**
     * 递归判定牌组是否能组成顺子或者刻子
     *
     * @param cards
     * @param laizi
     * @return
     */
    public static boolean isKeOrShun(List<Integer> cards, int laizi) {
        if (cards.size() == 0) {
            return true;
        }
        //若第一张牌是顺子中的一张
        for (int first = cards.get(0) - 2; first <= cards.get(0); first++) {
            //剪枝：顺子第一张牌不会大于七点，无赖子的情况下顺子第一张牌只能用手上的牌
            if (first % 10 > 7 || (laizi == 0 && first < cards.get(0))) {
                continue;
            }
            int shunCount = 0;
            for (int i = 0; i < 3; i++) {
                if (cards.indexOf(first + i) >= 0) {
                    shunCount++;
                }
            }
            //找到包含第一张牌的顺子
            if (shunCount == 3 || shunCount + laizi >= 3) {
                List<Integer> keOrShunCards = new ArrayList<Integer>(cards);
                int keOrShunLaizi = laizi;
                for (int i = 0; i < 3; i++) {
                    int deletePos = keOrShunCards.indexOf(first + i);
                    if (deletePos >= 0) {
                        keOrShunCards.remove(deletePos);
                    } else {
                        keOrShunLaizi--;
                    }
                }
                //剩下的牌是否成顺子或者刻子
                if (isKeOrShun(keOrShunCards, keOrShunLaizi)) {
                    return true;
                }
            }
        }
        //若第一张是刻子中的一张
        int keziCount = 1;
        int keziCard = cards.get(0);
        if (cards.get(1) == keziCard) {
            keziCount++;
        }
        if (cards.get(2) == keziCard) {
            keziCount++;
        }
        if (keziCount == 3 || keziCount + laizi >= 3) {
            List<Integer> keOrShunCards = new ArrayList<Integer>(cards);
            int keOrShunLaizi = laizi;
            for (int i = 0; i < 3; i++) {
                int deletePos = keOrShunCards.indexOf(keziCard);
                if (deletePos >= 0) {
                    keOrShunCards.remove(deletePos);
                } else {
                    keOrShunLaizi--;
                }
            }
            if (isKeOrShun(keOrShunCards, keOrShunLaizi)) {
                return true;
            }
        }
        return false;
    }
}
