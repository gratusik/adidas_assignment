package com.gratus.adidasconfirmed

import com.gratus.adidasconfirmed.domain.model.localCache.ProductModel

object MockResponseUtil {
    fun mockTheProductLocal():
        ProductModel {
        return ProductModel(
            productId = "HI333",
            isFavourite = 1,
        )
    }
}
