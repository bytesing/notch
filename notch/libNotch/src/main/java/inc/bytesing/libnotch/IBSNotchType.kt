package inc.bytesing.libnotch

import androidx.annotation.IntDef


/**
 *
 *@author <a hrep = "mailto:lichuangxin@kugou.net">chuangxinli</a>
 *@date 2020/7/20
 *@copyright Copyright (c) 2020 licklee All rights reserved.
 *
 */
@IntDef(
    IBSNotchType.INVALID,
    IBSNotchType.NO_NOTCH,
    IBSNotchType.P,
    IBSNotchType.HUAWEI,
    IBSNotchType.OPPP,
    IBSNotchType.VIVO,
    IBSNotchType.MIUI
)
@Retention(AnnotationRetention.SOURCE)
annotation class IBSNotchType {
    companion object{
        const val  INVALID= -1
        const val  NO_NOTCH = 0
        const val  P = 1
        const val  HUAWEI = 2
        const val  OPPP = 3
        const val  VIVO = 4
        const val  MIUI = 5
    }
}