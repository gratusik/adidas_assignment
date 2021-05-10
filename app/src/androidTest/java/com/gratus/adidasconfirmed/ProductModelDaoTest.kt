package com.gratus.adidasconfirmed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gratus.adidasconfirmed.data.service.local.dao.ProductDao
import com.gratus.adidasconfirmed.data.service.local.db.AdidasDataBase
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductModelDaoTest : TestCase() {
    private lateinit var database: AdidasDataBase
    private lateinit var productDao: ProductDao

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDB() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AdidasDataBase::class.java
        ).allowMainThreadQueries()
            .build()
        productDao = database.productDao()
    }

    @After
    fun closeDB() {
        database.close()
    }

    @Test
    fun insertGetProductModel() {
        productDao.insert(MockResponseUtil.mockTheProductLocal())
        val productModelData = productDao.getProductDetails("HI333")
        productModelData.observeForever {
            assertEquals(it.productId, MockResponseUtil.mockTheProductLocal().productId)
        }
    }

    @Test
    fun insertDeleteGetProductModel() {
        productDao.insert(MockResponseUtil.mockTheProductLocal())
        productDao.deleteByProductId("HI333")
        val productModelDataAfterDelete = productDao.getProductDetails("HI333")
        assertEquals(productModelDataAfterDelete.value, null)
    }
}
