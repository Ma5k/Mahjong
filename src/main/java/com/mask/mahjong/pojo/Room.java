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
                    break;
                case 1:
                    player.setDirection("south");
                    break;
                case 2:
                    player.setDirection("west");
                    break;
                case 3:
                    player.setDirection("north");
                    break;
                default:
                    player.setDirection("error");
                    break;
            }
            player.setHand(new ArrayList<Card>());
            player.setPlayerID(UUID.randomUUID().toString());
            player.setHand(new ArrayList<Card>());
            player.setScore(0);
            playerList.add(player);
        }
        this.playerList = playerList;
        return playerList;
    }
}
