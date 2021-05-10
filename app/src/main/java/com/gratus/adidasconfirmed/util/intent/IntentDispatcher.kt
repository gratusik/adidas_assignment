package com.gratus.adidasconfirmed.util.intent

import android.app.Activity
import android.content.ComponentName
import android.content.pm.PackageManager
import androidx.core.app.ShareCompat
import com.gratus.adidasconfirmed.R
import com.gratus.adidasconfirmed.domain.model.remoteResponse.ProductListResponseItem
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.PATH
import com.gratus.adidasconfirmed.util.constants.AppConstants.Companion.TYPE
import javax.inject.Inject

// Share intent dispatcher will be called from comic card fragment
class IntentDispatcher @Inject constructor() {

    fun share(activity: Activity, productListResponseItem: ProductListResponseItem) {
        val packageManager = activity.applicationContext.packageManager
        val componentName = ComponentName(activity.packageName, "${activity.packageName}$PATH")
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        val shareText =
            activity.getString(R.string.share_info) + " - " + productListResponseItem.imgUrl

        ShareCompat.IntentBuilder.from(activity)
            .setType(TYPE)
            .setChooserTitle(activity.getString(R.string.share_title))
            .setText(shareText)
            .startChooser()
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}
