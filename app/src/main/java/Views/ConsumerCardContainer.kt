package Views

import Extensions.dimenInInteger
import Model.Supplier
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.android.supplychainapp.R

class ConsumerCardContainer @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defStyleRes: Int = 0
) : LinearLayout(context, attributeSet, defStyleRes) {


    init {
        orientation = VERTICAL
    }

    fun addInfoCards(productInfoList: List<Supplier>) {
        removeAllViews()
        productInfoList.forEachIndexed { index , productItemInfo ->
            addView(ProductCardView(context).apply {
                setDataForProductInfoCard(productItemInfo)

            })
            addView(View(context).apply {
                if (index != productInfoList.size - 1 ) {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dimenInInteger(R.dimen.large_space)
                    )
                }
            })
        }
    }
}