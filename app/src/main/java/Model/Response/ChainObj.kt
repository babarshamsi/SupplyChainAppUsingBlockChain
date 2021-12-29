package Model.Response

data class ChainObj(
    var index: Int,
    var previousHash: String,
    var proof: Int,
    var timestamp: Double,
    var transactions: List<Object>
)


