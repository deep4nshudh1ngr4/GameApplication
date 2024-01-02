package com.simpleViralGames.project.controller;

import com.simpleViralGames.project.Enums.StatusCode;
import com.simpleViralGames.project.Manager.GameManager;
import com.simpleViralGames.project.Request.GameRequest;
import com.simpleViralGames.project.Responses.BaseResponse;
import com.simpleViralGames.project.Utils.BaseResponseUtil;
import com.simpleViralGames.project.config.LogFactory;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GameController {

    private static final Logger LOG = LogFactory.getLogger(GameController.class);

    @Autowired
    GameManager gameManager;

    @PostMapping("/game")
    public ResponseEntity<BaseResponse> addGame(@Valid @RequestBody GameRequest request) {
        try{
            return new ResponseEntity<>(gameManager.addGame(request), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Exception while creating game : {}", ExceptionUtils.getStackTrace(e));
            return new ResponseEntity<>(BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/game/{name}")
    public ResponseEntity<BaseResponse> editGame(@Valid @RequestBody GameRequest request,  @PathVariable("name") String gameName) {
        try{
            return new ResponseEntity<>(gameManager.editGame(request, gameName), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Exception while editing game: {}", ExceptionUtils.getStackTrace(e));
            return new ResponseEntity<>(BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/game")
    public ResponseEntity<BaseResponse> getGame(@RequestParam(name = "game_name", required = false) String gameName, @RequestParam(name = "author_name", required = false) String authorName) {
        try {
            return new ResponseEntity<>(gameManager.getGame(gameName, authorName), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Exception while getting game: {}", ExceptionUtils.getStackTrace(e));
            return new ResponseEntity<>(BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/games")
    public ResponseEntity<BaseResponse> getGames() {
        try{
            return new ResponseEntity<>(gameManager.getGameList(), HttpStatus.OK);
        }   catch (Exception e) {
            LOG.error("Exception while getting games list: {}", ExceptionUtils.getStackTrace(e));
            return new ResponseEntity<>(BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/game/{name}")
    public ResponseEntity<BaseResponse> deleteGame( @PathVariable("name") String gameName) {
        try{
            return new ResponseEntity<>(gameManager.deleteGame(gameName), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Exception while deleting game: {}", ExceptionUtils.getStackTrace(e));
            return new ResponseEntity<>(BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
