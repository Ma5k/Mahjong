package com.mask.mahjong.pojo;

import com.mask.mahjong.utils.CardComparator;
import com.mask.mahjong.utils.CardUtil;
import com.mask.mahjong.utils.GamblingUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 牌局
 * @author Mask
 */
@Data
@Slf4j
public class Gambling {

    /**
     * 牌局ID
     */
    private String gamblingID;

    /**
     * 牌池
     */
    private List<Card> cardPool;

    public Gambling(){
        this.gamblingID = UUID.randomUUID().toString();
        this.cardPool = creatCardPool();
    }

    /**
     * 创建牌池（洗牌新开一局）
     * @return
     */
    public List<Card> creatCardPool() {
        if(this.cardPool != null) {
            return this.cardPool;
        }
        List<Card> cards = new ArrayList<Card>(108);
        List<Card> cardPool = new ArrayList<Card>(108);
        for(int i = 0;i < 3;i++) {
            for(int j = 1;j <= 9;j++) {
                int cardNum = i * 10 + j;
                for(int k = 1;k <= 4;k++) {
                    Card card = new Card();
                    card.setCardID(CardUtil.cardCode(cardNum) + "_" + k);
                    card.setName(CardUtil.cardName(cardNum));
                    card.setNumber(cardNum);
                    card.setSuit(CardUtil.cardSuit(cardNum, false));
                    card.setPoint(cardNum % 10);
                    cards.add(card);
                }
            }
        }

        for(int i = 0;i < 108;i++) {
            Random random = new Random();
            int randomIndex = random.nextInt(cards.size());
            cardPool.add(cards.get(randomIndex));
            cards.remove(randomIndex);
        }
        return cardPool;
    }

/*

    public Gambling() {
        List<Player> playerList = GamblingUtil.createPlayers();
        List<Card> cardPool = GamblingUtil.creatCardPool();
        this.gamblingID = UUID.randomUUID().toString();
        this.cardPool = cardPool;
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
*/


    /**
     * 摸牌
     */
//    public void deal() {
//        List<Card> cardPool = this.cardPool;
//        Player currentPlayer = this.players.stream().filter(player -> player.isTurn()).findFirst().get();
//        if(currentPlayer.isTurn()) {
//            Card topCard = cardPool.get(cardPool.size() - 1);
//            topCard.setOwner(currentPlayer.getPlayerID());
//            cardPool.remove(topCard);
//            currentPlayer.getHand().add(topCard);
//            currentPlayer.getHand().sort(new CardComparator());
//        } else {
//            log.error("当前未轮到【%s】摸牌！", currentPlayer.getPlayerID());
//        }
//    }

//    public void play(Player turnPlayer,Card card) {
//        Player currentPlayer = this.players.stream().filter(player -> player.isTurn()).findFirst().get();
//        if(!turnPlayer.equals(currentPlayer)){
//            log.error(String.format("未轮到玩家【%s】出牌，当前出牌玩家为【%s】",turnPlayer.getPlayerID(),currentPlayer.getPlayerID()));
//        }else if(currentPlayer.getHand().indexOf(card) == -1) {
//            log.error(String.format("玩家【%s】手中没有【%s】，出牌失败！",currentPlayer.getPlayerID(),card.getName()));
//        } else {
//            card.setOpen(true);
//            currentPlayer.getHand().remove(card);
//            currentPlayer.getOpenedCards().add(card);
//            GamblingUtil.getNextPlayer(this.players);
//        }
//    }

//    public void setPlayerTurn(Player turnPlayer) {
//        Player currentPlayer = this.players.stream().filter(player -> player.isTurn()).findFirst().get();
//        currentPlayer.setTurn(false);
//        turnPlayer.setTurn(true);
//    }
}
