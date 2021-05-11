package com.gratus.adidasconfirmed.ui.view.base

import android.animation.ValueAnimator
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.gratus.adidasconfirmed.databinding.SubHeaderProductListBinding
import com.gratus.adidasconfirmed.util.animator.CustomAnimator
import com.gratus.adidasconfirmed.util.interceptor.AppInterceptor
import com.gratus.adidasconfirmed.util.networkManager.NetworkOnlineCheck
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var mInterceptor: AppInterceptor

    @Inject
    lateinit var networkOnlineCheck: NetworkOnlineCheck

    fun isNetworkConnected(): Boolean {
        return networkOnlineCheck.isNetworkOnline
    }

    // open and hide of keyboard on visibility of search edit text
    fun openHideKeyboard(subHeader: SubHeaderProductListBinding, b: Boolean) {
        val parentWidth = (requireView().parent as View).measuredWidth - 50
        if (b) {
            subHeader.feedText.visibility = View.GONE
            subHeader.searchFab.visibility = View.GONE
            subHeader.viewSearch.searchLayout.visibility =
                View.VISIBLE
            val animator: ValueAnimator = CustomAnimator().slideAnimator(
                subHeader.viewSearch.searchLayout,
                0,
                parentWidth
            )
            animator.start()
            subHeader.viewSearch.searchEditText.text.clear()
            subHeader.viewSearch.searchEditText.requestFocus()
            (activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?)?.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                0
            )
        } else {
            subHeader.feedText.visibility = View.VISIBLE
            subHeader.viewSearch.searchLayout.visibility = View.GONE
            subHeader.searchFab.visibility = View.VISIBLE
            subHeader.viewSearch.searchEditText.text.clear()
            (activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(
                subHeader.viewSearch.searchCancelImage.applicationWindowToken,
                0
            )
        }
    }
}
