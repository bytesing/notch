package inc.bytesing.libnotch.impl

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import inc.bytesing.libnotch.IBSNotch
import kotlin.math.sign


/**
 *
 *@author <a hrep = "mailto:lichuangxin@kugou.net">chuangxinli</a>
 *@date 2020/7/22
 *@copyright Copyright (c) 2020 licklee All rights reserved.
 *
 */
internal object BSNotchInViVoImpl : IBSNotch{
    private const val VIVO_NOTCH = 0x00000020 //是否有刘海
    private const val VIVO_FILLET = 0x00000008 //是否有圆角
    private const val TAG = "BSNotchInViVoImpl"
    @SuppressLint("PrivateApi")
    override fun hasNotch(context: Context): Boolean {
        var ret = false
        try {
            val classLoader = context.classLoader
            val FtFeature = classLoader.loadClass("android.util.FtFeature")
            val method =
                FtFeature.getMethod("isFeatureSupport", Int::class.javaPrimitiveType)
            ret = method.invoke(FtFeature, VIVO_NOTCH) as Boolean
        }catch (ignore: Throwable){
            if(IBSNotch.DEBUG) Log.e(TAG, "hasNotch error msg=$ignore")
        }finally {
            return ret
        }
    }

    override fun getNotchSize(context: Context): IntArray {
        var notchSize = intArrayOf(0,0)
        if(!hasNotch(context)){
            return notchSize
        }

        val resId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        notchSize[0]  = if (resId > 0) {
            context.resources.getDimensionPixelSize(resId)
        } else 0

        return notchSize
    }

    override fun getNotchHeight(context: Context): Int {
        return if(hasNotch(context)){
            getNotchSize(context)[1]
        }else 0
    }

    override fun setDisplayInNotch(activity: Activity) {
        if(IBSNotch.DEBUG)Log.e(TAG, "setDisplayInNotch: 至于vivo，vivo给了判断是否刘海屏的API，但是没用设置刘海区域显示到API，因此无需适配。")
    }
}