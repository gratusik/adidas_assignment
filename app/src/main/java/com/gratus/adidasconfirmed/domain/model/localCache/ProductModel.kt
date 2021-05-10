package com.gratus.adidasconfirmed.domain.model.localCache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.PRODUCT_TABLE

@Entity(tableName = PRODUCT_TABLE)
// model data class for product table room to store product id and is favourite
data class ProductModel(
    val productId: String?,
    val isFavourite: Int,
    @PrimaryKey(autoGenerate = false) val id: Int? = null,
)
