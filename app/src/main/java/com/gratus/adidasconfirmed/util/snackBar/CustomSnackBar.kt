package com.gratus.adidasconfirmed.util.snackBar

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.gratus.adidasconfirmed.R

// show snack bar based on network connection boolean value
class CustomSnackBar {

    fun getSnackBarCustom(bottomNavigation: BottomNavigationView?, snackBar: Snackbar, view: View, b: Boolean, color: Int) {
        val textView =
            view.findViewById<View>(R.id.snackbar_text) as TextView
        if (b) {
            textView.setTextColor(Color.WHITE)
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackBar.setBackgroundTint(color)
        } else {
            textView.setTextColor(Color.YELLOW)
        }
        if (bottomNavigation != null) {
            snackBar.anchorView = bottomNavigation
        }
        snackBar.show()
    }
}
