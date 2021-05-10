package com.gratus.adidasconfirmed.util.Interfaces

import android.view.View

// interface to use on item click of adapter item in fragment 
interface ProductListListener {
    fun onItemClick(view: View, productId: String)
}
