package com.mask.mahjong;

import lombok.Data;

/**
 * 玩家
 * @author Mask
 */
@Data
public class Player {

    /**
     * 玩家ID
     */
    private String playerID;

    /**
     * 手牌
     */
    private Card[] hand;

    /**
     * 打出去的牌（包含：打掉的牌、碰、杠、吃）
     */
    private Card[] openedCards;

    /**
     * 积分
     */
    private int score;

    /**
     * 方向（东南西北）
     */
    private String direction;
}
