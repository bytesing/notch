package inc.bytesing.libnotch

import android.app.Activity
import android.content.Context


/**
 *
 *@author <a hrep = "mailto:lichuangxin@kugou.net">chuangxinli</a>
 *@date 2020/7/20
 *@copyright Copyright (c) 2020 licklee All rights reserved.
 *
 */
interface IBSNotch {
    companion object{
        const val  DEBUG = true
    }

    fun hasNotch(context: Context): Boolean
    fun getNotchSize(context: Context): IntArray
    fun getNotchHeight(context: Context): Int
    fun setDisplayInNotch(activity: Activity)
}