package Extensions

import android.view.View
import androidx.annotation.DimenRes
import com.google.android.material.datepicker.DateSelector

private fun DateSelector<*>.unWrap() = 0f

fun Int?.unWrap(): Int = this ?: 0

fun View.dimenInInteger(@DimenRes dimenRes: Int) = dimen(dimenRes).toInt()

fun View.dimen(@DimenRes dimenRes: Int) = resources.getDimension(dimenRes)

const val EMPTY_STRING = ""

fun View.visibility(show: Boolean = true, invisible: Boolean = false) {
    visibility = if (show) View.VISIBLE else if (invisible) View.INVISIBLE else View.GONE
}


