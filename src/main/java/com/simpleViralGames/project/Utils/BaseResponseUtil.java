package com.simpleViralGames.project.Utils;

import com.simpleViralGames.project.Enums.StatusCode;
import com.simpleViralGames.project.Responses.BaseResponse;

import javax.validation.constraints.NotNull;

public class BaseResponseUtil {
    private BaseResponseUtil() {
        throw new IllegalStateException();
    }
    public static BaseResponse createSuccessBaseResponse() {
        return new BaseResponse(StatusCode.OK.getErrorState(), StatusCode.OK.getStatusMessage(), StatusCode.OK.getCode());
    }

    public static BaseResponse createNoDataBaseResponse() {
        return new BaseResponse(StatusCode.NO_DATA.getErrorState(), StatusCode.NO_DATA.getStatusMessage(), StatusCode.NO_DATA.getCode());
    }

    public static BaseResponse createErrorBaseResponse() {
        return new BaseResponse(StatusCode.INTERNAL_SERVER_ERROR.getErrorState(), StatusCode.INTERNAL_SERVER_ERROR.getStatusMessage(), StatusCode.INTERNAL_SERVER_ERROR.getCode());
    }

    public static <T extends BaseResponse> T createBaseResponse(T response, @NotNull StatusCode code) {
        response.setEs(code.getErrorState());
        response.setMessage(code.getStatusMessage());
        response.setStatusCode(code.getCode());
        return response;
    }

}
