package com.gratus.adidasconfirmed.data.service.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gratus.adidasconfirmed.data.service.local.dao.ProductDao
import com.gratus.adidasconfirmed.domain.model.localCache.ProductModel

// Database setup and version
@Database(entities = [ProductModel::class], version = 1)
abstract class AdidasDataBase : RoomDatabase() {

    abstract fun productDao(): ProductDao
}
