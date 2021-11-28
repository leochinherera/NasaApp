package com.app.nasaapp.data.vo


import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("center")
    val center: String,
    @SerializedName("date_created")
    val dateCreated: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("nasa_id")
    val nasaId: String,
    @SerializedName("title")
    val title: String

)