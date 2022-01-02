package Utils

import Utils.SingletonForProduct.isManufacturerInfoAdded
import Utils.SingletonForProduct.isRetailerInfoAdded
import Utils.SingletonForProduct.isSupplierInfoAdded
import com.supplychainapp.RetailerActivity
import com.supplychainapp.SupplierActivity
import com.supplychainapp.WholeSellerActivity

class GetProductInfoDecider {

    val hashMapPrioritiesForSupplyChainBlocks = hashMapOf<SingletonForProduct, Int>()

    fun screenToOpen(): Class<*>? {
        if (!isManufacturerInfoAdded) {
            return WholeSellerActivity::class.java
        }
        else if (!isSupplierInfoAdded) {
            return SupplierActivity::class.java
        }
        else if (!isRetailerInfoAdded) {
            return RetailerActivity::class.java
        }
        else {
            return null
        }
    }
 }