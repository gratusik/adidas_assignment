package com.gratus.adidasconfirmed.util.animator

import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout

class CustomAnimator {
    // slide animation for search
    fun slideAnimator(layout: CoordinatorLayout, start: Int, end: Int): ValueAnimator {
        val animator = ValueAnimator.ofInt(start, end)
        animator.duration = 500
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { valueAnimator -> // Update Width
            val value = valueAnimator.animatedValue as Int
            val layoutParams = layout.layoutParams
            layoutParams.width = value
            layout.layoutParams = layoutParams
        }
        return animator
    }
}
