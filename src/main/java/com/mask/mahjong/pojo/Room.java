package com.mask.mahjong.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Room {
    /**
     * 房间号
     */
    private String RoomID;

    /**
     * 房间名
     */
    private String RoomName;

    /**
     * 玩家
     */
    private List<Player> playerList;

    /**
     * 牌局
     */
    private Gambling gambling;

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
}
