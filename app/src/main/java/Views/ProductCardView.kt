package Views

import Extensions.EMPTY_STRING
import Model.Supplier
import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.android.supplychainapp.R
import kotlinx.android.synthetic.main.activity_consumer.view.*

class ProductCardView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defStyleRes: Int = 0
) : CardView(context, attributeSet, defStyleRes) {


    init {
        inflate(context, R.layout.activity_consumer, this)
//        orientation = VERTICAL
    }

    private var supplier: String = EMPTY_STRING
        set(value) {
            field = value
            supplierName?.text = supplier
        }

    private var retailer: String = EMPTY_STRING
        set(value) {
            field = value
            retailerName?.text = retailer
        }

    private var pickUp: String = EMPTY_STRING
        set(value) {
            field = value
            pickUpFrom?.text = pickUp
        }

    private var delivery: String = EMPTY_STRING
        set(value) {
            field = value
            deliverTo?.text = delivery
        }

    private var pickUptime: String = EMPTY_STRING
        set(value) {
            field = value
            pickUpDate?.text = pickUptime
        }

    private var deliverTime: String = EMPTY_STRING
        set(value) {
            field = value
            deliverDate?.text = deliverTime
        }

    fun setDataForProductInfoCard(product: Supplier) {
        supplier = product.supplierName ?: EMPTY_STRING
        retailer = product.retailerName ?: EMPTY_STRING
        pickUp = product.pickUpFrom ?: EMPTY_STRING
        delivery = product.deliverTo ?: EMPTY_STRING
        pickUptime = product.pickUpDate ?: EMPTY_STRING
        deliverTime = product.deliverDate ?: EMPTY_STRING
    }

}