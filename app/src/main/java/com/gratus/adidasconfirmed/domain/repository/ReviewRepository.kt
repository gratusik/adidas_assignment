package com.gratus.adidasconfirmed.domain.repository

import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import io.reactivex.Single

// repo to add Review
interface ReviewRepository {
    fun addReview(review: Review): Single<Review>
}
