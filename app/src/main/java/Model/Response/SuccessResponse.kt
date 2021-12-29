package Model.Response

import com.google.gson.annotations.SerializedName

data class SuccessResponse(
    @SerializedName("message")
    var message: Message)
