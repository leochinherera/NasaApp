package com.app.nasaapp.data.vo


import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("total_hits")
    val totalHits: Int
)