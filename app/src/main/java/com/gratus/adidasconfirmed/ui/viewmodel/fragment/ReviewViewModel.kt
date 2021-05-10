package com.gratus.adidasconfirmed.ui.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import com.gratus.adidasconfirmed.interactors.ReviewUseCase
import com.gratus.adidasconfirmed.ui.viewmodel.state.ReviewState
import javax.inject.Inject

class ReviewViewModel @Inject constructor(
    private val reviewUseCase: ReviewUseCase
) : ViewModel() {
    val reviewState = MutableLiveData<ReviewState>()

    // interact with model to get the data - product detail
    fun addNewReview(review: Review) {
        reviewState.value = ReviewState.Loading(true)
        reviewUseCase.getReview(review)
        reviewUseCase.execute(
            onSuccess = {
                reviewState.value = ReviewState.ReviewSuccess(it)
                reviewState.value = ReviewState.Loading(false)
            },
            onError = {
                reviewState.value = ReviewState.Error(it.localizedMessage)
                reviewState.value = ReviewState.Loading(false)
            }
        )
    }
}
