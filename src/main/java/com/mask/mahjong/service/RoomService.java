package com.mask.mahjong.service;

import com.mask.mahjong.pojo.Player;
import com.mask.mahjong.pojo.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
