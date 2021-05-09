package com.gratus.adidasconfirmed.util.image

import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.gratus.adidasconfirmed.R
import com.gratus.adidasconfirmed.databinding.ItemProductListBinding

class ImageLoaderUtil {
    // using glide to load image from url and store as cache
    fun loadGlideIntoImageView(link: String, mBinding: ItemProductListBinding) {
        Glide.with(mBinding.productImage.context)
            .asBitmap()
            .load(link)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    mBinding.productImage.setImageBitmap(resource)
                    Palette.from(resource).generate {
                        val lightVibrant: Palette.Swatch? = it?.lightVibrantSwatch
                        if (lightVibrant != null) {
                            mBinding.productCard.setCardBackgroundColor(lightVibrant.rgb)
                        } else {
                            val lightVibrantMute: Palette.Swatch? = it?.lightMutedSwatch
                            if (lightVibrantMute != null) {
                                mBinding.productCard.setCardBackgroundColor(lightVibrantMute.rgb)
                            } else {
                                mBinding.productCard.setCardBackgroundColor(
                                    ContextCompat.getColor(
                                        mBinding.productCard.context,
                                        R.color.cardBackground
                                    )
                                )
                            }
                        }
                    }
                }
            })
    }
}
