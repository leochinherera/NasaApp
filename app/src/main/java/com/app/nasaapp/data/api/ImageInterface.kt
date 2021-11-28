package com.app.nasaapp.data.api



import com.app.nasaapp.data.vo.ImageResponse

import io.reactivex.Single
import retrofit2.http.GET

import retrofit2.http.Query

interface ImageInterface {


    @GET("/search?q=milky%20way&media_type=image&year_start=2017")
    fun getImage(@Query("page") page: Int): Single<ImageResponse>

    @GET("/search?q=milky%20way")
    fun getImageDetails(@Query("nasa_id") nasaId: String): Single<ImageResponse>
}