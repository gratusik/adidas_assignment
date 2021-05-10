package com.gratus.adidasconfirmed.ui.viewmodel.state

import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem

sealed class ProductDetailsState {
    data class Loading(var loading: Boolean) : ProductDetailsState()
    data class Error(var message: String) : ProductDetailsState()
    data class ProductDetailsSuccess(val details: ProductListResponseItem) :
        ProductDetailsState()
}
