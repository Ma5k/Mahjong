package com.mask.mahjong.pojo;

import com.mask.mahjong.utils.CardComparator;
import com.mask.mahjong.utils.CardUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 玩家
 * @author Mask
 */
@Data
@Slf4j
public class Player implements Serializable {

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
     * 方向编号
     */
    private int directionNum;

    /**
     * 方向（东南西北）
     */
    private String direction;

    /**
     * 当前是否轮到
     */
    private boolean turn = false;

    /**
     * 是否已摸牌
     */
    private boolean dealt = false;

    /**
     * 摸牌
     */
    public void deal(int amount, Gambling gambling){
        if(this.isTurn()) {
            List<Card> cardPool = gambling.getCardPool();

            for (int i = 0; i < amount; i++) {
                Card topCard = cardPool.get(cardPool.size() - 1);
                topCard.setOwner(this.getPlayerID());
                cardPool.remove(topCard);
                this.getHand().add(topCard);
            }
            this.getHand().sort(new CardComparator());
        } else {
            log.info("未轮到玩家【{}】摸牌", this.playerID);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.playerID);
        sb.append("_");
        List<Integer> cardNumberList = this.getHand().stream().map(Card::getNumber).collect(Collectors.toList());
        sb.append(CardUtil.toString(cardNumberList,0));
        return sb.toString();
    }

    public void play(String cardID) {
        if(!this.isTurn()){
            log.error("未轮到玩家【{}】出牌",this.getPlayerID());
        }else if(!holdCard(cardID)) {
            log.error("玩家【{}】手中没有【{}】，出牌失败！",this.getPlayerID(),cardID);
        } else {
            Card card = getCard(cardID);
            card.setOpen(true);
            this.getHand().remove(card);
            this.getOpenedCards().add(card);
        }
    }

    private Card getCard(String cardID) {
        Card card = null;
        if(holdCard(cardID)) {
            card = hand.stream().filter(c -> c.getCardID().equals(cardID)).findFirst().get();
        }
        return card;
    }

    private boolean holdCard(String cardID) {
        boolean holdCard = false;
        if(hand.size() > 0) {
            holdCard = hand.stream().anyMatch(card -> cardID.equals(card.getCardID()));
        }
        return holdCard;
    }
}
