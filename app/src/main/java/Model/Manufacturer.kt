package Model

data class Manufacturer(
    var senderName: String = "",
    var receiverName: String = "",
    var pickUpDate: String = "",
    var deliverDate: String = "",
    var pickUpFrom: String = "",
    var deliverTo: String = "",
    var type: String = "manufacturer"
)
