package Utils

import Utils.SingletonForProduct.isManufacturerInfoAdded
import Utils.SingletonForProduct.isRetailerInfoAdded
import Utils.SingletonForProduct.isSupplierInfoAdded

object SingletonForProduct {
    var isSupplierInfoAdded = false
    var isRetailerInfoAdded = false
    var isManufacturerInfoAdded = false
}

val isAddMoreInfoConditionValid = (isSupplierInfoAdded && isRetailerInfoAdded).not() ||
        (isSupplierInfoAdded && isManufacturerInfoAdded).not() || (isRetailerInfoAdded && isManufacturerInfoAdded).not()

