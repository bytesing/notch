package inc.bytesing.libnotch

import android.annotation.SuppressLint
import android.util.Log


/**
 *
 *@author <a hrep = "mailto:lichuangxin@kugou.net">chuangxinli</a>
 *@date 2020/7/22
 *@copyright Copyright (c) 2020 licklee All rights reserved.
 *
 */
@SuppressLint("PrivateApi")
object BSSystemPropertiesCompat {
    private const val TAG = "BSSystemProCompat"

    fun <T> get(key: String, fall:T ) : T{
        var value  = fall
        try {
            val cls = Class.forName("android.os.SystemProperties")
            val getMethod = cls.getMethod("get", String::class.java)
            val ret = getMethod.invoke(null, key) as? T
            value = ret ?: value
        }catch (ignore: Throwable){
            ignore.printStackTrace()
        }
        Log.d(TAG, "get: $key=$value,fall=$fall")
        return value
    }
}