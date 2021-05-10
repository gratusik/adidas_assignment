package com.gratus.adidasconfirmed.data.service.remote

import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.PRODUCT_ID
import com.gratus.adidasconfirmed.util.constants.ServiceConstants
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

// add review service
interface ReviewService {
    @POST(ServiceConstants.REVIEW_SUBMIT_URL)
    fun addReviewService(@Path(PRODUCT_ID) productId: String, @Body review: Review): Single<Review>
}
