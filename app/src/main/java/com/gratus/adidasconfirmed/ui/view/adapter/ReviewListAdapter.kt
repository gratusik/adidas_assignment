package com.gratus.adidasconfirmed.ui.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gratus.adidasconfirmed.databinding.ItemReviewBinding
import com.gratus.adidasconfirmed.domain.model.remoteResponse.Review
import com.gratus.adidasconfirmed.ui.view.base.BaseViewHolder
import com.gratus.adidasconfirmed.ui.viewmodel.adapter.ReviewListItemViewModel
import javax.inject.Inject

class ReviewListAdapter @Inject constructor(private var reviews: ArrayList<Review>) :
    RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val itemReviewBinding: ItemReviewBinding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ReviewListViewHolder(itemReviewBinding, reviews)
    }

    // View Holder 
    class ReviewListViewHolder(
        itemReviewBinding: ItemReviewBinding,
        private val reviews: ArrayList<Review>
    ) :
        BaseViewHolder(itemReviewBinding.root) {
        private val mBinding: ItemReviewBinding = itemReviewBinding
        private var reviewListItemViewModel: ReviewListItemViewModel? = null
        override fun clear() {
            TODO("Not yet implemented")
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onBind(position: Int) {
            reviewListItemViewModel =
                ReviewListItemViewModel(reviews[position])
            mBinding.reviewListItemViewModel = reviewListItemViewModel
        }
    }

    // Bind data to view holder
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    // notify data change every time data has been added or removed
    fun updateReviewListAdapter(review: ArrayList<Review>) {
        reviews.clear()
        reviews.addAll(review)
        notifyDataSetChanged()
    }

    // notify data change every time data has been added or removed
    fun addReviewListAdapter(review: Review) {
        reviews.add(0, review)
        notifyDataSetChanged()
    }
}
