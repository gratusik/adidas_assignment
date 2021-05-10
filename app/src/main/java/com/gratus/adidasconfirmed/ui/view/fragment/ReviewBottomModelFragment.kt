package com.gratus.adidasconfirmed.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gratus.adidasconfirmed.R
import com.gratus.adidasconfirmed.databinding.FragmentReviewBottomModelBinding
import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import com.gratus.adidasconfirmed.ui.viewmodel.fragment.ReviewViewModel
import com.gratus.adidasconfirmed.ui.viewmodel.state.ReviewState
import com.gratus.adidasconfirmed.util.constants.AppConstants
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.REVIEW_DATA
import com.gratus.adidasconfirmed.util.constants.ServiceConstants
import com.gratus.adidasconfirmed.util.interceptor.AppInterceptor
import com.gratus.adidasconfirmed.util.networkManager.NetworkOnlineCheck
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.internal.Beta
import javax.inject.Inject

class ReviewBottomModelFragment : DaggerBottomSheetDialogFragment() {
    private lateinit var fragmentReviewBottomModelBinding: FragmentReviewBottomModelBinding

    private lateinit var reviewViewModel: ReviewViewModel

    @Inject
    lateinit var mInterceptor: AppInterceptor

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var networkOnlineCheck: NetworkOnlineCheck

    companion object {
        const val REQUEST_KEY = "Review_REQUEST_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        reviewViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(ReviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentReviewBottomModelBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_review_bottom_model,
                container,
                false
            )
        return fragmentReviewBottomModelBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentReviewBottomModelBinding.btnCancel.setOnClickListener {
            dismiss()
        }

        fragmentReviewBottomModelBinding.btnSubmit.setOnClickListener {
            if (networkOnlineCheck.isNetworkOnline) {
                mInterceptor.setInterceptor(ServiceConstants.REVIEW_SUBMIT_BASE_URL)
                val review = Review(
                    "en-US",
                    arguments?.getString(AppConstants.PRODUCT_ID),
                    fragmentReviewBottomModelBinding.ratingBar.rating.toInt(),
                    fragmentReviewBottomModelBinding.reviewEditText.text.toString(),
                )

                reviewViewModel.addNewReview(review)
            } else {
                DynamicToast.makeError(
                    this.requireContext(),
                    resources.getString(R.string.network_offline)
                ).show()
            }
        }
        observerReview()
        fragmentReviewBottomModelBinding.lifecycleOwner = this
    }

    // observe add review response
    private fun observerReview() {
        reviewViewModel.reviewState.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is ReviewState.Loading -> {
                    }
                    is ReviewState.ReviewSuccess -> {
                        setFragmentResult(REQUEST_KEY, bundleOf(REVIEW_DATA to it.review))
                        dismiss()
                    }
                    is ReviewState.Error -> {
                        DynamicToast.makeError(
                            this.requireContext(),
                            resources.getString(R.string.server_error)
                        ).show()
                    }
                }
            }
        )
    }
}

@Beta
open class DaggerBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
