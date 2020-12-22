package com.mask.mahjong.controller;

import com.mask.mahjong.pojo.Gambling;
import com.mask.mahjong.pojo.Player;
import com.mask.mahjong.pojo.Room;
import com.mask.mahjong.service.GamblingService;
import com.mask.mahjong.service.RoomService;
import com.mask.mahjong.utils.Dice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mask
 */
@RestController
@Slf4j
public class GamblingController {
    @Autowired
    RoomService roomService;

    @Autowired
    GamblingService gamblingService;

    @RequestMapping("/gambling/{roomID}/{gamblingID}")
    public Map<String, Object> startGambling(@PathVariable String roomID, @PathVariable String gamblingID) {
        Map<String, Object> resultMap = new HashMap<>();
        Room room = roomService.getRoom(roomID);
        if (room == null) {
            log.info("房间【{}】不存在", roomID);
            return resultMap;
        }
        List<Player> playerList = room.getPlayerList();
        int playerAmount = playerList.size();
        if (playerAmount < 4) {
            log.info("房间【{}】人数为【{}】，不满4人无法开始", roomID, playerAmount);
        } else {
            int[] dices = Dice.getDicePoint(2);
            Gambling gambling = new Gambling();
            gambling.setGamblingID(gamblingID);
            room.setGambling(gambling);
            room.getPlayerList().get(0).setTurn(true);
            for (int i = 0; i < 12; i++) {
                Player currentPlayer = playerList.stream().filter(Player::isTurn).findFirst().get();
                currentPlayer.deal(4, gambling);
                gamblingService.nextTurn(playerList);
            }
            for (int i = 0; i < 4; i++) {
                Player currentPlayer = playerList.stream().filter(Player::isTurn).findFirst().get();
                currentPlayer.deal(1, gambling);
                gamblingService.nextTurn(playerList);
            }
            Player currentPlayer = playerList.stream().filter(Player::isTurn).findFirst().get();
            currentPlayer.deal(1, gambling);
            resultMap.put("gambling",gambling);
        }
        return resultMap;
    }
}
