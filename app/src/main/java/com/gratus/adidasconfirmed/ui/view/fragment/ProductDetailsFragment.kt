package com.gratus.adidasconfirmed.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gratus.adidasconfirmed.R

class ProductDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(): ProductDetailsFragment {
            return ProductDetailsFragment()
        }
    }
}
