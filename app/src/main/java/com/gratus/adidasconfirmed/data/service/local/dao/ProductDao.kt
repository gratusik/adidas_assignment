@file:Suppress("SpellCheckingInspection")

package com.gratus.adidasconfirmed.data.service.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gratus.adidasconfirmed.domain.model.localCache.ProductModel

// DAO for room datatbase
@Dao
interface ProductDao {
    // insert dao product details
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productModel: ProductModel)

    // delete product with  product Id
    @Query("DELETE FROM product_table WHERE productId = :productId")
    fun deleteByProductId(productId: String)

    // searched results of product with productId
    @Query("SELECT * FROM product_table WHERE productId =:productId")
    fun getProductDetails(productId: String): LiveData<ProductModel>
}
