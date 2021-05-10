package com.gratus.adidasconfirmed.ui.viewmodel.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gratus.adidasconfirmed.domain.repository.ReviewRepository
import com.gratus.adidasconfirmed.interactors.ReviewUseCase
import com.gratus.adidasconfirmed.ui.viewmodel.state.ReviewState
import com.gratus.adidasconfirmed.util.MockResponseUtil
import com.gratus.adidasconfirmed.util.RxSchedulerRule
import io.reactivex.Single
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
class ReviewViewModelTest : TestCase() {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var reviewRepository: ReviewRepository

    private lateinit var reviewUseCase: ReviewUseCase

    private lateinit var reviewViewModel: ReviewViewModel

    @Mock
    private lateinit var reviewState: Observer<ReviewState>

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        setUpUseCases()
        setUpViewModel()
    }

    private fun setUpUseCases() {
        reviewUseCase =
            ReviewUseCase(reviewRepository)
    }

    private fun setUpViewModel() {
        reviewViewModel = ReviewViewModel(
            reviewUseCase
        )
        reviewViewModel.reviewState.observeForever(reviewState)
    }

    @Test
    fun addReview_returnsError() {
        reviewUseCase.getReview(MockResponseUtil.mockTheReview())
        // Arrange
        Mockito.`when`(reviewUseCase.buildUseCaseSingle())
            .thenReturn(Single.error(Throwable("error")))

        // Act
        reviewViewModel.addNewReview(MockResponseUtil.mockTheReview())

        // Assert
        Mockito.verify(reviewState).onChanged(ReviewState.Loading(true))
        Mockito.verify(reviewState).onChanged(ReviewState.Error("error"))
        Mockito.verify(reviewState).onChanged(ReviewState.Loading(false))
    }

    @Test
    fun addReview_returnsResponse() {
        reviewUseCase.getReview(MockResponseUtil.mockTheReview())
        // Arrange
        Mockito.`when`(reviewUseCase.buildUseCaseSingle())
            .thenReturn(Single.just(MockResponseUtil.mockTheReview()))

        // Act
        reviewViewModel.addNewReview(MockResponseUtil.mockTheReview())

        // Assert
        Mockito.verify(reviewState).onChanged(ReviewState.Loading(true))
        Mockito.verify(reviewState)
            .onChanged(ReviewState.ReviewSuccess(MockResponseUtil.mockTheReview()))
        Mockito.verify(reviewState).onChanged(ReviewState.Loading(false))
    }
}
