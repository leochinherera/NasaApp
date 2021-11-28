package com.app.nasaapp.data.vo


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("data")
    val mydata: List<Data>,
    @SerializedName("href")
    val href: String,
    @SerializedName("links")
    val links: List<Link>
)