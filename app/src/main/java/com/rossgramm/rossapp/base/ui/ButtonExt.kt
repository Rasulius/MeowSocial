package com.rossgramm.rossapp.base.ui

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.button.MaterialButton
import com.rossgramm.rossapp.R

fun MaterialButton.setShowProgress(isProgress: Boolean) {
    isEnabled = !isProgress
    icon = if (isProgress) {
        CircularProgressDrawable(context).apply {
            setStyle(CircularProgressDrawable.DEFAULT)
            setColorSchemeColors(ContextCompat.getColor(context, R.color.white))
            start()
        }
    } else null
    if (icon != null) {
        iconPadding = ScreenUtils.dpToPx(24F)
        icon.callback = object : Drawable.Callback {
            override fun unscheduleDrawable(who: Drawable, what: Runnable) {
            }

            override fun invalidateDrawable(who: Drawable) {
                this@setShowProgress.invalidate()
            }

            override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
            }
        }
    }
}
