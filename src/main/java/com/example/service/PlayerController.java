package com.example.service;

import com.example.entity.PlayerResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "/player", method = RequestMethod.GET)
    public ResponseEntity getPlayers(@RequestParam(required = false) Integer id) {
        if (null != id) {
            PlayerResponse playerResponse = playerService.getPlayerById(id);
            return new ResponseEntity(playerResponse, HttpStatus.OK);
        }

        List<PlayerResponse> playerResponses = playerService.getAllPlayers();
        return new ResponseEntity<>(playerResponses, HttpStatus.OK);
    }

}
