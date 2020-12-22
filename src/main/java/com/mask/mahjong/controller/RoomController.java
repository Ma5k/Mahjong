package com.mask.mahjong.controller;

import com.mask.mahjong.pojo.Room;
import com.mask.mahjong.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mask
 */
@RestController
@Slf4j
public class RoomController {

    @Autowired
    RoomService roomService;

    @RequestMapping("/room/{roomID}")
    public Map<String, Object> creatRoom(@PathVariable String roomID){
        Map<String, Object> resultMap = new HashMap<>();
        Room room = new Room();
        room.setRoomID(roomID);
        room = roomService.createRoom(roomID);
        resultMap.put("room", room);
        return resultMap;
    }
}
