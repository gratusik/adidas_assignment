package com.gratus.adidasconfirmed.util.bindingUtil

import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.gratus.adidasconfirmed.R

object BindingUtils {
    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(imageView: ImageView, imageDir: String?) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(imageDir)
            .into(imageView)
    }

    // review list in product details page 
    // change rating background color based on rating
    @JvmStatic
    @BindingAdapter("setLinearBackground")
    fun setLinearBackground(linearLayout: LinearLayout, rating: Int) {
        when {
            rating >= 4 -> {
                linearLayout.setBackgroundResource(R.drawable.ratings_green_background)
            }
            rating >= 2 -> {
                linearLayout.setBackgroundResource(R.drawable.ratings_yellow_background)
            }
            else -> {
                linearLayout.setBackgroundResource(R.drawable.ratings_red_background)
            }
        }
    }
}
