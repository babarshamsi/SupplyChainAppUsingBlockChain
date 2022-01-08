package Utils

import Utils.SingletonForProduct.isRetailerInfoAdded
import Utils.SingletonForProduct.isSupplierInfoAdded
import Utils.SingletonForProduct.isWholeSellerInfoAdded
import com.supplychainapp.RetailerActivity
import com.supplychainapp.SupplierActivity
import com.supplychainapp.WholeSellerActivity

class GetProductInfoDecider {

    fun screenToOpen(): Class<*>? {
        if (!isWholeSellerInfoAdded) {
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