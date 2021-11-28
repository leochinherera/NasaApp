package com.app.nasaapp.data.vo


import com.google.gson.annotations.SerializedName

data class ImageCollection(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("links")
    val links: List<LinkX>,
    @SerializedName("metadata")
    val metadata: Metadata,
    @SerializedName("version")
    val version: String
)