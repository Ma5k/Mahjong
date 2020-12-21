package com.mask.mahjong.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Room {
    /**
     * 玩家
     */
    private List<Player> playerList;

    /**
     * 牌局
     */
    private Gambling gambling;

    public Room(){
        this.playerList = createPlayers();
        this.gambling = new Gambling();
        for(int i = 0;i < 12;i++) {
            Player currentPlayer =  this.playerList.stream().filter(Player::isTurn).findFirst().get();
            currentPlayer.deal(4,this.gambling);
            nextTurn();
        }
        for(int i = 0;i < 4;i++) {
            Player currentPlayer =  this.playerList.stream().filter(Player::isTurn).findFirst().get();
            currentPlayer.deal(1,this.gambling);
            nextTurn();
        }
        Player currentPlayer =  this.playerList.stream().filter(Player::isTurn).findFirst().get();
        currentPlayer.deal(1,this.gambling);
    }

    /**
     *
     * @return
     */
    public List<Player> createPlayers(){
        if(this.playerList != null) {
            return this.playerList;
        }
        List<Player> playerList = new ArrayList<Player>(4);
        for(int i =0;i < 4;i++) {
            Player player = new Player();
            switch (i) {
                case 0:
                    player.setTurn(true);
                    player.setDirection("east");
                    player.setDirectionNum(0);
                    break;
                case 1:
                    player.setDirection("south");
                    player.setDirectionNum(1);
                    break;
                case 2:
                    player.setDirection("west");
                    player.setDirectionNum(2);
                    break;
                case 3:
                    player.setDirection("north");
                    player.setDirectionNum(3);
                    break;
                default:
                    player.setDirection("error");
                    player.setDirectionNum(4);
                    break;
            }
            player.setHand(new ArrayList<Card>());
            player.setPlayerID(UUID.randomUUID().toString());
            player.setScore(0);
            playerList.add(player);
        }
        this.playerList = playerList;
        return playerList;
    }

    private void nextTurn(){
        Player currentPlayer = this.playerList.stream().filter(Player::isTurn).findFirst().get();
        currentPlayer.setTurn(false);
        int nextPlayerIndex = currentPlayer.getDirectionNum();
        if(nextPlayerIndex == 3) {
            this.playerList.get(0).setTurn(true);
        } else {
            this.playerList.get(nextPlayerIndex + 1).setTurn(true);
        }
    }
}
