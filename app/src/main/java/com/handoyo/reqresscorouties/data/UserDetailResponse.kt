package com.handoyo.reqresscorouties.data


import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("data")
    val `data`: DataUser,
    @SerializedName("support")
    val support: SupportUser
)