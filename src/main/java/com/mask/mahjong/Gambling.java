package com.mask.mahjong;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 牌局
 * @author Mask
 */
@Data
public class Gambling {

    /**
     * 牌局ID
     */
    private String gamblingID;

    /**
     * 玩家一
     */
    private Player playerOne = new Player();

    /**
     * 玩家二
     */
    private Player playerTwo = new Player();

    /**
     * 玩家三
     */
    private Player playerThree = new Player();

    /**
     * 玩家四
     */
    private Player playerFour = new Player();

    /**
     * 牌池
     */
    private List<Card> cardPool = new ArrayList<Card>(108);



}
