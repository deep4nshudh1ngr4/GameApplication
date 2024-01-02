package com.simpleViralGames.project.Manager;

import com.simpleViralGames.project.Constants.Constants;
import com.simpleViralGames.project.DTO.GameDetailsDTO;
import com.simpleViralGames.project.Database.mongo.Dao.GameDao;
import com.simpleViralGames.project.Database.mongo.Entity.Game;
import com.simpleViralGames.project.Enums.StatusCode;
import com.simpleViralGames.project.Request.GameRequest;
import com.simpleViralGames.project.Responses.BaseResponse;
import com.simpleViralGames.project.Responses.GameDetailsResponse;
import com.simpleViralGames.project.Utils.BaseResponseUtil;
import com.simpleViralGames.project.config.LogFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class GameManager {

    private static final Logger LOG = LogFactory.getLogger(GameManager.class);

    @Autowired
    GameDao gameDao;

    public BaseResponse addGame(GameRequest request) {
        LOG.info("received request to add game: {}", request.getGameDetails());

        BaseResponse validationResponse = validateGameRequest(request);

        if (!validationResponse.getEs().equals(Constants.SUCCESS)) {
            return validationResponse;
        }

        GameDetailsDTO gameDetails = request.getGameDetails();
        Game game;
        game = gameDao.findByNameAndDeleted(gameDetails.getName(), 0);

        if (Objects.nonNull(game)) {
            LOG.info("Game with name: {} already exists", game.getName());
            return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.GAME_NAME_ALREADY_EXISTS);
        } else {
            game = Game.builder()
                    .name(gameDetails.getName())
                    .url(gameDetails.getUrl())
                    .build();
        }

        game.create(gameDetails.getAuthor());
        gameDao.save(game);
        return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.OK);
    }

    public BaseResponse editGame(GameRequest request, String gameName) {
        LOG.info("received request to edit game {}", request);
        BaseResponse validationResponse = validateGameRequest(request);

        if (!validationResponse.getEs().equals(Constants.SUCCESS)) {
            return validationResponse;
        }

        Game game = gameDao.findByNameAndDeleted(gameName, 0);
        if(Objects.isNull(game)) {
            LOG.info("Game with name: {} not exists", gameName);
            return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.GAME_NAME_NOT_EXISTS);
        }

        GameDetailsDTO gameDetails = request.getGameDetails();

        game.setAuthor(gameDetails.getAuthor());
        game.setName(gameDetails.getName());
        game.setUrl(gameDetails.getUrl());

        game.update(gameDetails.getAuthor());

        gameDao.save(game);
        return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.OK);
    }

    public GameDetailsResponse getGame(String gameName, String authorName) {
        LOG.info("received request to get game");
        if(StringUtils.isEmpty(gameName) && StringUtils.isEmpty(authorName)) {
            return BaseResponseUtil.createBaseResponse(new GameDetailsResponse(), StatusCode.GET_REQUEST_FAILED);
        }

        GameDetailsResponse response;
        Game game = null;
        if(StringUtils.isNotEmpty(gameName)) {
            game = gameDao.findByNameAndDeleted(gameName, 0);
        } else if(StringUtils.isNotEmpty(authorName)) {
            game = gameDao.findByAuthorAndDeleted(authorName, 0);
        }
        if (Objects.isNull(game)) {
            LOG.info("Game not exists");
            return BaseResponseUtil.createBaseResponse(new GameDetailsResponse(), StatusCode.GAME_NAME_NOT_EXISTS);
        }

        GameDetailsDTO gameDetailsDTO = GameDetailsDTO.builder()
                .author(game.getAuthor())
                .date(game.getCreated())
                .url(game.getUrl())
                .name(game.getName())
                .build();
        response = GameDetailsResponse.builder()
                .gameDetailsResponses(Collections.singletonList(gameDetailsDTO))
                .build();

        return BaseResponseUtil.createBaseResponse(response, StatusCode.OK);
    }

    public GameDetailsResponse getGameList() {
        LOG.info("received request to get game list{}");

        GameDetailsResponse response;
        List<Game> gamesList = null;
        gamesList = gameDao.findAllByDeleted(0);

        if (Objects.isNull(gamesList)) {
            LOG.info("No active game found");
            return BaseResponseUtil.createBaseResponse(new GameDetailsResponse(), StatusCode.NO_DATA);
        }

        List<GameDetailsDTO> gameDetailsDTOList = new ArrayList<>();

        for(Game game: gamesList) {
            GameDetailsDTO gameDetailsDTO = GameDetailsDTO.builder()
                    .author(game.getAuthor())
                    .date(game.getCreated())
                    .url(game.getUrl())
                    .name(game.getName())
                    .build();
            gameDetailsDTOList.add(gameDetailsDTO);
        }
        response = GameDetailsResponse.builder()
                .gameDetailsResponses(gameDetailsDTOList)
                .build();

        return BaseResponseUtil.createBaseResponse(response, StatusCode.OK);
    }

    public BaseResponse deleteGame(String gameName) {
        LOG.info("received request to delete game");
        if(StringUtils.isEmpty(gameName)) {
            return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.INVALID_GAME_NAME);
        }

        Game game = gameDao.findByNameAndDeleted(gameName, 0);
        if(Objects.isNull(game)) {
            LOG.info("Game with name: {} not exists", gameName);
            return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.GAME_NAME_NOT_EXISTS);
        }

        game.setDeleted(1);
        game.setUpdated(System.currentTimeMillis());

        gameDao.save(game);
        return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.OK);
    }
    private BaseResponse validateGameRequest(GameRequest request) {
        try {
            GameDetailsDTO gameDetails = request.getGameDetails();

            if (StringUtils.isEmpty(gameDetails.getName())) {
                return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.INVALID_GAME_NAME);
            }

            if (StringUtils.isEmpty(gameDetails.getUrl())) {
                return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.INVALID_GAME_URL);
            }

            if (StringUtils.isEmpty(gameDetails.getAuthor())) {
                return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.INVALID_AUTHOR_NAME);
            }
            return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.OK);
        } catch (Exception e) {
            LOG.error("Exception while validating game request ", e);
            return BaseResponseUtil.createBaseResponse(new BaseResponse(), StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
