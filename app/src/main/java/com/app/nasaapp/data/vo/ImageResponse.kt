package com.app.nasaapp.data.vo


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("collection")
    val collectionsItem: ImageCollection
)