package com.gratus.adidasconfirmed.network

import com.gratus.adidasconfirmed.data.service.remote.ReviewService
import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import com.gratus.adidasconfirmed.util.MockResponseUtil
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@RunWith(JUnit4::class)
class ReviewServiceTest : MockServerSetup<ReviewService>() {
    @Mock
    private lateinit var reviewService: ReviewService

    @Before
    fun initService() {
        reviewService = retrofitService(ReviewService::class.java)
    }

    @Test
    fun addReview_toNetwork() {
        enqueueResponse("review.json", 200)
        val response = reviewService.addReviewService("HI333", MockResponseUtil.mockTheReview())
        val review: Review = response.blockingGet()
        assertEquals(review.productId, MockResponseUtil.mockTheReview().productId)
        assertEquals(
            review.rating,
            MockResponseUtil.mockTheReview().rating
        )
    }
}
