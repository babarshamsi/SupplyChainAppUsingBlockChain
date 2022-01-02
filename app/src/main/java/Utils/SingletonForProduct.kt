package Utils

object SingletonForProduct {
    var isSupplierInfoAdded = false
    var isRetailerInfoAdded = false
    var isWholeSellerInfoAdded = false

    var isAllInfoHasBeenSaved = isSupplierInfoAdded && isRetailerInfoAdded && isWholeSellerInfoAdded

    fun getBoolForIsAllInfoHasBeenSaved() = isSupplierInfoAdded && isRetailerInfoAdded && isWholeSellerInfoAdded
}


