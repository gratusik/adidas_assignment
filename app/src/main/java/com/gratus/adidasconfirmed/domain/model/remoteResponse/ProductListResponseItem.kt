package com.gratus.adidasconfirmed.domain.model.remoteResponse

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ProductListResponseItem(
    @SerializedName("currency")
    var currency: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("imgUrl")
    var imgUrl: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("price")
    var price: Int,
    @SerializedName("reviews")
    var reviews: ArrayList<Review>,
    var averageRating: Float?
) : Parcelable {
    // concat currency and price
    fun getPriceWithCurrency(): String {
        return if (currency.isEmpty()) {
            "€ $price".trim()
        } else {
            "$currency $price".trim()
        }
    }

    // get average rating from reviews list 
    fun getAverageRating(): Float {
        var sum = 0.0
        var averageRating = 0.0f
        if (reviews.isNotEmpty()) {
            for (i in reviews.indices) {
                sum += reviews[i].rating!!
            }
            averageRating = (sum / reviews.size).toFloat()
        }
        return averageRating
    }
}
