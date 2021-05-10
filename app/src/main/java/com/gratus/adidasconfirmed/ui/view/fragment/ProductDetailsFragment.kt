package com.gratus.adidasconfirmed.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.gratus.adidasconfirmed.R
import com.gratus.adidasconfirmed.databinding.FragmentProductDetailsBinding
import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import com.gratus.adidasconfirmed.ui.view.adapter.ReviewListAdapter
import com.gratus.adidasconfirmed.ui.view.base.BaseFragment
import com.gratus.adidasconfirmed.ui.viewmodel.fragment.ProductDetailsViewModel
import com.gratus.adidasconfirmed.ui.viewmodel.state.ProductDetailsState
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.PRODUCT_ID
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.REVIEW_DATA
import com.gratus.adidasconfirmed.util.constants.ServiceConstants
import com.gratus.adidasconfirmed.util.intent.IntentDispatcher
import javax.inject.Inject

open class ProductDetailsFragment : BaseFragment() {
    private lateinit var fragmentProductDetailsBinding: FragmentProductDetailsBinding

    private lateinit var productDetailsViewModel: ProductDetailsViewModel

    @Inject
    lateinit var reviewListAdapter: ReviewListAdapter

    @Inject
    lateinit var intentDispatcher: IntentDispatcher

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    // initialize review to update on add review 
    var review: Review = Review.empty()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentProductDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
        return fragmentProductDetailsBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productDetailsViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(ProductDetailsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(getProductId())
        setReviewListAdapter()
        // navigate back to product  list page
        fragmentProductDetailsBinding.headerDetails.backImage.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
        // retry button on network or server problem
        fragmentProductDetailsBinding.expCard.retry.setOnClickListener {
            init(getProductId())
        }
        // share button to share product to others
        fragmentProductDetailsBinding.headerDetails.shareImage.setOnClickListener {
            intentDispatcher.share(
                requireActivity(),
                productDetailsViewModel.productDetails.value!!
            )
        }
        // toggle button to insert and delete favourite products in room db
        fragmentProductDetailsBinding.headerDetails.favoriteButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                productDetailsViewModel.insertProductDetails(getProductId())
            } else {
                productDetailsViewModel.deleteProductDetails(getProductId())
            }
        }
        // on click move to review bottom modal fragment to write review
        // wait for result to update in product details page the review and average rating
        fragmentProductDetailsBinding.bottomView.setOnClickListener {
            setFragmentResultListener(ReviewBottomModelFragment.REQUEST_KEY) { key, bundle ->
                review = bundle.getParcelable(REVIEW_DATA)!!
                reviewListAdapter.addReviewListAdapter(review)
                productDetailsViewModel.productDetails.value?.reviews?.add(0, review)
                fragmentProductDetailsBinding.productDetailsItem.ratingText.text = String.format(
                    "%.1f",
                    productDetailsViewModel.productDetails.value?.getAverageRating()
                )
            }
            val bundle = bundleOf(
                PRODUCT_ID to getProductId(),
            )
            val navController = Navigation.findNavController(view)
            navController.navigate(
                R.id.action_productDetailsFragment_to_reviewBottomModelFragment,
                bundle
            )
        }
        fragmentProductDetailsBinding.productDetailsViewModel = productDetailsViewModel
        fragmentProductDetailsBinding.lifecycleOwner = this
    }

    private fun getProductId(): String {
        val productId = arguments?.getString(PRODUCT_ID)
        if (productId != null) {
            return productId
        }
        return productId.toString()
    }

    // init to get product details from remote server 
    private fun init(productId: String) {
        if (isNetworkConnected()) {
            fragmentProductDetailsBinding.progressBar.visibility = View.VISIBLE
            mInterceptor.setInterceptor(ServiceConstants.BASE_URL)
            productDetailsViewModel.getProductDetails(productId)
            observerProductDetailsPage()
        } else {
            exceptionLayoutVisibility(getString(R.string.network_offline))
        }
    }

    private fun observerProductDetailsPage() {
        productDetailsViewModel.productDetailsState.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is ProductDetailsState.Loading -> {
                        if (!it.loading) {
                            fragmentProductDetailsBinding.progressBar.visibility = View.GONE
                        }
                    }
                    is ProductDetailsState.ProductDetailsSuccess -> {
                        exceptionLayoutGone()
                        setUpProductDetailsPage()
                        reviewListAdapter.updateReviewListAdapter(it.details.reviews)
                    }
                    is ProductDetailsState.Error -> {
                        exceptionLayoutVisibility(getString(R.string.server_error))
                    }
                }
            }
        )
    }

    private fun setUpProductDetailsPage() {
        productDetailsViewModel.getProductDetailsCache(getProductId()).observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    fragmentProductDetailsBinding.headerDetails.favoriteButton.isChecked = true
                }
            }
        )
        fragmentProductDetailsBinding.appBarLayout.addOnOffsetChangedListener(object :
                OnOffsetChangedListener {
                var scrollRange = -1
                override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.totalScrollRange
                    }
                    if (scrollRange + verticalOffset == 0) {
                        fragmentProductDetailsBinding.headerDetails.productText.visibility =
                            View.VISIBLE
                    } else {
                        fragmentProductDetailsBinding.headerDetails.productText.visibility = View.GONE
                    }
                }
            })
    }

    // setting up the review list adapter
    private fun setReviewListAdapter() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        fragmentProductDetailsBinding.reviewRv.layoutManager = linearLayoutManager
        fragmentProductDetailsBinding.reviewRv.itemAnimator = DefaultItemAnimator()
        fragmentProductDetailsBinding.reviewRv.adapter = reviewListAdapter
    }

    // Visibility status of exception on network connection
    private fun exceptionLayoutVisibility(errorString: String) {
        fragmentProductDetailsBinding.progressBar.visibility = View.GONE
        fragmentProductDetailsBinding.appBarLayout.visibility = View.GONE
        fragmentProductDetailsBinding.reviewRv.visibility = View.GONE
        fragmentProductDetailsBinding.bottomView.visibility = View.GONE
        fragmentProductDetailsBinding.expCard.rootExpLayout.visibility = View.VISIBLE
        fragmentProductDetailsBinding.expCard.expText.text = errorString
        fragmentProductDetailsBinding.expCard.retry.visibility = View.VISIBLE
    }

    private fun exceptionLayoutGone() {
        fragmentProductDetailsBinding.expCard.rootExpLayout.visibility = View.GONE
        fragmentProductDetailsBinding.expCard.retry.visibility = View.GONE
        fragmentProductDetailsBinding.appBarLayout.visibility = View.VISIBLE
        fragmentProductDetailsBinding.reviewRv.visibility = View.VISIBLE
        fragmentProductDetailsBinding.bottomView.visibility = View.VISIBLE
    }
}
