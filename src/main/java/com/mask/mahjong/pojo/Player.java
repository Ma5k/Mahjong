package com.mask.mahjong.pojo;

import com.mask.mahjong.utils.CardUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Card> hand;

    /**
     * 打出去的牌（包含：打掉的牌、碰、杠、吃）
     */
    private List<Card> openedCards;

    /**
     * 积分
     */
    private int score = 0;

    /**
     * 方向（东南西北）
     */
    private String direction;

    /**
     * 当前是否轮到
     */
    private boolean turn = false;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.playerID);
        sb.append("_");
        List<Integer> cardNumberList = this.getHand().stream().map(Card::getNumber).collect(Collectors.toList());
        sb.append(CardUtil.toString(cardNumberList,0));
        return sb.toString();
    }

}
