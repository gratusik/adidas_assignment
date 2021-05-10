package com.gratus.adidasconfirmed.ui.viewmodel.state

import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review

sealed class ReviewState {
    data class Loading(var loading: Boolean) : ReviewState()
    data class Error(var message: String) : ReviewState()
    data class ReviewSuccess(val review: Review) :
        ReviewState()
}
