package com.gratus.adidasconfirmed.di.modules

import dagger.Module

// Room database module
@Module
class RoomModule {
    // connect to db
//    @Singleton
//    @Provides
//    fun providesKRDatabase(application: Application): AdidasDataBase {
//        return Room.databaseBuilder(
//            application,
//            AdidasDataBase::class.java,
//            AppConstants.ADIDAS_DB
//        )
//            .build()
//    }
//
//    // room db service dao
//    @Singleton
//    @Provides
//    fun providesOutletDetailsDao(adidasDataBase: AdidasDataBase): OutletDetailsDao {
//        return adidasDataBase.outletDetailsDao
//    }
}
