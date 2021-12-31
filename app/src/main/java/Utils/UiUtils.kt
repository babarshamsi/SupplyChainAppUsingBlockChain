package Utils

import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat

class UiUtils {


    companion object {

        val UI_TAG = "UI"

        fun isValidDrawableRes(context: Context?, @DrawableRes id: Int): Boolean {
            return try {
                ContextCompat.getDrawable(context!!, id)
                true
            } catch (e: Exception) {
                Log.d(UI_TAG, "invalid resId")
                false
            }
        }


        fun isValidLayoutRes(context: Context?, @LayoutRes id: Int): Boolean {
            return try {
                context?.resources?.getLayout(id)
                true
            } catch (e: java.lang.Exception) {
                Log.d(UI_TAG, "invalid layoutResId")
                false
            }
        }

    }

}