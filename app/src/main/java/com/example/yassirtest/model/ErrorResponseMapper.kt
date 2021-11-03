package com.example.yassirtest.model

import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message

object ErrorResponseMapper : ApiErrorModelMapper<MovieErrorResponse> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): MovieErrorResponse {
        return MovieErrorResponse(apiErrorResponse.statusCode.code, apiErrorResponse.message())
    }
}