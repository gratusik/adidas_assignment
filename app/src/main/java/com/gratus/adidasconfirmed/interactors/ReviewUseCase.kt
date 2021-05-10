package com.gratus.adidasconfirmed.interactors

import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import com.gratus.adidasconfirmed.domain.repository.ReviewRepository
import com.gratus.adidasconfirmed.interactors.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

// get product details use case which interact with view model and repository to get data
class ReviewUseCase @Inject constructor(private val repository: ReviewRepository) :
    SingleUseCase<Review>() {

    private var review: Review = Review.empty()

    fun getReview(rev: Review) {
        review = rev
    }

    override fun buildUseCaseSingle(): Single<Review> {
        return repository.addReview(review)
    }
}
