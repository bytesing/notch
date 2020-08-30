package inc.bytesing.libnotch

import android.app.Activity
import android.content.Context
import android.util.Log
import inc.bytesing.libnotch.impl.BSNotchInHuaWeiImpl
import inc.bytesing.libnotch.impl.BSNotchInMIUIImpl
import inc.bytesing.libnotch.impl.BSNotchInOPPOImpl
import inc.bytesing.libnotch.impl.BSNotchInPImpl
import inc.bytesing.libnotch.impl.BSNotchInViVoImpl


/**
 *
 *@author <a hrep = "mailto:lichuangxin@kugou.net">chuangxinli</a>
 *@date 2020/7/22
 *@copyright Copyright (c) 2020 licklee All rights reserved.
 *
 */
object BSNotchCompat : IBSNotch {
    private const val TAG = "BSNotchCompat"
    @IBSNotchType
    private var NOTCH_TYPE = IBSNotchType.INVALID
    private var mINotch : IBSNotch? = null;
    private var mNotchSize : IntArray? = null;
    private val EMPTY_NOTCH_SIZE = intArrayOf(0,0)
    override fun hasNotch(context: Context): Boolean {
        val notch = getNotch(context)
        return notch?.hasNotch(context) ?: false;
    }

    override fun getNotchSize(context: Context): IntArray {
        val notch = getNotch(context)
        return notch?.getNotchSize(context) ?: EMPTY_NOTCH_SIZE;
    }

    override fun getNotchHeight(context: Context): Int {
        return getNotchSize(context)[1]
    }

    override fun setDisplayInNotch(activity: Activity) {
        val notch = getNotch(activity)
        notch?.setDisplayInNotch(activity)
    }

    @IBSNotchType
    private fun getNotchType(context: Context): Int {
        if (NOTCH_TYPE != IBSNotchType.INVALID) {
            return NOTCH_TYPE
        }
        NOTCH_TYPE = if (BSNotchInPImpl.hasNotch(context)) {
            IBSNotchType.P
        } else if (BSNotchInHuaWeiImpl.hasNotch(context)) {
            IBSNotchType.HUAWEI
        } else if (BSNotchInOPPOImpl.hasNotch(context)) {
            IBSNotchType.OPPP
        } else if (BSNotchInViVoImpl.hasNotch(context)) {
            IBSNotchType.VIVO
        } else if (BSNotchInMIUIImpl.hasNotch(context)) {
            IBSNotchType.MIUI
        } else {
            IBSNotchType.NO_NOTCH
        }
        if(IBSNotch.DEBUG) Log.d(TAG, "getNotchType: $NOTCH_TYPE")
        return NOTCH_TYPE

    }

    private fun getNotch(context: Context) : IBSNotch? {
        if(NOTCH_TYPE != IBSNotchType.INVALID){
            return mINotch
        }
        mINotch = when(getNotchType(context)){
            IBSNotchType.P -> BSNotchInPImpl
            IBSNotchType.HUAWEI -> BSNotchInHuaWeiImpl
            IBSNotchType.OPPP -> BSNotchInOPPOImpl
            IBSNotchType.VIVO -> BSNotchInViVoImpl
            IBSNotchType.MIUI -> BSNotchInMIUIImpl
            else -> null
        }
        return mINotch
    }
}