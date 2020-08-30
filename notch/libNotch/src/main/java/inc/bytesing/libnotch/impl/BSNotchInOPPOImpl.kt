package inc.bytesing.libnotch.impl

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import inc.bytesing.libnotch.IBSNotch


/**
 *
 *@author <a hrep = "mailto:lichuangxin@kugou.net">chuangxinli</a>
 *@date 2020/7/21
 *@copyright Copyright (c) 2020 licklee All rights reserved.
 *
 */
internal object BSNotchInOPPOImpl : IBSNotch{
    private const val TAG = "BSNotchInOPPOImpl"
    private var NOTCH_SIZE : IntArray? = null
    private var mOPPONotchStr = ""

    override fun hasNotch(context: Context): Boolean {
        return context.packageManager
            .hasSystemFeature("com.oppo.feature.screen.heteromorphism")
    }

    @SuppressLint("PrivateApi")
    override fun getNotchSize(context: Context): IntArray {
        if (NOTCH_SIZE == null){

        try {
            val cls = Class.forName("android.os.SystemProperties")
            val getMethod = cls.getMethod("get", String::class.java)
            mOPPONotchStr = getMethod.invoke(null, "ro.oppo.screen.heteromorphism") as String

        } catch (ignore: Throwable) {
            ignore.printStackTrace()
            if(IBSNotch.DEBUG)Log.e(TAG, "getNotchSize: error msg =  $ignore" )
        }
        if(IBSNotch.DEBUG)Log.d(TAG, "getNotchSize() in ro.oppo.screen.heteromorphism = $mOPPONotchStr")
        val resId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        val height = if( resId > 0){
            context.resources.getDimensionPixelSize(resId)
        }else 0

            NOTCH_SIZE = intArrayOf(0,height)
        }
        return NOTCH_SIZE!!
    }

    override fun getNotchHeight(context: Context): Int {
        return getNotchSize(context)[1]
    }

    /**
     * Oppo Android O机型不需要设置显示到刘海区域，只要设置了应用全屏就会默认显示。
     */
    override fun setDisplayInNotch(activity: Activity) {
        if(IBSNotch.DEBUG)Log.e(TAG, "setDisplayInNotch: Oppo Android O机型不需要设置显示到刘海区域，只要设置了应用全屏就会默认显示。")
    }

}