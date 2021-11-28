package com.app.nasaapp.data.vo


import com.google.gson.annotations.SerializedName

data class LinkX(
    @SerializedName("href")
    val href: String,
    @SerializedName("prompt")
    val prompt: String,
    @SerializedName("rel")
    val rel: String
)