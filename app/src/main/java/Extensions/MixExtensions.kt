package Extensions

import android.view.View
import androidx.annotation.DimenRes
import com.google.android.material.datepicker.DateSelector

const val EMPTY_STRING = ""
const val MANUFACTURER = "manufacturer"
const val WHOLESELER = "wholeSeller"
const val RETAILER = "retailer"
const val SUPPLIER = "supplier"
const val TYPE_PLACE_HOLDER = "[TYPE]"
const val yes = "Yes"
const val no = "No"



private fun DateSelector<*>.unWrap() = 0f

fun Int?.unWrap(): Int = this ?: 0

fun View.dimenInInteger(@DimenRes dimenRes: Int) = dimen(dimenRes).toInt()

fun View.dimen(@DimenRes dimenRes: Int) = resources.getDimension(dimenRes)


fun View.visibility(show: Boolean = true, invisible: Boolean = false) {
    visibility = if (show) View.VISIBLE else if (invisible) View.INVISIBLE else View.GONE
}

fun String.capsFirstLetter() = this.substring(0, 1).toUpperCase() + this.substring(1).toLowerCase()


