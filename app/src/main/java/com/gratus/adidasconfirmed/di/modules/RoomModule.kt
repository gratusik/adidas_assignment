package com.gratus.adidasconfirmed.di.modules

import android.app.Application
import androidx.room.Room
import com.gratus.adidasconfirmed.data.service.local.dao.ProductDao
import com.gratus.adidasconfirmed.data.service.local.db.AdidasDataBase
import com.gratus.adidasconfirmed.util.constants.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Room database module
@Module
class RoomModule {
    // connect to db
    @Singleton
    @Provides
    fun providesAdidasDataBase(application: Application): AdidasDataBase {
        return Room.databaseBuilder(
            application,
            AdidasDataBase::class.java,
            AppConstants.ADIDAS_DB
        )
            .build()
    }

    // room db service dao
    @Singleton
    @Provides
    fun providesProductDao(adidasDataBase: AdidasDataBase): ProductDao {
        return adidasDataBase.productDao()
    }
}
