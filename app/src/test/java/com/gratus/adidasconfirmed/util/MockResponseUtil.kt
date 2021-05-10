package com.gratus.adidasconfirmed.util

import com.gratus.adidasconfirmed.domain.model.localCache.ProductModel
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review

object MockResponseUtil {
    fun mockedProductsList() = arrayListOf(mockTheProduct())
    fun mockTheProduct(): ProductListResponseItem {
        return ProductListResponseItem(
            currency = "",
            price = 0,
            id = "HI333",
            name = "product",
            description = "description",
            imgUrl = "https://assets.adidas.com/images/w_320,h_320,f_auto,q_auto:sensitive,fl_lossy/c7099422ccc14e44b406abec00ba6c96_9366/NMD_R1_V2_Shoes_Black_FY6862_01_standard.jpg",
            reviews = ArrayList(mockReviews()),
            averageRating = 0f
        )
    }

    private fun mockReviews() = listOf(mockTheReview())
    fun mockTheReview(): Review {
        return Review(
            locale = "en-US",
            productId = "HI333",
            rating = 3,
            text = "Amazing!"
        )
    }

    fun mockTheProductLocal():
        ProductModel {
        return ProductModel(
            productId = "HI333",
            isFavourite = 1,
        )
    }
}
