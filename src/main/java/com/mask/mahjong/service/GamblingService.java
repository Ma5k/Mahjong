package com.mask.mahjong.service;

import com.mask.mahjong.pojo.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamblingService {
    public void nextTurn(List<Player> playerList){
        Player currentPlayer = playerList.stream().filter(Player::isTurn).findFirst().get();
        currentPlayer.setTurn(false);
        int currentPlayerIndex = playerList.indexOf(currentPlayer);
        if(currentPlayerIndex == 3) {
            playerList.get(0).setTurn(true);
        } else {
            playerList.get(currentPlayerIndex + 1).setTurn(true);
        }
    }
}
