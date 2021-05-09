package com.gratus.adidasconfirmed.ui.viewmodel.adapter

import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.util.Interfaces.ProductListListener
import javax.inject.Inject

// Item view model to data binding of data to respective xml
class ProductListItemViewModel @Inject constructor(
    private var productListResponseItem: ProductListResponseItem,
    private var listener: ProductListListener
) {
    fun getProductListResponseItem(): ProductListResponseItem {
        return productListResponseItem
    }

    fun onItemClick() {
        listener.onItemClick(productListResponseItem.id)
    }
}
