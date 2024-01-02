package com.simpleViralGames.project.Enums;

public enum StatusCode {
    OK(200, 0, "Success"),
    INTERNAL_SERVER_ERROR(500, 1, "Internal server error"),
    NO_DATA(300, 2, "No data found!"),

    INVALID_GAME_NAME(422, 1, "Game name can't be empty"),

    INVALID_GAME_URL(423, 1, "Game url can't be empty"),

    INVALID_AUTHOR_NAME(421, 1, "Author name can't be empty"),

    GAME_NAME_ALREADY_EXISTS(420, 1, "Game with same name already exists"),

    GAME_NAME_NOT_EXISTS(424, 1, "Game name does not exist"),

    GET_REQUEST_FAILED(425, 1, "No game name or author name found");


    private Integer code;
    private Integer errorState;
    private String statusMessage;

    StatusCode(Integer code, Integer errorState, String statusMessage) {
        this.code = code;
        this.statusMessage = statusMessage;
        this.errorState = errorState;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public Integer getErrorState() {
        return errorState;
    }
}
