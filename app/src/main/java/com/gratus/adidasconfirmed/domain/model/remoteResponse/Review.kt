package com.gratus.adidasconfirmed.domain.model.remoteResponse

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Review(
    @SerializedName("locale")
    var locale: String,
    @SerializedName("productId")
    var productId: String,
    @SerializedName("rating")
    var rating: Int,
    @SerializedName("text")
    var text: String
) : Parcelable
