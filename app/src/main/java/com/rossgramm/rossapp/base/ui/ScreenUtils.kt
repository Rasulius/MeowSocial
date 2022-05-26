package com.rossgramm.rossapp.base.ui

import android.content.res.Resources

object ScreenUtils {

    @JvmStatic
    fun dpToPx(dp: Float): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()

    @JvmStatic
    fun pxToDp(px: Int): Int = (px / Resources.getSystem().displayMetrics.density).toInt()

}