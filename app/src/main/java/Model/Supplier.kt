package Model

data class Supplier(
    var supplierName: String = "",
    var retailerName: String = "",
    var pickUpDate: String = "",
    var deliverDate: String = "",
    var pickUpFrom: String = "",
    var deliverTo: String = "",
    var type: String = "supplier"
)

//data class SupplierInfo(
//    var index: Int
//) : Supplier()
