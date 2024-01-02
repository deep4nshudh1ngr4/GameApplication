package com.simpleViralGames.project.Responses;

import com.simpleViralGames.project.DTO.GameDetailsDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class GameDetailsResponse extends BaseResponse{

    @JsonProperty("game_details")
    List<GameDetailsDTO> gameDetailsResponses;
}
