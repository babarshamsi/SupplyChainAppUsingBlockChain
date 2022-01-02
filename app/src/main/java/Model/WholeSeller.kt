package Model

import Extensions.WHOLESELER

data class WholeSeller(
    var senderName: String = "",
    var receiverName: String = "",
    var pickUpDate: String = "",
    var deliverDate: String = "",
    var pickUpFrom: String = "",
    var deliverTo: String = "",
    var type: String = WHOLESELER
)
