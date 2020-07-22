package com.mask.mahjong.pojo;

import com.mask.mahjong.utils.CardComparator;
import com.mask.mahjong.utils.GamblingUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 牌局
 * @author Mask
 */
@Data
public class Gambling {

    private static Logger logger = LoggerFactory.getLogger(Gambling.class);

    /**
     * 牌局ID
     */
    private String gamblingID;

    /**
     * 玩家
     */
    private List<Player> players = new ArrayList<Player>(4);

    /**
     * 牌池
     */
    private List<Card> cardPool = GamblingUtil.creatCardPool();

    public Gambling() {
        List<Player> playerList = GamblingUtil.createPlayers();
        List<Card> cardPool = GamblingUtil.creatCardPool();
        this.gamblingID = UUID.randomUUID().toString();
        this.cardPool = cardPool;
        this.players = playerList;
        for(int i = 0;i < 13;i++) {
            for(int j = 0;j < 4;j++) {
                deal();
                Player currentPlayer = this.players.stream().filter(player -> player.isTurn()).findFirst().get();
                int currentPlayerIndex = players.indexOf(currentPlayer);
                int nextPlayerIndex = currentPlayerIndex == players.size() - 1?0:currentPlayerIndex+1;
                this.players.get(currentPlayerIndex).setTurn(false);
                this.players.get(nextPlayerIndex).setTurn(true);
            }
        }
    }


    /**
     * 摸牌
     */
    public void deal() {
        List<Card> cardPool = this.cardPool;
        Player currentPlayer = this.players.stream().filter(player -> player.isTurn()).findFirst().get();
        if(currentPlayer.isTurn()) {
            Card topCard = cardPool.get(cardPool.size() - 1);
            topCard.setOwner(currentPlayer.getPlayerID());
            cardPool.remove(topCard);
            currentPlayer.getHand().add(topCard);
            currentPlayer.getHand().sort(new CardComparator());
        } else {
            logger.error("当前未轮到【%s】摸牌！", currentPlayer.getPlayerID());
        }
    }

    public void play(Player turnPlayer,Card card) {
        Player currentPlayer = this.players.stream().filter(player -> player.isTurn()).findFirst().get();
        if(!turnPlayer.equals(currentPlayer)){
            logger.error(String.format("未轮到玩家【%s】出牌，当前出牌玩家为【%s】",turnPlayer.getPlayerID(),currentPlayer.getPlayerID()));
        }else if(currentPlayer.getHand().indexOf(card) == -1) {
            logger.error(String.format("玩家【%s】手中没有【%s】，出牌失败！",currentPlayer.getPlayerID(),card.getName()));
        } else {
            card.setOpen(true);
            currentPlayer.getHand().remove(card);
            currentPlayer.getOpenedCards().add(card);
            GamblingUtil.getNextPlayer(this.players);
        }
    }

    public void setPlayerTurn(Player turnPlayer) {
        Player currentPlayer = this.players.stream().filter(player -> player.isTurn()).findFirst().get();
        currentPlayer.setTurn(false);
        turnPlayer.setTurn(true);
    }
}
