package com.mask.mahjong.utils;

import com.mask.mahjong.pojo.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 牌局工具类
 *
 * @author Mask
 */
@Slf4j
public class GamblingUtil {

    /**
     *
     * @param players
     * @return
     */
    public static Player getNextPlayer(List<Player> players) {
        Player currentPlayer = players.stream().filter(player -> player.isTurn()).findFirst().get();
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextPlayerIndex = currentPlayerIndex == players.size() - 1?0:currentPlayerIndex+1;
        players.get(currentPlayerIndex).setTurn(false);
        players.get(nextPlayerIndex).setTurn(true);
        return players.get(nextPlayerIndex);
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
    public String huJudgment(List<Integer> cards, int laizi) {
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
    public boolean jiangJudgment(List<Integer> cards, int laizi) {
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
                log.info("-------------------------------------------------------");
                log.info("当前牌组：");
                log.info(CardUtil.toString(remainingCards, laizi));
                int currentCard = remainingCards.get(i);
                log.info("移除牌：" + CardUtil.cardName(currentCard));
                remainingCards.remove(i);
                int sameCardIndex = remainingCards.indexOf(currentCard);
                if (sameCardIndex > -1) {
                    log.info("移除牌：" + CardUtil.cardName(remainingCards.get(sameCardIndex)));
                    remainingCards.remove(sameCardIndex);
                } else {
                    log.info("移除牌：赖子");
                    remainingLaizi--;
                }
                log.info("移除【将】后的牌组：");
                log.info(CardUtil.toString(remainingCards, remainingLaizi));
                log.info("-------------------------------------------------------");
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
    public boolean shunJudgment(List<Integer> cards, int laizi) {
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
                log.info("-------------------------------------------------------");
                log.info("当前牌组：");
                log.info(CardUtil.toString(remainingCards, laizi));
                //从剩余牌组中移除顺子成员
                for (int i = 0; i < 3; i++) {
                    //顺子成员所在位置下标
                    int deletePos = remainingCards.indexOf(first + i);
                    if (deletePos >= 0) {
                        log.info("移除牌：" + CardUtil.cardName(remainingCards.get(deletePos)));
                        remainingCards.remove(deletePos);
                    } else {
                        log.info("移除牌：赖子");
                        remainingLaizi--;
                    }
                }
                log.info("移除【顺子】后的牌组：");
                log.info(CardUtil.toString(remainingCards, remainingLaizi));
                log.info("-------------------------------------------------------");
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
    public boolean keJudgment(List<Integer> cards, int laizi) {
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
            log.info("-------------------------------------------------------");
            log.info("当前牌组：");
            log.info(CardUtil.toString(remainingCards, laizi));
            //从剩余牌组中移除刻子成员
            for (int i = 0; i < 3; i++) {
                //刻子成员所在的位置下标
                int deletePos = remainingCards.indexOf(keziCard);
                if (deletePos >= 0) {
                    log.info("移除牌：" + remainingCards.get(deletePos));
                    remainingCards.remove(deletePos);
                } else {
                    log.info("移除牌：赖子");
                    remainingLaizi--;
                }
            }
            log.info("移除【刻子】后的牌组：");
            log.info(CardUtil.toString(remainingCards, remainingLaizi));
            log.info("-------------------------------------------------------");
            if (shunJudgment(remainingCards, remainingLaizi) || keJudgment(remainingCards, remainingLaizi)) {
                return true;
            }
        }
        return false;
    }
}
