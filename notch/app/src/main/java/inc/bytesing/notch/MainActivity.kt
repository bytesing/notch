package inc.bytesing.notch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
//import com.bytesing.andart.notch.NotchCompat
import com.kugou.shortvideoapp.R

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }
    private lateinit var mTv : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTv = findViewById(R.id.text)
//        val hashNotch = NotchCompat.hasNotch(this)
//        val notchHeight = NotchCompat.getNotchHeight(this)
//        val notchSize = NotchCompat.getNotchSize(this)

//        val msg = " hashNotch=$hashNotch,notchHeight=$notchHeight,notchSize="+ notchSize.contentToString()
//        mTv.text = msg
//        Log.e(TAG, msg)
    }
}
