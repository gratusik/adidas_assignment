package com.gratus.adidasconfirmed.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_FORCED
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.gratus.adidasconfirmed.R
import com.gratus.adidasconfirmed.databinding.FragmentProductListBinding
import com.gratus.adidasconfirmed.ui.view.adapter.ProductListAdapter
import com.gratus.adidasconfirmed.ui.view.base.BaseFragment
import com.gratus.adidasconfirmed.ui.viewmodel.fragment.ProductListViewModel
import com.gratus.adidasconfirmed.ui.viewmodel.state.ProductListState
import com.gratus.adidasconfirmed.util.Interfaces.ProductListListener
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.PRODUCT_ID
import com.gratus.adidasconfirmed.util.constants.ServiceConstants
import javax.inject.Inject

class ProductListFragment : BaseFragment(), ProductListListener {

    private lateinit var fragmentProductListBinding: FragmentProductListBinding

    private lateinit var productListViewModel: ProductListViewModel

    @Inject
    lateinit var productListAdapter: ProductListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productListViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(ProductListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentProductListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        return fragmentProductListBinding.root
    }

    // init to get product list from remote server 
    private fun init() {
        if (isNetworkConnected()) {
            fragmentProductListBinding.progressBar.visibility = View.VISIBLE
            mInterceptor.setInterceptor(ServiceConstants.BASE_URL)
            productListViewModel.getProductList()
            setUpProductListPage()
        } else {
            exceptionLayoutVisibility(getString(R.string.network_offline))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // search visibility - visible on click search fab
        fragmentProductListBinding.subHeader.searchFab.setOnClickListener {
            openHideKeyboard(
                fragmentProductListBinding.subHeader,
                true
            )
        }
        // search visibility - gone on click of close image
        fragmentProductListBinding.subHeader.viewSearch.searchCancelImage.setOnClickListener {
            openHideKeyboard(
                fragmentProductListBinding.subHeader,
                false
            )
        }
        fragmentProductListBinding.subHeader.viewSearch.searchEditText.addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    productListViewModel.onTextChanged(s)
                }

                override fun afterTextChanged(s: Editable) {
                }
            })
        fragmentProductListBinding.expCard.retry.setOnClickListener {
            init()
        }
        if (productListViewModel.productListState.value == null) {
            init()
        } else {
            setUpProductListPage()
        }
        setProductListAdapter()
        fragmentProductListBinding.productListViewModel = productListViewModel
        fragmentProductListBinding.lifecycleOwner = this
    }

    // observe for the product list data from remote server using live data
    private fun setUpProductListPage() {
        productListViewModel.productListState.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is ProductListState.Loading -> {
                        if (!it.loading) {
                            fragmentProductListBinding.progressBar.visibility = View.GONE
                        }
                    }
                    is ProductListState.ProductListSuccess -> {
                        if (it.list.size > 0) {
                            exceptionLayoutGone()
                            productListAdapter.updateProductListAdapter(it.list)
                        } else {
                            exceptionLayoutVisibility(getString(R.string.no_data_found))
                        }
                    }
                    is ProductListState.Error -> {
                        exceptionLayoutVisibility(getString(R.string.server_error))
                    }
                }
            }
        )
    }

    // setting up the product list adapter
    private fun setProductListAdapter() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        fragmentProductListBinding.productListRv.layoutManager = linearLayoutManager
        fragmentProductListBinding.productListRv.itemAnimator = DefaultItemAnimator()
        fragmentProductListBinding.productListRv.adapter = productListAdapter
        productListAdapter.setListener(ProductListFragment())
    }

    // Visibility status of exception on network connection
    private fun exceptionLayoutVisibility(errorString: String) {
        fragmentProductListBinding.progressBar.visibility = View.GONE
        fragmentProductListBinding.productListRv.visibility = View.GONE
        fragmentProductListBinding.expCard.rootExpLayout.visibility = View.VISIBLE
        fragmentProductListBinding.expCard.expText.text = errorString
        if (fragmentProductListBinding.subHeader.viewSearch.searchLayout.isVisible) {
            fragmentProductListBinding.subHeader.subHeaderLayout.visibility = View.VISIBLE
            fragmentProductListBinding.expCard.retry.visibility = View.GONE
        } else {
            fragmentProductListBinding.expCard.retry.visibility = View.VISIBLE
            fragmentProductListBinding.subHeader.subHeaderLayout.visibility = View.GONE
        }
    }

    private fun exceptionLayoutGone() {
        fragmentProductListBinding.expCard.rootExpLayout.visibility = View.GONE
        fragmentProductListBinding.expCard.retry.visibility = View.GONE
        fragmentProductListBinding.productListRv.visibility = View.VISIBLE
        fragmentProductListBinding.subHeader.subHeaderLayout.visibility = View.VISIBLE
    }

    // on item click in recycler view an get product id to move the product details fragment 
    override fun onItemClick(view: View, productId: String) {
        if (view.requestFocus()) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(SHOW_FORCED, 0)
        }
        val bundle = bundleOf(
            PRODUCT_ID to productId,
        )
        val navController = Navigation.findNavController(view)
        navController.navigate(
            R.id.action_productListFragment_to_productDetailsFragment,
            bundle
        )
    }
}
