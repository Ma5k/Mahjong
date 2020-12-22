package com.mask.mahjong.controller;

import com.mask.mahjong.pojo.Card;
import com.mask.mahjong.pojo.Player;
import com.mask.mahjong.pojo.Room;
import com.mask.mahjong.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        boolean playerExist = false;
        if(playerList == null){
            playerList = new ArrayList<Player>();
        } else if(playerList.size() > 0) {
            playerExist = playerList.stream().anyMatch(player -> playerID.equals(player.getPlayerID()));
        }
        if(playerExist) {
            log.info("玩家【{}】已在房间【{}】", playerID, roomID);
            resultMap.put("player", room.getPlayerList().stream().filter(player -> player.getPlayerID().equals(playerID)).findFirst().get());
        } else {
            Player player = new Player();
            player.setPlayerID(playerID);
            player.setHand(new ArrayList<Card>());
            player.setOpenedCards(new ArrayList<Card>());
            playerList.add(player);
            log.info("玩家【{}】进入房间【{}】", playerID, roomID);
            resultMap.put("player", player);
        }
        return resultMap;
    }
}
