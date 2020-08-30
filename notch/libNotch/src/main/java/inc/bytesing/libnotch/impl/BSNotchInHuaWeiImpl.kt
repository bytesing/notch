package inc.bytesing.libnotch.impl

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.WindowManager
import inc.bytesing.libnotch.IBSNotch


/**
 *
 *@author <a hrep = "mailto:lichuangxin@kugou.net">chuangxinli</a>
 *@date 2020/7/21
 *@copyright Copyright (c) 2020 licklee All rights reserved.
 *
 */
internal object BSNotchInHuaWeiImpl : IBSNotch{
    private const val TAG = "BSNotchInHuaWeiImpl"
    private const val FLAG_NOTCH_SUPPORT = 0x00010000

    override fun hasNotch(context: Context): Boolean {
        val ret = hwNotchSizeUtil(context,"hasNotchInScreen",false);
        return ret
    }

    override fun getNotchSize(context: Context): IntArray {
        return hwNotchSizeUtil(context,"getNotchSize", intArrayOf(0,0));
    }

    override fun getNotchHeight(context: Context): Int {
        return getNotchSize(context)[0]
    }

    override fun setDisplayInNotch(activity: Activity) {
        try {
            val layoutParams = activity.window.attributes
            val layoutParamsExCls =
                Class.forName("com.huawei.android.view.LayoutParamsEx")
            val con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams::class.java)
            val layoutParamsObj = con.newInstance(layoutParams)
            val method = layoutParamsExCls.getMethod("addHwFlags",Int::class.javaPrimitiveType)
            method.invoke(layoutParamsObj, FLAG_NOTCH_SUPPORT)
        }catch (ignore:Throwable){
            if(IBSNotch.DEBUG)Log.e(TAG, "setDisplayInNotch failure msg:$ignore")
        }
    }

    private fun <T> hwNotchSizeUtil(context: Context, methodName:String,fall:T): T{
        var ret:T = fall;
        try {
            val cl = context.classLoader
            val  hwNotchSizeUtil
                    = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val getMethod = hwNotchSizeUtil.getMethod(methodName)
            ret = getMethod.invoke(hwNotchSizeUtil) as T

        }catch (ignore:Throwable){
            if(IBSNotch.DEBUG) Log.e(TAG, "hasNotch error msg=$ignore")
        }finally {
            return ret
        }
    }

}