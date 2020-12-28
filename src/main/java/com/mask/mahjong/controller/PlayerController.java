package com.mask.mahjong.controller;

import com.mask.mahjong.pojo.Card;
import com.mask.mahjong.pojo.Player;
import com.mask.mahjong.pojo.Room;
import com.mask.mahjong.service.RoomService;
import com.mask.mahjong.utils.GamblingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mask
 */
@RestController
@Slf4j
public class PlayerController {

    @Autowired
    RoomService roomService;

    @RequestMapping("/player/{playerID}")
    public Map<String, Object> createPlayer(@PathVariable String playerID){
        Map<String, Object> resultMap = new HashMap<>();
        Player player = new Player();
        player.setPlayerID(playerID);
        return resultMap;
    }

    @RequestMapping("/player/{roomID}/{playerID}")
    public Map<String, Object> joinRoom(@PathVariable String playerID, @PathVariable String roomID){
        Map<String, Object> resultMap = new HashMap<>();
        Room room = roomService.getRoom(roomID);
        List<Player> playerList = room.getPlayerList();
        Player player = roomService.getPlayer(roomID, playerID);
        if(player != null) {
            log.info("玩家【{}】已在房间【{}】", playerID, roomID);
            resultMap.put("player", roomService.getPlayer(roomID, playerID));
        } else {
            player = new Player();
            player.setPlayerID(playerID);
            player.setHand(new ArrayList<Card>());
            player.setOpenedCards(new ArrayList<Card>());
            playerList.add(player);
            log.info("玩家【{}】进入房间【{}】", playerID, roomID);
            resultMap.put("player", player);
        }
        return resultMap;
    }

    @RequestMapping("/player/deal/{roomID}/{playerID}")
    public Map<String, Object> dealCard(@PathVariable String roomID, @PathVariable String playerID) {
        Map<String, Object> resultMap = new HashMap<>();
        Room room = roomService.getRoom(roomID);
        Player currentPlayer = roomService.getCurrentPlay(roomID);
        currentPlayer.deal(1, room.getGambling());
        currentPlayer.setDealt(true);
        List<Integer> cards = currentPlayer.getHand().stream().map(Card::getNumber).collect(Collectors.toList());
        String judgment = GamblingUtil.huJudgment(cards,0);
        resultMap.put("judgment",judgment);
        resultMap.put("player", currentPlayer);
        return resultMap;
    }

    @RequestMapping("/player/{roomID}/{playerID}/{cardID}")
    public Map<String, Object> playCard(@PathVariable String roomID, @PathVariable String playerID, @PathVariable String cardID) {
        Map<String, Object> resultMap = new HashMap<>();
        Room room = roomService.getRoom(roomID);
        Player player = roomService.getPlayer(roomID, playerID);
        if(player.isDealt()) {
            player.play(cardID);
            room.nextTurn();
        } else {
            log.error("玩家【{}】还未摸牌", cardID);
        }
        resultMap.put("player",player);
        return resultMap;
    }
}
