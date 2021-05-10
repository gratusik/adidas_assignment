package com.gratus.adidasconfirmed.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gratus.adidasconfirmed.data.service.remote.ReviewService
import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import com.gratus.adidasconfirmed.util.MockResponseUtil
import com.gratus.adidasconfirmed.util.RxSchedulerRule
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ReviewDataSourceTest : TestCase() {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var reviewDataSource: ReviewDataSource

    @Mock
    private lateinit var reviewService: ReviewService

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        reviewDataSource = ReviewDataSource(reviewService)
    }

    @Test
    fun addReview_toNetwork() {

        // Arrange
        Mockito.`when`(
            MockResponseUtil.mockTheReview().productId?.let {
                reviewService.addReviewService(
                    it,
                    MockResponseUtil.mockTheReview()
                )
            }
        )
            .thenReturn(Single.just(MockResponseUtil.mockTheReview()))

        // Act
        val review: TestObserver<Review>? =
            reviewDataSource.addReview(MockResponseUtil.mockTheReview()).test()

        // Assert
        if (review != null) {
            assertEquals(
                review.values()[0].productId,
                MockResponseUtil.mockTheReview().productId
            )
        }
    }
}
