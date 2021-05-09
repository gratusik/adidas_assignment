package com.gratus.adidasconfirmed.util.bindingUtil

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingUtils {
    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(imageView: ImageView, imageDir: String?) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(imageDir)
            .into(imageView)
    }
}
