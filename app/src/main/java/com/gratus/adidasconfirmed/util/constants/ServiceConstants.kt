package com.gratus.adidasconfirmed.util.constants

interface ServiceConstants {
    companion object {
        const val BASE_URL = "http://localhost:3001/"
        const val PRODUCT_LIST_URL = "product"
        const val PRODUCT_DETAILS_URL = "product/{productId}"
        const val REVIEW_SUBMIT_BASE_URL = "http://localhost:3002/"
        const val REVIEW_SUBMIT_URL = "reviews/{productId}"
    }
}
