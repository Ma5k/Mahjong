package com.mask.mahjong.controller;

import com.mask.mahjong.pojo.Room;
import lombok.extern.slf4j.Slf4j;
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
public class GamblingController {
    @RequestMapping("/new/{roomId}")
    public Map<String, Object> creatRoom(@PathVariable String roomId){
        Map<String, Object> resultMap = new HashMap<>();
        Room room = new Room();
        resultMap.put("room", room);
        return resultMap;
    }
}
