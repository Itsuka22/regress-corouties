package com.handoyo.reqresscorouties.data


import com.google.gson.annotations.SerializedName

data class SupportUser(
    @SerializedName("text")
    val text: String,
    @SerializedName("url")
    val url: String
)