package com.example.universityapp.data.model

import com.google.gson.annotations.SerializedName

data class University(
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("web_pages")
    val webPages: List<String>
)