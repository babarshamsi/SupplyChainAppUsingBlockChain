package Model

import Extensions.SUPPLIER

data class Supplier(
    var senderName: String = "",
    var receiverName: String = "",
    var pickUpDate: String = "",
    var deliverDate: String = "",
    var pickUpFrom: String = "",
    var deliverTo: String = "",
    var type: String = SUPPLIER
)
