package com.mask.mahjong;

import lombok.Data;

/**
 * 牌
 * @author Mask
 */
@Data
public class Card {

    /**
     * 牌的id
     */
    private String cardID;

    /**
     * 麻将的花色
     */
    private String suit;

    /**
     * 麻将的数字
     */
    private int number;

    /**
     * 是否是赖子
     */
    private boolean laizi = false;

    /**
     * 是否明牌（打出、碰或杠之后视为明牌）
     */
    private boolean open = false;

    /**
     * 是否为碰
     */
    private boolean peng = false;

    /**
     * 是否为杠
     */
    private boolean gang = false;

    /**
     * 是否为吃
     */
    private boolean chi= false;

    /**
     * 持牌人
     */
    private String owner;
}
