package inc.bytesing.libnotch.impl

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.Window
import inc.bytesing.libnotch.IBSNotch


/**
 *
 *@author <a hrep = "mailto:lichuangxin@kugou.net">chuangxinli</a>
 *@date 2020/7/21
 *@copyright Copyright (c) 2020 licklee All rights reserved.
 *
 */
internal object BSNotchInMIUIImpl : IBSNotch{
    private const val TAG = "BSNotchInMIUIImpl"

    @SuppressLint("PrivateApi")
    override fun hasNotch(context: Context): Boolean {
        var  notch = 0;
        try {
            notch = Class.forName("android.os.SystemProperties")
                .getMethod("getInt", String::class.java,Int::class.javaPrimitiveType)
                .invoke(null,"ro.miui.notch",0) as Int
        }catch (ignore:Throwable){
            if(IBSNotch.DEBUG) Log.e(TAG, "hasNotch error msg=$ignore")
        }finally {
            return notch == 1
        }
    }

    override fun getNotchSize(context: Context): IntArray {
        var notchWidth = 0
        var  notchHeight = 0
        try {
            var resourceId =
                context.resources.getIdentifier("notch_height", "dimen", "android")
            notchHeight = if(resourceId > 0){
                context.resources.getDimensionPixelSize(resourceId)
            }else notchWidth

            resourceId = context.resources.getIdentifier("notch_width", "dimen", "android")
            notchWidth = if(resourceId > 0){
                context.resources.getDimensionPixelSize(resourceId)
            }else notchHeight
        }catch (ignore : Throwable){
            if(IBSNotch.DEBUG) Log.e(TAG, "getNotchSize error msg=$ignore")
        }finally {
            return intArrayOf(notchWidth,notchHeight)
        }

    }

    override fun getNotchHeight(context: Context): Int {
        return getNotchSize(context)[1]
    }


    override fun setDisplayInNotch(activity: Activity) {
        val flag =  0x00000100 or 0x00000200 or 0x00000400
        setDisplayInNotch(activity,flag)
    }

    /**
     * 设置显示到刘海区域
     * 0x00000100 | 0x00000200 竖屏绘制到耳朵区
     * 0x00000100 | 0x00000400 横屏绘制到耳朵区
     * 0x00000100 | 0x00000200 | 0x00000400 横竖屏都绘制到耳朵区
     */
    fun setDisplayInNotch(activity: Activity, flag: Int){

        try {
            val method = Window::class.java.getMethod("addExtraFlags",
                Int::class.javaPrimitiveType)
            method.invoke(activity.window,flag)
        }catch (ignore : Throwable){
            if(IBSNotch.DEBUG) Log.e(TAG, "setDisplayInNotch error msg=$ignore")
        }
    }
}