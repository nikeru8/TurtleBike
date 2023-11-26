package com.turtle.turtlebike

import com.turtle.turtlebike.api.YoubikeService
import com.turtle.turtlebike.model.UBikeModel
import retrofit2.Response

class MainRepository(var ubikeService: YoubikeService) {

    suspend fun callUbikeService(): Response<UBikeModel> {
        return ubikeService.getBike()
    }

}