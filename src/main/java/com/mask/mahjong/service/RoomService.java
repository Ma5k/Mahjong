package com.mask.mahjong.service;

import com.mask.mahjong.pojo.Player;
import com.mask.mahjong.pojo.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class RoomService {

    private static final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public Room createRoom(String roomID) {
        if(rooms.containsKey(roomID)) {
            log.info("房间【{}】已存在", roomID);
        } else {
            log.info("创建房间【{}】", roomID);
            Room room = new Room();
            room.setPlayerList(new ArrayList<Player>(4));
            room.setRoomID(roomID);
            rooms.put(roomID,room);
        }
        return rooms.get(roomID);
    }

    @Cacheable(value = "room", key = "#roomID")
    public Room getRoom(String roomID) {
        Room room = rooms.get(roomID);

        if(room == null) {
            room = createRoom(roomID);
        }
        return room;
    }

    public void removeRoom(String roomID) {
        rooms.remove(roomID);
    }

    public Player getPlayer(String roomID, String playerID) {
        Room room = rooms.get(roomID);
        Player player = null;
        if(roomExistPlayer(roomID, playerID)) {
            player = room.getPlayerList().stream().filter(p -> p.getPlayerID().equals(playerID)).findFirst().get();
        }
        return player;
    }

    boolean roomExistPlayer(String roomID, String playerID) {
        Room room = getRoom(roomID);
        List<Player> playerList = room.getPlayerList();
        boolean playerExist = false;
        if(playerList == null) {
            log.info("房间【{}】不存在玩家【{}】", roomID, playerID);
        } else {
            playerExist = playerList.stream().anyMatch(player -> playerID.equals(player.getPlayerID()));
        }
        return playerExist;
    }

    public Player getCurrentPlay(String roomID) {
        Room room = rooms.get(roomID);
        Player player = null;
        List<Player> playerList = room.getPlayerList();
        if(playerList.stream().anyMatch(Player::isTurn)){
            player = playerList.stream().findFirst().filter(Player::isTurn).get();
        }
        return player;
    }
}
