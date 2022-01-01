package Utils

import Utils.SingletonForProduct.isManufacturerInfoAdded
import Utils.SingletonForProduct.isRetailerInfoAdded
import Utils.SingletonForProduct.isSupplierInfoAdded
import com.supplychainapp.ManufacturerActivity
import com.supplychainapp.RetailerActivity
import com.supplychainapp.SupplierActivity

class GetProductInfoDecider {

    val hashMapPrioritiesForSupplyChainBlocks = hashMapOf<SingletonForProduct, Int>()

    fun screenToOpen(): Class<*>? {
        if (!isManufacturerInfoAdded) {
            return ManufacturerActivity::class.java
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