package Views

import Extensions.*
import Model.Supplier
import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.android.supplychainapp.R
import kotlinx.android.synthetic.main.card_view_for_product_information.view.*


class ProductCardView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defStyleRes: Int = 0
) : CardView(context, attributeSet, defStyleRes) {


    init {
        inflate(context, R.layout.card_view_for_product_information, this)
        setBackgroundColor(context.resources.getColor(R.color.black))
    }

    private var type: String = EMPTY_STRING
    set(value) {
        field = value
        typeName?.text = type
    }

    private var supplier: String = EMPTY_STRING
        set(value) {
            field = value
            senderName?.text = supplier
        }

    private var retailer: String = EMPTY_STRING
        set(value) {
            field = value
            receiverName?.text = retailer
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
        setSenderAndReceiverTypes(product)
        type = product.type.capsFirstLetter()
        supplier = product.senderName
        retailer = product.receiverName
        pickUp = product.pickUpFrom
        delivery = product.deliverTo
        pickUptime = product.pickUpDate
        deliverTime = product.deliverDate
    }

    private fun setSenderAndReceiverTypes(product: Supplier) {
        if (product.type.equals(SUPPLIER)) {
            senderType?.text = MANUFACTURER.capsFirstLetter()
            receiverType?.text = SUPPLIER.capsFirstLetter()
        }
        else if (product.type.equals(WHOLESELER)) {
            senderType?.text = SUPPLIER.capsFirstLetter()
            receiverType?.text = WHOLESELER.capsFirstLetter()
        }
        else if (product.type.equals(RETAILER)) {
            senderType?.text = WHOLESELER.capsFirstLetter()
            receiverType?.text = RETAILER.capsFirstLetter()
        }
    }

}