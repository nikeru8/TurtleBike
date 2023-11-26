package com.turtle.turtlebike.api

import com.turtle.turtlebike.model.UBikeModel
import retrofit2.Response
import retrofit2.http.GET

interface YoubikeService {

    @GET("dotapp/youbike/v2/youbike_immediate.json")
    suspend fun getBike(
    ): Response<UBikeModel>

}