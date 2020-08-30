package inc.bytesing.libnotch.impl

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.DisplayCutout
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import inc.bytesing.libnotch.IBSNotch


/**
 *
 *@author <a hrep = "mailto:lichuangxin@kugou.net">chuangxinli</a>
 *@date 2020/7/22
 *@copyright Copyright (c) 2020 licklee All rights reserved.
 *
 */
@RequiresApi(Build.VERSION_CODES.P)
internal object BSNotchInPImpl : IBSNotch {
    private const val TAG = "BSNotchInPImpl"
    override fun hasNotch(context: Context): Boolean {
        if (!hasPProperty()) {
            return false
        }
        var activity = context as? Activity
        val displayCutout: DisplayCutout? = activity?.window?.decorView?.rootWindowInsets?.displayCutout
        if(IBSNotch.DEBUG)Log.d(TAG, "hasNotch: safeInsetTop=($displayCutout?.safeInsetTop)")
        getNotchSize(context)
        return (displayCutout?.safeInsetTop ?: 0) > 0;
    }

    override fun getNotchSize(context: Context): IntArray {
        val notchSize = intArrayOf(0,0)
        if (!hasPProperty()) {
            return notchSize
        }

        val activity  =  context as? Activity
        val displayCutout : DisplayCutout? = activity?.window?.decorView?.rootWindowInsets?.displayCutout
        displayCutout?.safeInsetTop?.let {
            notchSize[1] = it
            notchSize[0] = displayCutout.safeInsetRight - displayCutout.safeInsetLeft
            Log.d(TAG, "getNotchSize: left = "
                    +displayCutout.safeInsetLeft
                    +",top="+it
                    +",right="+displayCutout.safeInsetRight
                    +",bottom="+displayCutout.safeInsetBottom
            )
        }
        return  notchSize
    }

    override fun getNotchHeight(context: Context): Int {
        return getNotchSize(context)[1]
    }

    override fun setDisplayInNotch(activity: Activity) {
        if(!hasPProperty()){
            return
        }
        try {
            val window = activity.window
            // 延伸显示区域到耳朵区
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
            // 允许内容绘制到耳朵区
            val decorView = window.decorView
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        } catch (e: Throwable) {
            if(IBSNotch.DEBUG) Log.e(TAG, "setDisplayInNotch failure msg:$e")
        }
    }
    fun hasPProperty():Boolean{
        val hasPProperty =  Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        if(IBSNotch.DEBUG) Log.d(TAG, "hasPProperty:  SDK_INT = "+Build.VERSION.SDK_INT+",result = $hasPProperty")
        return hasPProperty
    }
}