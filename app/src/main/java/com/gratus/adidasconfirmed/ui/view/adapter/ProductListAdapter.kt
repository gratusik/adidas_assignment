package com.gratus.adidasconfirmed.ui.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gratus.adidasconfirmed.databinding.ItemProductListBinding
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.ui.view.base.BaseViewHolder
import com.gratus.adidasconfirmed.ui.view.fragment.ProductListFragment
import com.gratus.adidasconfirmed.ui.viewmodel.adapter.ProductListItemViewModel
import com.gratus.adidasconfirmed.util.Interfaces.ProductListListener
import com.gratus.adidasconfirmed.util.image.ImageLoaderUtil
import javax.inject.Inject

class ProductListAdapter @Inject constructor(private var productListResponseItem: ArrayList<ProductListResponseItem>) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var mListener: ProductListListener? = null

    // setting listener to item click in the adapter
    fun setListener(mListener: ProductListFragment) {
        this.mListener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val itemProductListBinding: ItemProductListBinding = ItemProductListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ProductListViewHolder(itemProductListBinding, mListener, productListResponseItem)
    }

    // View Holder 
    class ProductListViewHolder(
        itemProductListBinding: ItemProductListBinding,
        private val mListener: ProductListListener?,
        private val productListResponseItem: ArrayList<ProductListResponseItem>
    ) :
        BaseViewHolder(itemProductListBinding.root) {
        private val mBinding: ItemProductListBinding = itemProductListBinding
        private var productListItemViewModel: ProductListItemViewModel? = null
        override fun clear() {
            TODO("Not yet implemented")
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onBind(position: Int) {
            if (mListener != null) {
                productListItemViewModel =
                    ProductListItemViewModel(
                        productListResponseItem[position],
                        mListener,
                        mBinding.viewDetailsFab
                    )
            }
            mBinding.productListItemViewModel = productListItemViewModel
            ImageLoaderUtil().loadGlideIntoImageView(
                productListResponseItem[position].imgUrl,
                mBinding
            )
        }
    }

    // Bind data to view holder
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return productListResponseItem.size
    }

    // notify data change every time data has been added or removed
    fun updateProductListAdapter(updateProductListResponseItem: ArrayList<ProductListResponseItem>) {
        productListResponseItem.clear()
        productListResponseItem.addAll(updateProductListResponseItem)
        notifyDataSetChanged()
    }
}
